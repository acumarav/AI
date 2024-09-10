from sqlalchemy import Column
from sqlmodel import Field, SQLModel
from sqlmodel import create_engine
from datetime import datetime
from sqlmodel import Session, select, SQLModel
from constants import CONNECTION_STRING
from typing import List

engine = create_engine(CONNECTION_STRING)


class Talk(SQLModel, table=True):
    __tablename__ = "chat_history"
    __table_args__ = {'extend_existing': True}
    id: int = Field(default=None, primary_key=True, nullable=True)
    alert_id: int = Field(default=None, primary_key=False, nullable=False)
    question: str = Field(default=None, nullable=False)
    answer: str = Field(default=None, nullable=False)


def init_db():
    SQLModel.metadata.create_all(engine)


def save_talk(alert_id: int, question: str, answer: str) -> None:
    new_talk = Talk()
    new_talk.alert_id = alert_id
    new_talk.question = question
    new_talk.answer = answer
    with Session(engine) as session:
        session.add(new_talk)
        session.commit()


def load_chat_history(alert_id: int) -> List:
    results = []
    with Session(engine) as session:
        statement = select(Talk).where(Talk.alert_id == alert_id)
        for talk in session.exec(statement):
            results.append((talk.question, talk.answer))
            session.commit()
    return results


def find_chat_history_question(alert_id: int, question: str) -> List:
    results = []
    with Session(engine) as session:
        statement = select(Talk).where(Talk.alert_id == alert_id, Talk.question == question)
        for talk in session.exec(statement):
            results.append((talk.question, talk.answer))
            session.commit()
    return results
