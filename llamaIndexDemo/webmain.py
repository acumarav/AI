import os
from dotenv import load_dotenv
from llama_index.core.chat_engine.types import ChatMode
from llama_index.vector_stores.postgres import PGVectorStore
from llama_index.core import VectorStoreIndex, ServiceContext
from llama_index.core.callbacks import LlamaDebugHandler, CallbackManager
from llama_index.core import Settings
from llama_index.core.postprocessor import SentenceEmbeddingOptimizer
from common import get_pg_vector_store
import streamlit as st
from common import USER, ROLE, ASSISTANT, CONTENT
from node_postprocessors.duplicate_postprocessor import DuplicateRemoverNodePostprocessor


@st.cache_resource
def get_index() -> VectorStoreIndex:
    llama_debug = LlamaDebugHandler(print_trace_on_end=True)
    callback_manager = CallbackManager(handlers=[llama_debug])
    Settings.callback_manager = callback_manager

    pg_vector_store = get_pg_vector_store()
    index = VectorStoreIndex.from_vector_store(vector_store=pg_vector_store)
    return index


def buildUserMsg(content: str):
    user_msg = {ROLE: USER,
                CONTENT: content}
    return user_msg


def buildLlmMsg(content: str):
    user_msg = {ROLE: ASSISTANT,
                CONTENT: content}
    return user_msg


def webmain() -> None:
    st.set_page_config(page_title="Chat with LlamaIndex docs, powered by LlamaIndex",
                       page_icon="🦙 ",
                       layout="centered",
                       initial_sidebar_state="auto",
                       menu_items=None)
    st.title("Chat with Llama index docs 🗨 🦙")

    index = get_index()
    if "chat_engine" not in st.session_state.keys():
        postprocessor = SentenceEmbeddingOptimizer(embed_model=Settings.embed_model, percentile_cutoff=0.5,
                                                   threshold_cutoff=0.7)

        st.session_state.chat_engine = index.as_chat_engine(chat_mode=ChatMode.CONTEXT, verbose=True,
                                                            node_postprocessors=[postprocessor, DuplicateRemoverNodePostprocessor()])

    if "messages" not in st.session_state.keys():
        st.session_state.messages = [
            {
                ROLE: ASSISTANT,
                CONTENT: "Ask me a question about LlamaIndex's open source python library?"
            }
        ]

    if prompt := st.chat_input("Your question"):
        st.session_state.messages.append(buildUserMsg(prompt))

    for message in st.session_state.messages:
        with st.chat_message(message[ROLE]):
            st.write(message[CONTENT])

    if st.session_state.messages[-1][ROLE] != ASSISTANT:
        with st.chat_message(ASSISTANT):
            with st.spinner("Thinking..."):
                response = st.session_state.chat_engine.chat(message=prompt)
                st.write(response.response)
                st.session_state.messages.append(buildLlmMsg(response.response))
                nodes = [node for node in response.source_nodes]
                for col, node, i in zip(st.columns(len(nodes)), nodes, range(len(nodes))):
                    with col:
                        st.header(f"Source Node {i + 1}: score= {node.score}")
                        st.write(node.text)


#
# query = "What is a LlamaIndex query engine?"
# query_engine = index.as_query_engine()
# response = query_engine.query(query)
# print(response)
# pass


if __name__ == '__main__':
    webmain()
