from dotenv import load_dotenv

# from langchain.embeddings.openai import OpenAIEmbeddings
# from langchain.vectorstores.pgvector import PGVector
#
# from langchain.chains import RetrievalQA
# from langchain.llms import OpenAI
#
# import psycopg2
from llama_index.llms import openai
from llama_index.embeddings.openai import OpenAIEmbedding
from llama_index.core import download_loader, ServiceContext
from llama_index.vector_stores.postgres import PGVectorStore
from llama_index.core import StorageContext
from llama_index.readers.file import UnstructuredReader, HTMLTagReader
from llama_index.core import SimpleDirectoryReader
from llama_index.core.node_parser import SimpleNodeParser
from llama_index.llms.openai import OpenAI
from llama_index.core import VectorStoreIndex
from llama_index.core import Settings
# import nltk
# nltk.download()

from llama_index.vector_stores.postgres import PGVectorStore

CONNECTION_STRING="postgresql+psycopg2://admin:admin@localhost:5434/llamaindex"
INDEX_NAME="llamaindex_docs"

if __name__ == '__main__':
    print("Going to ingest llamaindex documentation...")
    # unstructured_reader =  download_loader("UnstructuredReader")
    uns_reader = UnstructuredReader()
    parser = HTMLTagReader()
    # dir_reader = SimpleDirectoryReader(input_dir="/adata/projects/AI/llamaIndexDemo/llamaindex_docs_tmp", file_extractor={".html": uns_reader})
    dir_reader = SimpleDirectoryReader(input_dir="/adata/projects/AI/llamaIndexDemo/llamaindex_docs", file_extractor={".html": uns_reader})

    documents = dir_reader.load_data()
    print(f"found {len(documents)} documents")

    node_parser = SimpleNodeParser.from_defaults(chunk_size=500,chunk_overlap=20)
    nodes = node_parser.get_nodes_from_documents(documents=documents)
    pass

    llm = OpenAI(model="gpt-3.5-turbo", temperature=0)
    embed_model = OpenAIEmbedding(model="text-embedding-ada-002", embed_batch_size=100)
    Settings.llm = llm
    Settings.embed_model = embed_model
    Settings.node_parser = node_parser

    index_name = "llamaindex-documentation-helper"
    vector_store = PGVectorStore.from_params(
        database="llamaindex",
        host="localhost",
        password="admin",
        port=5434,
        user="admin",
        table_name=INDEX_NAME,
        embed_dim=1536  # openai embedding dimension
    )
    pass

    storage_context = StorageContext.from_defaults(vector_store=vector_store)

    index = VectorStoreIndex.from_documents(documents=documents,
                                            storage_context=storage_context,
                                            show_progress=True)
    print("finished ingesting....")


