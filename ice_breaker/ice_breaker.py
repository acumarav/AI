from langchain import PromptTemplate
from langchain.chat_models import ChatOpenAI
from langchain.chains import LLMChain

from third_parties.linkedin import (
    scrape_linkenin_profile_stub,
    scrape_linkedin_profile_real,
)

from agents.linked_lookup_agent import lookup as linkedin_lookup_agent

if __name__ == "__main__":
    print("Hello LangChain!")

    linkedin_profile_url = linkedin_lookup_agent(name="Eden Marco Udemy")

    summary_template = """
    given the Linkedin information {information} about a person from I want you to create:
    1. a short summary
    2. two interesting facts about them
    """

    summary_prompt_template = PromptTemplate(
        input_variables=["information"], template=summary_template
    )

    llm = ChatOpenAI(temperature=0, model_name="gpt-3.5-turbo")
    chain = LLMChain(llm=llm, prompt=summary_prompt_template)

    # linkedin_data = scrape_linkenin_profile_stub(unused_url="dummy")
    linkedin_data = scrape_linkedin_profile_real(
        linkedin_profile_url=linkedin_profile_url
    )

    print(chain.run(information=linkedin_data))

    print(linkedin_data)
