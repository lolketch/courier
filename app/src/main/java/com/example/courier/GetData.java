package com.example.courier;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class GetData extends AsyncTask<String, Void, String> {
    private Context context;
    private TextView textView;

    public GetData(Context context, TextView textView){
        this.context = context;
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String link = "https://courier110.000webhostapp.com/GetData.php";
//            String data = URLEncoder.encode("UTF-8")
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//            wr.write();
//            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();

        }catch (Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }
    protected void onPostExecute(String result) {
        this.textView.setText(result);
    }
}
