package com.example.courier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    String[] numa = {"abva"};
//    private TextView today_text;
    private static final String JSON_URL = "https://courier110.000webhostapp.com/info.php";
    ListView listView;
    ArrayList<JSONObject> infoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ListView listView = (ListView)findViewById(R.id.listview);
//
////        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,numa);
////        listView.setAdapter(adapter);
//
//        today_text = (TextView) findViewById(R.id.today_text_view);
//        new GetData(this, today_text).execute();
        listView = (ListView) findViewById(R.id.listView);
        loadJSONFromURL(JSON_URL);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String _id = infoList.get(position).optString("id");
                String description = infoList.get(position).optString("description");
                String in_address = infoList.get(position).optString("in_address");
                String out_address = infoList.get(position).optString("out_address");
                String cost = infoList.get(position).optString("cost");
                //Toast.makeText(getApplicationContext(), nameEx ,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);

                intent.putExtra("id", _id);
                intent.putExtra("description", description);
                intent.putExtra("in_address", in_address);
                intent.putExtra("out_address", out_address);
                intent.putExtra("cost", cost);
                startActivity(intent);

            }
        });
    }

private void  loadJSONFromURL(String url){
    final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
    progressBar.setVisibility(ListView.VISIBLE);
    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray("orders");
                        ArrayList<JSONObject> listItems = getArrayListFromJSONArray(jsonArray);
                        infoList = listItems;
                        ListAdapter adapter = new ListViewAdapter(getApplicationContext(),R.layout.row,R.id.textViewName,listItems);
                        listView.setAdapter(adapter);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}

    private ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray){
        ArrayList<JSONObject> aList = new ArrayList<JSONObject>();
        try {
            if(jsonArray!= null){
                for(int i = 0; i<jsonArray.length();i++){
                    aList.add(jsonArray.getJSONObject(i));
                }
            }
        }catch (JSONException js){
            js.printStackTrace();
        }
        return aList;
    }

    public  static  String EncodingToUTF8(String response){
        try {
            byte[] code = response.toString().getBytes("ISO-8859-1");
            response = new String(code, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
        return response;
    }
}