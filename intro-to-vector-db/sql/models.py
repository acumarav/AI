from typing import Optional, List

from sqlmodel import Field, SQLModel
from sqlmodel import create_engine
from pgvector.sqlalchemy import Vector
from sqlalchemy import Column

CONNECTION_STRING="postgresql+psycopg2://admin:admin@localhost:5434/vector"

engine = create_engine(CONNECTION_STRING)

class Document(SQLModel, table = True):
    id: int = Field(default=None, primary_key=True, nullable=False)
    file_path: str = Field(default=None, nullable=False)
    content: str = Field(default=None, nullable=False)
    embedded: List[float] = Field(sa_column=Column(Vector(1536)), default=None)


def init_db():
    SQLModel.metadata.create_all(engine)
