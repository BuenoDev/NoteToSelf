package com.example.gustavo.listexemple;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.util.Date;


/**
 * Created by gustavo on 01/03/18.
 */

class ListData {
    public String timestamp;
    static int counter = 0;

    ListData(){
        timestamp = ""+counter;
        counter ++;
    }
    ListData(JSONObject jObj){
        try {
            timestamp = jObj.getString("timestamp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject ConvertToJSON() throws JSONException{
        JSONObject object = new JSONObject();

        object.put("timestamp",this.timestamp);

        return object;
    }
}
