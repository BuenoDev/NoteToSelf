package com.example.gustavo.listexemple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     *
     * O App deverá, ao se apertar o botão, popular o listData view com a data e hora que o
     * botao foi pressionado, persistindo os dados em um arquivo JSON
     *
     * */

    private ArrayList<ListData> listData = new ArrayList<ListData>();
    TimeListAdapter adapter = new TimeListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnAdd = (Button) findViewById(R.id.button);
        ListView list = (ListView) findViewById(R.id.listView);

        list.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getList().add(new ListData());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onPause() {
        try {
            adapter.saveList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPause();
        //TODO save data in listAdapter

    }

    @Override
    protected void onResume() {
        try {
            adapter.loadList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onResume();

        //TODO load data into listData adapter
    }


    //inner adapter class
    public class TimeListAdapter extends BaseAdapter{

        JSONSerializer serializer = new JSONSerializer("JSONFile.json",getApplicationContext());

        public ArrayList<ListData> getList(){
            return listData;
        }

        public void saveList() throws IOException, JSONException {
            serializer.SaveData(this.getList());
        }
        public ArrayList<ListData> loadList() throws IOException {
            return serializer.LoadData();
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_layout,parent,false);

            TextView text = (TextView) convertView.findViewById(R.id.txtTime);
            ImageView btnDel = (ImageView) convertView.findViewById(R.id.btnDel);

            text.setText((CharSequence) listData.get(position).timestamp.toString());

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO apagar item da lista
                    listData.remove(position);

                }
            });

            return convertView;
        }
    }
}
