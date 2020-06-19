package com.example.trackstat;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}
//https://api.covid19api.com/live/country/south-africa/status/confirmed
//base url
    public static final String BASE_API_URL =
        "https://corona.lmao.ninja/v2/countries/";

    //a function that helps create a proper url
    //function return a url
    public static URL buildRawUrl(){
        //add the remainder of the url
        String allCountryFullUrl = BASE_API_URL;
        //declare a url and convert the above string into a url
        URL url = null;
        //build url in a try, catch block
        try {
            url = new URL(allCountryFullUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public static final String QUARY_PARAMETER_KEY_ONE= "yesterday";
    public static final String QUARY_PARAMETER_KEY_TWO = "sort";



    //to use a URI Builder instead of a URL
    public static URL buildBetterUrl(String title, String sort){
        //create a URI
        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()   //the parse method convert a string into a URI
                .appendQueryParameter(QUARY_PARAMETER_KEY_ONE,title)
                .appendQueryParameter(QUARY_PARAMETER_KEY_TWO,sort)
                .build();
        //build url in a try, catch block
        try {
            //lets try to convert the URI to a URL
            url = new URL(uri.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    //now connect to the API with the url
    //create a mtd to connect
    public static String getJason(URL url) throws IOException {
        //create the connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            //now that we have connection let's read data
            InputStream stream = connection.getInputStream();
            //convert stream to string using a scanner
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("//a"); //the //a means read all data
            //check if we have data
            boolean hasData = scanner.hasNext();
            if (hasData){
                return scanner.next();
            }else{
                return null;
            }
        }catch (Exception e){
            Log.d("Error", e.toString());
            return null;
        }
        //close connection
        finally {
            connection.disconnect();
        }

    }


    // a function that will return an array list of countries
    public static ArrayList<Countries> getCountryFromJson(String json){
        //got practice, create constants for the data we want to retrieve
        final String COUNTRY_NAME = "country";
        final String TOTAL_CASES = "cases";
        final String RECOVERED = "recovered";
        final String DEATHS = "deaths";

        ArrayList<Countries> countries = new ArrayList<Countries>();
        try {
            JSONArray arrayJson = new JSONArray(json);
            //count number of returned countries
            int numberOfCountries = arrayJson.length();
            for (int i = 0; i < numberOfCountries; i++) {
                JSONObject countryJson = arrayJson.getJSONObject(i);
                //to retrieve all the json we need
                Countries countries1 = new Countries(countryJson.getString(COUNTRY_NAME),
                        countryJson.getString(TOTAL_CASES),
                        countryJson.getString(DEATHS),
                        countryJson.getString(RECOVERED));
                countries.add(countries1);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return countries;
    }

}
