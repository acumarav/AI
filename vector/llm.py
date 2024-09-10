import json
from langchain.chains import RetrievalQA
from langchain.chat_models import ChatOpenAI
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.llms import OpenAI
from langchain.schema import HumanMessage
from langchain.vectorstores.pgvector import PGVector
from typing import Set


from constants import CONNECTION_STRING, COLLECTION_NAME

import os

# OPENAI_API_KEY=""
# os.environ["OPENAI_API_KEY"] = OPENAI_API_KEY

# os.environ["OPENAI_API_KEY"] = OPENAI_API_KEY
# LLM Model
llm_model = OpenAI(temperature=0.9)

chat_model = ChatOpenAI()
cold_llm = ChatOpenAI(temperature=0.2, verbose=True)

def ask_llm(question: str) -> str:
    answer = llm_model.invoke(question)
    return answer


def ask_chat_model(question: str) -> str:
    messages = [HumanMessage(content=question)]
    answer = chat_model.invoke(messages)
    return answer


def concat_sources_string(source_urls: Set[str]) -> str:
    if not source_urls:
        return ""
    sources_list = list(source_urls)
    sources_string = "references: \n"
    for i, source in enumerate(sources_list):
        sources_string += f"{i + 1}. {source}\n"
    return sources_string

def ask_chat_with_retrievalqa(query: str):
    docsearch = PGVector.from_existing_index(collection_name=COLLECTION_NAME,
                                             embedding=OpenAIEmbeddings(),
                                             connection_string=CONNECTION_STRING)
    retriever = docsearch.as_retriever(search_kwargs={"k": 6})

    qa_chain = RetrievalQA.from_chain_type(
        llm=cold_llm,
        retriever=retriever,
        chain_type="stuff",
        verbose=True,
        return_source_documents=True
    )
    llm_result = qa_chain({"query": query})
    sources = set([doc.metadata["source"] for doc in llm_result["source_documents"]])
    result_with_docs = (f"{llm_result['result']} \n\n {concat_sources_string(sources)}")
    return result_with_docs
