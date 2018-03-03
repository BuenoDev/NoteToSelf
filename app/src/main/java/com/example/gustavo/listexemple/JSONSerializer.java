package com.example.gustavo.listexemple;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by gustavo on 02/03/18.
 */

public class JSONSerializer {
    private String filename;
    private Context context;

    public JSONSerializer(String fn, Context cn){
        this.filename = fn;
        this.context = cn;
    }

    public void SaveData(ArrayList<ListData> dataArray) throws IOException, JSONException {

        JSONArray jsonArray = new JSONArray();

        for(ListData data : dataArray) jsonArray.put(data.ConvertToJSON());

        Writer writer = null;
        try{
            OutputStream outputStream = context.openFileOutput(filename,context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            writer.write(jsonArray.toString());

        }finally{
            if(writer != null) writer.close();
        }
    }

    public ArrayList<ListData> LoadData() throws IOException {
        //TODO Create load data serializer
        ArrayList<ListData> listData = new ArrayList<ListData>();
            try{
                InputStream inputStream = context.openFileInput(filename);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder jsonString = new StringBuilder();
                String line;

                while((line = reader.readLine())!= null) jsonString.append(line);

                JSONArray jArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
                for(int i=0; i< jArray.length();i++){
                    listData.add(new ListData());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return listData;
    }
}
