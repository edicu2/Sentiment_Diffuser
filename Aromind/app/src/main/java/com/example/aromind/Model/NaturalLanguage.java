package com.example.aromind.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.DocumentSentimentResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.SentimentOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.SentimentResult;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

import java.util.ArrayList;

public class NaturalLanguage {

        private final String APIKEY_WATSON = "o0zW-sXrq1_NsnbrhWlxXpU3DDBA4weVvbuuJ5jyt9aq";
        private final String URL_WATSON = "https://gateway.watsonplatform.net/natural-language-understanding/api";
        private String document;

        public NaturalLanguage(ArrayList<String> text){
            if (text == null || text.size()<1){
                Log.i("TTTERROR", "ERROR");
                return;
            }

            for (int i=0; i<text.size(); i++) {
                connectNL(text.get(i));
                Log.i("TTT3", text.get(i));
            }
        }

        private void connectNL(final String text){

            String url = URL_WATSON;
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... urls) {
                    Log.i("TTT4", urls[0].toString());
                    String result = new String();
                    if (urls == null || urls.length < 1) {
                        return null;
                    }
                    IamOptions options = new IamOptions.Builder()
                            .apiKey(APIKEY_WATSON)
                            .build();
                    Log.i("TTT5", "test");
                    NaturalLanguageUnderstanding naturalLanguageUnderstanding = new NaturalLanguageUnderstanding("2018-11-16", options);
                    System.out.println(urls[0].toString());

                    naturalLanguageUnderstanding.setEndPoint(String.valueOf(urls[0]));

                    SentimentOptions sentiment = new SentimentOptions.Builder()
                            .build();

                    Features features = new Features.Builder()
                            .sentiment(sentiment)
                            .build();

                    AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                            .text(text)
                            .features(features)
                            .build();
                    Log.i("TTT6", text);

                    AnalysisResults response = naturalLanguageUnderstanding
                            .analyze(parameters)
                            .execute();
                    System.out.println(response);
                    SentimentResult sentimentResult =response.getSentiment();
                    DocumentSentimentResults documentSentimentResults = sentimentResult.getDocument();
                    String label = documentSentimentResults.getLabel();
                    Double score = documentSentimentResults.getScore();
                    result = label+" : "+String.valueOf(score);
                    Log.i("TTT7", documentSentimentResults.toString());
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    document = result;
                    Log.i("TTT8", document);
                }
            }.execute(url);
        }

        public String getDocument(){
            return document;
        }

    }
