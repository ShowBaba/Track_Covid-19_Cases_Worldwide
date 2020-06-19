package com.example.trackstat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetJSONData {

    public static String getCountryName(String json) throws JSONException {
        String countryName = "";
        JSONArray entireArray = new JSONArray(json);
        for (int i=0; i < entireArray.length(); i++){
            JSONObject country = entireArray.getJSONObject(i);
            countryName = country.getString("Country");
        }
        return countryName;
    }

    public static int getTotalCases(String json) throws JSONException{
        //array to hold all the case each day
        ArrayList<Integer> cases = new ArrayList<>();

        JSONArray arrayDays = new JSONArray(json);
        int numOfDys = arrayDays.length();

        //loop through the days array and get the cases
        for (int i=0;i<numOfDys;i++){
            JSONObject caseJson = arrayDays.getJSONObject(i);
            cases.add(caseJson.getInt("Cases"));
        }
//            int latestCase = cases.get(cases.size()-1);
        return cases.get(cases.size()-1);
//        return
    }

}
