import os
import requests


# My gist link: https://gist.githubusercontent.com/cumarav/fcaaac37bf928b13c051d304db9be018/raw/e5e97eed186f334351896d6e591f5ed5decca0e8/alex.json


def scrape_linkedin_profile_real(linkedin_profile_url: str):
    """scrape information about the linkedin profiles,
    Manually scrape the information from the LinkedIn profile"""

    api_endpoint = "https://nubela.co/proxycurl/api/v2/linkedin"
    header_dic = {"Authorization": f'Bearer {os.environ.get("PROXYCURL_API_KEY")}'}

    response = requests.get(
        api_endpoint, params={"url": linkedin_profile_url}, headers=header_dic
    )
    return clean_response(response)


def scrape_linkedin_profile_stub(unused_url: str):
    stub_endpoint = "https://gist.githubusercontent.com/cumarav/fcaaac37bf928b13c051d304db9be018/raw/e5e97eed186f334351896d6e591f5ed5decca0e8/alex.json"
    response = requests.get(stub_endpoint)
    return clean_response(response)


def clean_response(response):
    data = response.json()
    data = {
        k: v
        for k, v in data.items()
        if v not in ([], "", "", None)
        and k not in ["people_also_viewed", "certifications"]
    }
    if data.get("groups"):
        for group_dict in data.get("groups"):
            group_dict.pop("profile_pic_url")

    return data
