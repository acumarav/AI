#
# In order to start the app run command:
# streamlit run  frontend.py
#
import pandas as pd
import streamlit as st
import time
from st_aggrid import AgGrid, GridOptionsBuilder, ColumnsAutoSizeMode
from streamlit_chat import message
from chat_history import init_db, save_talk, load_chat_history, find_chat_history_question
from constants import CONNECTION, CHAT_HISTORY, RESPONSE_HISTORY, INFO, FIX, VALIDATION
from llm import ask_chat_model, ask_chat_with_retrievalqa


def render_chat_history(selected_alert_id: int) -> None:
    if selected_alert_id > 0:
        alert_talks = load_chat_history(alert_id=selected_alert_id)
        for i, (question, answer) in enumerate(alert_talks):
            if question:
                message(question, is_user=True, key=i * 2 - 1)
            if answer:
                message(answer, is_user=False, key=i * 2)


st.set_page_config(layout="wide",
                   page_title="Alert Syntetizer")

conn = st.connection(CONNECTION, type='sql', ttl=600)
alerts_query = conn.query("SELECT * FROM ALERTS;")

data_frame = pd.DataFrame(alerts_query,
                          columns=['alert_id', 'message_id', 'description', 'additional_information', 'severity'])

h1, h2 = st.columns([2, 20])
with h1:
    st.image(image='data/images/langchain.png', width=96)
with h2:
    st.header("  Modern Incident Synthesizer")

gd = GridOptionsBuilder.from_dataframe(data_frame)
gd.configure_pagination(paginationPageSize=10, enabled=True, paginationAutoPageSize=False)
gd.configure_selection(selection_mode='single')
gridOptions = gd.build()

grid_return = AgGrid(data_frame, gridOptions=gridOptions, columns_auto_size_mode=ColumnsAutoSizeMode.FIT_CONTENTS)

if CHAT_HISTORY not in st.session_state:
    st.session_state[CHAT_HISTORY] = []
    st.session_state[RESPONSE_HISTORY] = []
    init_db()

mfErr: str = ""
mfFix: str = ""
mfValidation: str = ""
alert_description = ''
alert_id = 0
selected_rows = grid_return["selected_rows"]

if selected_rows:
    al = selected_rows[0]
    errCode = al['message_id']
    alert_id = al['alert_id']
    alert_description = al['description'] + '\n ' + al['additional_information']
    mfErr = 'Give me 3 answers about an error code ' + errCode + ' with description and documents for "' + al[
        'description'] + '"?'
    mfFix = 'How should I fix this problem ' + al['description'] + '' + al['additional_information']
    mfValidation = 'How can I test that this problem ' + al['description'] + '\n ' + al[
        'additional_information'] + ' is resolved?'
else:
    st.write("no selection")

render_chat_history(selected_alert_id=alert_id)

template = st.radio("Template:", (INFO, FIX, VALIDATION), horizontal=True)
text_area_content = mfErr
if template == FIX:
    text_area_content = mfFix
elif template == VALIDATION:
    text_area_content = mfValidation

prompt = st.text_area(value=text_area_content, label="Your question:", label_visibility='hidden')

col1, col2, col3, col4 = st.columns([1, 1, 1, 1])
with col1:
    btnResult = st.button(label="Ask LLM ")
with col2:
    btnResultEx = st.button(label="Ask with references")
with col3:
    btnResultAddNote = st.button(label="Add note")
with col4:
    btnReport = st.button(label="Generate Report")

existing_question = find_chat_history_question(alert_id=alert_id, question=prompt)

if (btnResult or btnResultEx or btnResultAddNote) and existing_question:
    st.warning("This question was already asked, see above.", icon="⚠️")

if btnReport:
    st.info(
        "We have stored full conversation in the DB.\n Prompt for report generation will be add soon!",
        icon="⚠️")

if btnResult and not existing_question:
    with st.spinner("AI engine generating response..."):
        response = ask_chat_model(question=prompt)
        rp = response.content
        save_talk(alert_id, question=prompt, answer=rp)
        st.rerun()

if btnResultEx and not existing_question:
    time.sleep(1)
    result_with_docs = ask_chat_with_retrievalqa(query=prompt)
    # result_with_docs = "I do not know - " + datetime.now().strftime("%d/%m/%Y %H:%M:%S")
    print(f"Result_with_doc: {result_with_docs}")
    st.session_state[CHAT_HISTORY].append(prompt)
    st.session_state[RESPONSE_HISTORY].append(result_with_docs)
    save_talk(alert_id, question=prompt, answer=result_with_docs)
    st.rerun()

if btnResultAddNote and not existing_question:
    save_talk(alert_id, question=prompt, answer="")
    time.sleep(1)
    st.rerun()
