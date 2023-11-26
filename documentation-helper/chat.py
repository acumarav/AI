import os
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.chat_models import ChatOpenAI
from langchain.chains import RetrievalQA
from langchain_core.documents import Document

from typing import Any, List

from langchain.vectorstores.pgvector import PGVector, DistanceStrategy

from langchain_core.vectorstores import VectorStoreRetriever

from sql.models import CONNECTION_STRING
from constants import COLLECTION_NAME

query = "What it says about nodejs"


def run_retriever_search(retriever: VectorStoreRetriever) -> None:
    docs = retriever.get_relevant_documents(query=query)
    for doc in docs:
        print("-" * 80)
        print(doc.page_content)
        print(doc.metadata)
        print("-" * 80)


def run_similarity_search(docsearch: PGVector) -> None:
    docs = docsearch.similarity_search_with_score(query=query)
    print(f"Found: {len(docs)} documents")
    for doc, score in docs:
        print("-" * 80)
        print("Score: ", score)
        print(doc.page_content)
        print(doc.metadata)
        print("-" * 80)


def run_llm(query: str) -> Any:
    embeddings = OpenAIEmbeddings()

    empty_docs = []

    docsearch = PGVector.from_existing_index(collection_name=COLLECTION_NAME, embedding=embeddings,
                                             connection_string=CONNECTION_STRING)
    # run_similarity_search(docsearch=docsearch)

    retriever = docsearch.as_retriever(search_kwargs={"k": 3})
    # run_retriever_search(retriever=retriever)

    llm = ChatOpenAI(temperature=0.0, model='gpt-3.5-turbo-16k', verbose=True)

    qa_stuff = RetrievalQA.from_chain_type(
        llm=llm,
        chain_type="stuff",
        retriever=retriever,
        verbose=True,
    )
    return qa_stuff({"query": query})


if __name__ == "__main__":
    #run_llm("node.js")
    #response = run_llm(query="What is RetrievalQA chain?")
    response = run_llm(query="What is Zowe Mediation layer?")
    print(response)
