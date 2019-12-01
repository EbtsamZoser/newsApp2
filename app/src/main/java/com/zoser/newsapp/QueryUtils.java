package com.zoser.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    private static List<NewsClass> fetchNews(Context context, String requeredUrl) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            Log.e(TAG, "fetchNews: enterapted" + ie);
        } //url opject
        URL newsurl = createUrl(requeredUrl);
        //httpreq
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(newsurl);
        } catch (IOException ioe) {
            Log.e(TAG, "fetchNews: problem of making http", ioe);
        }
        List<NewsClass> gNews;
        gNews = extractNewsFromJson(jsonResponse);
        return gNews;
    }


    private static List<NewsClass> extractNewsFromJson(
            String jsonResponse) {


        if (jsonResponse == null) return null;
        ArrayList<NewsClass> gNews = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject element = results.getJSONObject(i);
                JSONObject fields = element.getJSONObject("fields");
                String imUrl = fields.getString("thumbnail");
                Bitmap image = extractImage(imUrl);
                gNews.add(new NewsClass(
                        element.getString("webTitle"),
                        element.getString("type"),
                        element.getString("webUrl"),
                        element.getString("webPublicationDate"),
                        element.getString("sectionName"),
                        image
                ));
            }

        } catch (JSONException je) {
            Log.e(TAG, "json news proplem parsing", je);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return gNews;

    }

    private static Bitmap extractImage(String imUrl) throws IOException {
        URL urlObj = createUrl(imUrl);
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        Bitmap image = null;
        if (urlObj != null) {
            urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                image = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            }
            urlConnection.disconnect();
        }
        return image;
    }

    private static String makeHttpRequest(URL newsurl) throws IOException {
        String jsonResponse = "";
        if (newsurl == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) newsurl.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            //200 mean valid right true
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readStream(inputStream);
            } else {
                Log.e(TAG, "makehttp:error code" + urlConnection.getResponseCode());
            }
        } catch (IOException ioe) {
            Log.e(TAG, "http: can not got json", ioe);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }


    private static URL createUrl(String requeredUrl) {
        URL url = null;
        try {
            url = new URL(requeredUrl);
        } catch (MalformedURLException mue) {
            Log.e(TAG, "creat url: proplem url", mue);
        }
        return url;
    }


}

