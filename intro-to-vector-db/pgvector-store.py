import os
from langchain.document_loaders import TextLoader
from langchain.document_loaders import ReadTheDocsLoader
from langchain.document_loaders import UnstructuredMarkdownLoader
from langchain.document_loaders import DirectoryLoader
from langchain.text_splitter import CharacterTextSplitter
from langchain.text_splitter import MarkdownTextSplitter
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.vectorstores.pgvector import PGVector

from langchain.chains import RetrievalQA
from langchain.llms import OpenAI

import psycopg2

CONNECTION_STRING="postgresql+psycopg2://admin:admin@localhost:5434/vector"
COLLECTION_NAME="apiml_markdown"

if __name__ == '__main__':
    ai_api_key= os.environ["OPENAI_API_KEY"]
    #print(f"\napi key={ai_api_key}")

    loader = DirectoryLoader(
        "./apiml_docs", glob="**/*.md", loader_cls=UnstructuredMarkdownLoader
    )
    my_documents = loader.load()
    print(f"Found {len(my_documents)} documents")

    conn = psycopg2.connect(database="vector", user="admin", password="admin", port=5434, host="localhost")
    cur = conn.cursor()

    insert = """insert into "public".document(file_path, content) values (%s,%s);"""

    text_splitter = MarkdownTextSplitter(chunk_size=1000, chunk_overlap=200)
    texts = text_splitter.split_documents(my_documents)

    for text in texts:
        record = {
            "file_path": text.metadata["source"],
            "content": text.page_content
        }
        print(f"\n\t{record}")
        file_path = text.metadata["source"]
        cur.execute(insert, (file_path, text.page_content) )
        conn.commit()

    cur.close()
    conn.close()
