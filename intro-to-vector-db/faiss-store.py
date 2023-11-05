import os
from langchain.document_loaders import TextLoader
from langchain.text_splitter import CharacterTextSplitter
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.vectorstores import FAISS

from langchain.chains import  RetrievalQA
from langchain.llms import  OpenAI

if __name__ == '__main__':
    print("Hello FAISS VectorStore!")
    loader = TextLoader("mediumblogs/mediumblogs.txt")
    document = loader.load()
    print(document)

    text_splitter = CharacterTextSplitter(chunk_size=700, chunk_overlap=200)
    texts = text_splitter.split_documents(document)
    print(len(texts))

    ai_api_key = os.environ["OPENAI_API_KEY"]
    print(ai_api_key)
    embeddings = OpenAIEmbeddings(openai_api_key=ai_api_key)

    vectorstore = FAISS.from_documents(texts, embeddings)
    vectorstore.save_local("faiss_index_react")

    new_vectorstore = FAISS.load_local("faiss_index_react", embeddings)

    qa = RetrievalQA.from_chain_type(llm= OpenAI(), chain_type="stuff", retriever = new_vectorstore.as_retriever())

    res = qa.run("Give me the gist of Vector database in 3 sentences ")
    print(res)

