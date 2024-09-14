import os
from dotenv import  load_dotenv
from llama_index.vector_stores.postgres import PGVectorStore
from llama_index.core import VectorStoreIndex
from llama_index.core.callbacks import LlamaDebugHandler, CallbackManager
from llama_index.core import Settings

INDEX_NAME="llamaindex_docs"

def main(url: str) -> None:

    pg_vector_store = PGVectorStore.from_params(
        database="llamaindex",
        host="localhost",
        password="admin",
        port=5434,
        user="admin",
        table_name=INDEX_NAME,
        embed_dim=1536  # openai embedding dimension
    )

    index = VectorStoreIndex.from_vector_store(vector_store=pg_vector_store)

    llama_debug = LlamaDebugHandler(print_trace_on_end=True)
    callback_manager = CallbackManager(handlers=[llama_debug])
    Settings.callback_manager = callback_manager

    query = "What is a LlamaIndex query engine?"
    query_engine = index.as_query_engine()
    response = query_engine.query(query)
    print(response)
    pass

if __name__ == '__main__':
    load_dotenv()
    print("**** Hello world ****")
    main(url='https://cbarkinozer.medium.com/an-overview-of-the-llamaindex-framework-9ee9db787d16')