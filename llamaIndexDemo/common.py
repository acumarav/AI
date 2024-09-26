from llama_index.vector_stores.postgres import PGVectorStore

INDEX_NAME = "llamaindex_docs"

ROLE = "role"
USER = "user"
ASSISTANT = "assistant"
CONTENT = "content"


def get_pg_vector_store():
    pg_vector_store = PGVectorStore.from_params(
        database="llamaindex",
        host="localhost",
        password="admin",
        port=5434,
        user="admin",
        table_name=INDEX_NAME,
        embed_dim=1536  # openai embedding dimension
    )
    return pg_vector_store
