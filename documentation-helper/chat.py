import os
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.chat_models import ChatOpenAI
from langchain.chains import RetrievalQA

from typing import Any

from langchain.vectorstores.pgvector import PGVector, DistanceStrategy

from sql.models import CONNECTION_STRING


def run_llm(query: str) -> Any:
    embeddings = OpenAIEmbeddings()

    empty_docs = []
    db = PGVector(embedding_function=embeddings, connection_string=CONNECTION_STRING,
                  collection_name="document")

db.
    # db = PGVector.from_documents(embedding=embeddings, documents=empty_docs, connection_string=CONNECTION_STRING,
    #                              collection_name="document")

    # retriever = db.as_retriever(search_kwargs={"k": 3})
    # llm = ChatOpenAI(temperature=0.0, model='gpt-3.5-turbo-16k')
    # qa_stuff = RetrievalQA.from_chain_type(
    #     llm=llm,
    #     chain_type="stuff",
    #     retriever=retriever,
    #     verbose=True,
    # )

    print(f"Found: {len(docs)} documents")
    for doc in docs:
        print(doc)


if __name__ == "__main__":
    run_llm("node.js")
