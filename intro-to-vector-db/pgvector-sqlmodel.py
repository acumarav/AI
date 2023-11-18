import os
import psycopg2
from typing import List
from sqlmodel import Session, select,SQLModel

from sql.models import CONNECTION_STRING, engine, init_db, Document


def get_documents() -> List:
    output2 = []
    with Session(engine) as session:
        statement = select(Document)
        res = session.exec(statement)
        for pr in res:
            document_dict = {
                'id': pr.id,
                'file_path': pr.file_path,
                'content': pr.content,
            }
            output2.append(pr)
    return output2

def main():
    print(f"Connection string: {CONNECTION_STRING}")
    init_db()
    docs = get_documents()
    for doc in docs:
        print(f" ID:{doc.id}, path: {doc.file_path}")


if __name__ == '__main__':
    main()