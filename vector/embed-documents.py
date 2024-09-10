import os
from typing import List

# from langchain.document_loaders.json_loader import JSONLoader
from langchain.document_loaders import PyPDFDirectoryLoader
from langchain.document_loaders.url import UnstructuredURLLoader
from langchain.document_loaders.text import TextLoader
from langchain.document_loaders import DirectoryLoader
from langchain.text_splitter import MarkdownTextSplitter, RecursiveCharacterTextSplitter
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.vectorstores.pgvector import PGVector

# from sql.models import engine, init_db, CONNECTION_STRING
from openai import OpenAI
from constants import COLLECTION_NAME, CONNECTION_STRING

ai_api_key = os.environ["OPENAI_API_KEY"]


def ingest_quickref() -> None:
    loader = DirectoryLoader(
        "./data/quickref", glob="**/*.json", loader_cls=TextLoader
    )
    qw_documents = loader.load()
    print(f"Found {len(qw_documents)} documents")
    md_splitter = MarkdownTextSplitter(chunk_size=2000, chunk_overlap=100)
    chunks = md_splitter.split_documents(qw_documents)
    print(f"Produced {len(chunks)} chunks to embed")

    PGVector.from_documents(
        embedding=OpenAIEmbeddings(),
        documents=chunks,
        collection_name=COLLECTION_NAME,
        connection_string=CONNECTION_STRING
    )


def ingest_pdfs() -> None:
    loader = PyPDFDirectoryLoader(
        "./data/redbooks", glob="**/*.pdf"
    )
    pdf_documents = loader.load()
    print(f"Found {len(pdf_documents)} documents")
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=3000, chunk_overlap=100)
    chunks = text_splitter.split_documents(pdf_documents)
    print(f"Produced {len(chunks)} chunks to embed")
    PGVector.from_documents(
        embedding=OpenAIEmbeddings(),
        documents=chunks,
        collection_name=COLLECTION_NAME,
        connection_string=CONNECTION_STRING
    )
    print(f"Embeddings stored")


def embed_wolken_urls() -> None:
    wolken_urls = [
        "https://knowledge.broadcom.com/external/article/244847/transaction-hung-in-cics-making-a-db2-ca.html",
        "https://knowledge.broadcom.com/external/article/223756/db2-and-cics-abends-related-to-the-db2-s.html",
        "https://knowledge.broadcom.com/external/article/52380/db2-stored-proc-time-in-cics.html",
        "https://knowledge.broadcom.com/external/article/117814/exclude-a-small-subset-of-cics-transacti.html",
        "https://knowledge.broadcom.com/external/article/188423/sysview-delay-in-db2.html",
        "https://knowledge.broadcom.com/external/article/213337/about-impacts-on-sysview-for-db2-by-upgr.html",
        "https://knowledge.broadcom.com/external/article/128597/what-acf2-security-setup-is-needed-for-i.html",
        "https://knowledge.broadcom.com/external/article/102299/how-to-install-ca-intertest-cics-r11-and.html",
        "https://knowledge.broadcom.com/external/article/264703/generol-upgrade-to-cics-rdo-definitions.html",
        "https://knowledge.broadcom.com/external/article/27722/acf2-support-for-setting-up-the-keyrings.html",
        "https://knowledge.broadcom.com/external/article/18137/why-does-the-ca-datacom-140-ddol-signon.html",
        "https://knowledge.broadcom.com/external/article/101572/c1a0010e-and-c1a0011e-errors-in-endevor.html",
        "https://knowledge.broadcom.com/external/article/26327/how-to-define-an-acf2-scope-record-to-al.html",
        "https://knowledge.broadcom.com/external/article/24699/ca-acf2-was-installed-with-smpe-dddefs-i.html",
        "https://knowledge.broadcom.com/external/article/9992",
        "https://knowledge.broadcom.com/external/article/129522/acf04056-access-to-resource-dfhapplcicss.html",
        "https://knowledge.broadcom.com/external/article/14940/cics-start-acf04056-violation-for-resour.html",
        "https://knowledge.broadcom.com/external/article/264703/generol-upgrade-to-cics-rdo-definitions.html",
        "https://knowledge.broadcom.com/external/article/103621/ideal-db2-failure-due-to-dsnhdecp-module.html",
        "https://knowledge.broadcom.com/external/article/55882/resolving-dfh4103-dfh4104-dfh4110-and-df.html",
        "https://knowledge.broadcom.com/external/article/49593/what-is-cics-surrogat-checking-and-the-x.html",
        "https://knowledge.broadcom.com/external/article/16182/i-upgraded-to-acf2cics-r16-and-i-am-unab.html",
        "https://knowledge.broadcom.com/external/article/55344/cics-startup-missing-top-secret-phase-m.html",
        "https://knowledge.broadcom.com/external/article/36971/we-are-upgrading-to-new-release-of-cics.html",
        "https://knowledge.broadcom.com/external/article/52368/i-am-coding-an-acf2cics-acf2parm-mro-par.html",
        "https://knowledge.broadcom.com/external/article/22910/cics-transaction-gateway-for-zos-and-rci.html",
        "https://knowledge.broadcom.com/external/article/52902/dfhxs1111dfhus0050dfhdm0105dfhme0116-whe.html",
        "https://knowledge.broadcom.com/external/article/38928/how-to-install-datacomdb-when-cics-sdfhl.html",
        "https://knowledge.broadcom.com/external/article/204025/acf2-ckti-not-triggering-with-mq-monitor.html",
        "https://knowledge.broadcom.com/external/article/56302/asra-abend-calling-a-cics-nonideal-subpr.html",
        "https://knowledge.broadcom.com/external/article/27237/how-to-set-up-your-acf2-system-to-allow.html",
        "https://knowledge.broadcom.com/external/article/54650/cui-transaction-hangs.html"]
    loader = UnstructuredURLLoader(urls=wolken_urls, continue_on_failure=True, show_progress_bar=True)
    kb_documents = loader.load()
    print(f"Found {len(kb_documents)} documents")
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=2000, chunk_overlap=100)
    chunks = text_splitter.split_documents(kb_documents)
    print(f"Produced {len(chunks)} chunks to embed")
    PGVector.from_documents(
        embedding=OpenAIEmbeddings(),
        documents=chunks,
        collection_name=COLLECTION_NAME,
        connection_string=CONNECTION_STRING
    )
    print(f"Wolken embeddings stored")


if __name__ == "__main__":
    """
    Data ingestion happening once per document - it is part of setup or maintenance, calls are commented by default
    """
    # ingest_quickref()
    ingest_pdfs()
    # embed_wolken_urls()
