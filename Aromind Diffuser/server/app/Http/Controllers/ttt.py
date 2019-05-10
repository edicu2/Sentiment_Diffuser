import json
import sys
from watson_developer_cloud import NaturalLanguageUnderstandingV1
from watson_developer_cloud.natural_language_understanding_v1 import Features, SentimentOptions

natural_language_understanding = NaturalLanguageUnderstandingV1(
    version='2018-11-16',
    iam_apikey='nCbrQdGJ9ivTqD0vgNcyr-u5UEK-A4aABWWmAb7jnIm7',
    url='https://gateway.watsonplatform.net/natural-language-understanding/api'
)

response = natural_language_understanding.analyze(
    text=str(sys.argv[1]),
    #text = " 안녕하세요",
    features=Features(sentiment=SentimentOptions(targets=['']))).get_result()
print(json.dumps(response, indent=2))
