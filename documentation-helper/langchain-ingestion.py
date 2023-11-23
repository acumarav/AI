import os
from typing import List
from sqlmodel import Session, select, SQLModel

from langchain.document_loaders import UnstructuredMarkdownLoader
from langchain.document_loaders import DirectoryLoader
from langchain.text_splitter import MarkdownTextSplitter
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.docstore.document import Document as LangDocument
from langchain.vectorstores.pgvector import PGVector

from sql.models import engine, init_db, CONNECTION_STRING
from openai import OpenAI

ai_api_key = os.environ["OPENAI_API_KEY"]

COLLECTION_NAME="local_docs"

embeddings = OpenAIEmbeddings()


def ingest() -> None:
    loader = DirectoryLoader(
        "./apiml_docs", glob="**/*.md", loader_cls=UnstructuredMarkdownLoader
    )
    my_documents = loader.load()
    print(f"Found {len(my_documents)} documents")
    text_splitter = MarkdownTextSplitter(chunk_size=2000, chunk_overlap=100)
    chunks = text_splitter.split_documents(my_documents)
    print(f"Produced {len(chunks)} chunks to embed")

    PGVector.from_documents(
        embedding=embeddings,
        documents=chunks,
        collection_name=COLLECTION_NAME,
        connection_string=CONNECTION_STRING
    )


if __name__ == "__main__":
    ingest()
