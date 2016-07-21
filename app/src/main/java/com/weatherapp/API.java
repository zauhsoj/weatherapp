package com.weatherapp;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Eugene on 12-07-2016.
 */
public class API {
    public static String call(String Url, HashMap map, boolean isGet) {

        try {
            String parameters = "";

            if (map != null) {
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry pair = (Map.Entry) iterator.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());
                    if (isGet)
                        parameters += "&" + pair.getKey() + "=" + URLEncoder.encode(pair.getValue().toString(), "UTF-8");
                    else
                        parameters += "&" + pair.getKey() + "=" + pair.getValue();
                    iterator.remove(); // avoids a ConcurrentModificationException
                }
            }
            if (parameters.length() > 0) {
                parameters = parameters.substring(1, parameters.length());
            }
            URL url;
            if (isGet) {
                url = new URL(Url + parameters);
            } else {
                url = new URL(Url);
            }
            Log.i("URL", url.toString());

            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            Integer result = 0;
                /* forming th java.net.URL object */
            urlConnection = (HttpURLConnection) url.openConnection();
                 /* optional request header */
            //urlConnection.setRequestProperty("Content-Type", "application/json");
                /* optional request header */
            //urlConnection.setRequestProperty("Accept", "application/json");
                /* for Get request */
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");

            if (isGet) {
                urlConnection.setRequestMethod("GET");
            } else {
                urlConnection.setRequestMethod("POST");
            }
            Log.i("request", urlConnection.getRequestMethod());
                /* header */
            //urlConnection.setRequestProperty("Authorization", "Bearer " + storeUserData.getString(Constants.USER_TOKEN));
            urlConnection.setConnectTimeout(5000);
            /*add parameters*/
            String charset = "UTF-8";
            Log.i("parameters", parameters);
            if (isGet) {
                urlConnection.setDoOutput(false);
            } else {
                urlConnection.setDoOutput(true);
            }
            if (!isGet) {
                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(parameters);
                wr.flush();
                wr.close();
            }
            int statusCode = urlConnection.getResponseCode();
            String response = "";
                /* 200 represents HTTP OK */
            if (statusCode >= 400 && statusCode < 500) {
                inputStream = urlConnection.getErrorStream();
                response = convertInputStreamToString(inputStream);
                Log.i("exception from API", "" + response);
            }
            Log.i("API Result", statusCode + "");
            if (statusCode == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                response = convertInputStreamToString(inputStream);
                Log.i("response result", response);
                result = 1; // Successful
            } else {
                result = 0; //"Failed to fetch data!";
            }
            Log.i("result", result + "");
            return response; //"Failed to fetch data!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        if (null != inputStream) {
            inputStream.close();
        }
        return result;
    }
}
