package com.example.aromind.Face;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.aromind.Sentiment.Sentiment_HistoryHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class Face {

    private static final String subscriptionKey = "65dc80447ea0495c92ed496b0b682509";

    private static String imageUrl= null;
    private static final String uriBase = "https://koreacentral.api.cognitive.microsoft.com/face/v1.0/detect";

    private static String imageWithFaces =null;

    //"{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg\"}";


    private static final String faceAttributes = "emotion";

    private String result;
    private double score;
    private String emotionKey;
    private String[] emo = {"anger", "contempt", "disgust", "fear", "happiness", "neutral", "sadness", "surprise"};
    private Double[] emoScroe= new Double[8];
    private Sentiment_HistoryHelper sentiment_historyHelper;
private Context context;

    //얼굴 감지 REST API호출
    public Face(String imageUrl) {
        this.imageWithFaces = imageUrl;
        //this.context =context;
        Log.i("imageWithFaces", imageWithFaces);
    }

    public void connectAF(final Context context){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... urls) {
                Log.i("urrr", String.valueOf(urls[0]));
                HttpClient httpclient = HttpClientBuilder.create().build();

                try {
                    Log.i("messagea", "ok");
                    URIBuilder builder = new URIBuilder(String.valueOf(urls[0]));
                    Log.i("messagea2", "ok");
                    // Request parameters. All of them are optional.
                    builder.setParameter("returnFaceId", "true");
                    builder.setParameter("returnFaceLandmarks", "false");
                    builder.setParameter("returnFaceAttributes", faceAttributes);
                    Log.i("messagea23", "ok");
                    // Prepare the URI for the REST API call.
                    URI uri = builder.build();
                    HttpPost request = new HttpPost(uri);

                    // Request headers.
                    request.setHeader("Content-Type", "application/json");
                    request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

                    // Request body.
                    StringEntity reqEntity = new StringEntity(imageWithFaces);
                    Log.i("messageab", imageWithFaces);
                    request.setEntity(reqEntity);
                    Log.i("messagea3", request.toString());
                    // Execute the REST API call and get the response entity.
                    HttpResponse response = httpclient.execute(request);
                    HttpEntity entity = response.getEntity();

                    //JSON 응답 구문문
                    if (entity != null) {
                        // Format and display the JSON response.
                        System.out.println("REST Response:\n");

                        String jsonString = EntityUtils.toString(entity).trim();
                        if (jsonString.charAt(0) == '[') {
                            JSONArray jsonArray = new JSONArray(jsonString);
                            result = String.valueOf(jsonArray);
                            System.out.println(jsonArray.toString(2));
                            Log.i("messagea4", jsonArray.toString());
                        } else if (jsonString.charAt(0) == '{') {
                            JSONObject jsonObject = new JSONObject(jsonString);
                            System.out.println(jsonObject.toString(2));
                            Log.i("messagea4", jsonObject.toString());
                        } else {
                            System.out.println(jsonString);
                        }
                    }
                } catch (Exception e) {
                    // Display error message.
                    System.out.println(e.getMessage());
                }

                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result == null){
                    Log.i("감정이다이쉐캬야", "noDATA");
                }else{
                    Log.i("감정이다이쉐캬야", result);
                }

                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String faceAttributes = jsonObject.optString("faceAttributes");
                    Log.i("감정이다이쉐캬야", faceAttributes);

                    JSONObject faceAttribute = new JSONObject(faceAttributes);

                    JSONObject emotion = faceAttribute.optJSONObject("emotion");
                    score = emotion.optDouble(emo[0]);
                    Log.i("감정이다이쉐캬야s", String.valueOf(score));

                    for (int i=0; i<emotion.length(); i++){

                        emoScroe[i] = emotion.optDouble(emo[i]);

                        Double temp = emotion.optDouble(emo[i]);

                        Log.i("감정이다이쉐캬야", String.valueOf(temp)+"/"+String.valueOf(i));
                        if (temp > score){
                            score = temp;
                            emotionKey = emo[i];
                            Log.i("haaapppp", emotionKey);
                        }
                    }

                    if (emotionKey.equals("surprise")){
                        Double score2 = emoScroe[0];
                        String emo2 = emo[0];
                        for (int j=1; j<emoScroe.length-1; j++ ){
                            Double temp2 = emoScroe[j];
                            if (temp2 > score2){
                                score2 = temp2;
                                emo2 = emo[j];
                            }
                        }
                        if (emo2.equals("happiness")) {
                            Log.i("haapiness", emotionKey);
                        }else if (emo2.equals("neutral")){
                            score = 0.0;
                        }else{
                            score = score2*-1;
                        }
                    }else if (emotionKey.equals("neutral")){
                        score = 0.0;
                    }else if (!emotionKey.equals("happiness")){
                        score = score * -1;
                    }else {
                        Log.i("haapiness", emotionKey);
                    }

                    sentiment_historyHelper = new Sentiment_HistoryHelper(context, "sentiment", null, 1);
                    long now = System.currentTimeMillis();
                    sentiment_historyHelper.insert(score, now);
                    Log.i("감정이다이궤ㅐ", emotionKey+" : "+String.valueOf(score));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }.execute(uriBase);
    }
}
