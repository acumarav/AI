import os
from typing import List
from sqlmodel import Session, select, SQLModel
from langchain.embeddings.openai import OpenAIEmbeddings

from langchain.document_loaders import UnstructuredMarkdownLoader
from langchain.document_loaders import DirectoryLoader
from langchain.text_splitter import MarkdownTextSplitter

from langchain.docstore.document import Document as LangDocument

from sql.models import CONNECTION_STRING, engine, init_db, Document as PgDocument

from openai import OpenAI

EMBEDDINGS_MODEL = "text-embedding-ada-002"


def get_documents() -> List:
    output2 = []
    with Session(engine) as session:
        statement = select(PgDocument)
        res = session.exec(statement)
        for pr in res:
            document_dict = {
                'id': pr.id,
                'file_path': pr.file_path,
                'content': pr.content,
            }
            output2.append(pr)
    return output2


def write_document(document: PgDocument):
    with Session(engine) as session:
        session.add(document)
        session.commit()


def print_documents():
    docs = get_documents()
    for doc in docs:
        print(f" ID:{doc.id}, path: {doc.file_path}, vec: {doc.embedded}")


def write_documents():
    ai_api_key = os.environ["OPENAI_API_KEY"]
    client = OpenAI(api_key=ai_api_key)
    print(f"\napi key={ai_api_key}")

    loader = DirectoryLoader(
        "./apiml_docs", glob="**/*.md", loader_cls=UnstructuredMarkdownLoader
    )
    my_documents = loader.load()
    print(f"Found {len(my_documents)} documents")
    text_splitter = MarkdownTextSplitter(chunk_size=2000, chunk_overlap=200)
    texts = text_splitter.split_documents(my_documents)
    print(f"Produced {len(texts)} chunks to embed")
    for doc in texts:
        unsaved_doc = embed_one_document(doc, client=client)
        write_document(unsaved_doc)


def embed_one_document(lang_doc: LangDocument, client: OpenAI) -> PgDocument:
    new_document = PgDocument()
    new_document.file_path = lang_doc.metadata["source"]
    new_document.content = lang_doc.page_content
    res = client.embeddings.create(input=[lang_doc.page_content], model=EMBEDDINGS_MODEL)
    print(f'Generated embeddings for the string "{lang_doc.page_content[0:100]}", dimensions: {len(res.data[0].embedding)}')
    vector = res.data[0].embedding
    new_document.embedded = vector
    print(f"New doc: {new_document}")
    return new_document


def main():
    print(f"Connection string: {CONNECTION_STRING}")
    init_db()
    write_documents()


if __name__ == '__main__':
    main()
