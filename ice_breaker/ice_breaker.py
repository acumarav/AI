from typing import Tuple

from langchain.prompts import PromptTemplate
from langchain.chat_models import ChatOpenAI
from langchain.chains import LLMChain

from output_parsers import person_intel_parser, PersonIntel
from third_parties.linkedin import (
    scrape_linkedin_profile_stub,
    scrape_linkedin_profile_real,
)

from agents.linked_lookup_agent import lookup as linkedin_lookup_agent


def ice_break(name: str, real_mode: bool) -> Tuple[PersonIntel, str]:
    summary_template = """
    given the Linkedin information {information} about a person from I want you to create:
    1. a short summary
    2. two interesting facts about them
    3. a topic that may interest them
    4. 2 creative ice breakers to open a conversation with them
    \n{format_instructions}
    """

    summary_prompt_template = PromptTemplate(
        input_variables=["information"],
        template=summary_template,
        partial_variables={
            "format_instructions": person_intel_parser.get_format_instructions()
        },
    )

    llm = ChatOpenAI(temperature=0, model_name="gpt-3.5-turbo")
    chain = LLMChain(llm=llm, prompt=summary_prompt_template)

    linkedin_data = scrape_linkedin_profile_stub(unused_url="dummy")

    if real_mode:
        linkedin_profile_url = linkedin_lookup_agent(name=name)
        linkedin_data = scrape_linkedin_profile_real(
            linkedin_profile_url=linkedin_profile_url
        )

    result = chain.run(information=linkedin_data)
    print(result)
    # print(linkedin_data)
    return person_intel_parser.parse(result), linkedin_data.get("profile_pic_url")


if __name__ == "__main__":
    print("Hello LangChain!")
    # result = ice_break(name="Eden Marco Udemy")
    result, pic_url = ice_break(name="Michael Patriarco Broadcom", real_mode=True)
    # result, pic_url = ice_break(name="Harrison Chase", real_mode=False)
    print("Pic URL: " + pic_url)
    print("the end!")
