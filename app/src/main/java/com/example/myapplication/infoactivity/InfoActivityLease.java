package com.example.myapplication.infoactivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DataParser;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitInstance;
import com.example.myapplication.api.InterfaceAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoActivityLease extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    MarkerOptions origin, destination;


    WebView webView;
    String tmp;
    TextView usernameTxt;
    Button btn_reserv;
    TextView header,in_add, out_add, desc;
    TextView TVcost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_lease);
        webView = findViewById(R.id.webView);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String description = intent.getStringExtra("description");

        String in_address = intent.getStringExtra("in_address");
        String out_address = intent.getStringExtra("out_address");
        String cost = intent.getStringExtra("cost");
//        tmp = " id: "+id + "<br><br>" + " Описание: " +description + "<br><br>"+" Куда: "+in_address + "<br><br>"+" Откуда: "+out_address + "<br><br>"+" Стоимость заказа: "+cost + "<br><br>";

//        webView.loadData(tmp,"text/html; charset=UTF-8", null);
        header = (TextView) findViewById(R.id.header);
        header.setText("№ "+id + "(Выполняется)");

        TVcost = (TextView) findViewById(R.id.cost);
        TVcost.setText(cost+"Р");

        in_add = (TextView) findViewById(R.id.in_address);
        in_add.setText("Откуда: "+in_address);

        out_add = (TextView) findViewById(R.id.out_address);
        out_add.setText("Куда: " + out_address);

        desc = (TextView) findViewById(R.id.description);
        desc.setText("Описание заказа: " + description);
        //Принимаем ник курьера
        usernameTxt = (TextView) findViewById(R.id.username);
        String username = intent.getStringExtra("username");
        System.out.println("HUILA" + username);
//        usernameTxt.setText(username.toString());
        //Резервирование заказа
        btn_reserv = (Button) findViewById(R.id.btn_reserv);
        btn_reserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Тут надо выполнять запрос на сервер, который забронирует заказ за определенным пользователем.
                //
                String leaseToken = createReservationToken(username,id);
                giveaway_order(leaseToken);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            List<Address> place1 = geocoder(in_address);
            List<Address> place2 = geocoder(out_address);

            if(place1.size() > 0 && place2.size() > 0) {
                double latitude1 = place1.get(0).getLatitude();
                double longitude1 = place1.get(0).getLongitude();
                double latitude2 = place2.get(0).getLatitude();
                double longitude2 = place2.get(0).getLongitude();

                //Setting marker to draw route between these two points
                origin = new MarkerOptions().position(new LatLng(latitude1, longitude1)).title("Начало пути").snippet("origin");
                destination = new MarkerOptions().position(new LatLng(latitude2, longitude2)).title("Конечная цель").snippet("destination");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(origin.getPosition(), destination.getPosition());

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);


    }
    public List<Address> geocoder(String add) throws IOException {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        addresses = geocoder.getFromLocationName(add, 1);
        if(addresses.size() > 0) {
            double latitude= addresses.get(0).getLatitude();
            double longitude= addresses.get(0).getLongitude();
            System.out.println("SUKA x: " + latitude);
            System.out.println("SUKA y: " + longitude);
        }
        return addresses;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(origin);
        mMap.addMarker(destination);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin.getPosition(), 13));
    }
    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }
    /**
     * A class to parse the JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DataParser parser = new DataParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = new ArrayList();
            PolylineOptions lineOptions = new PolylineOptions();

            for (int i = 0; i < result.size(); i++) {

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.parseColor("#53BF24"));
                lineOptions.geodesic(true);

            }

            // Drawing polyline in the Google Map

            if (points.size() != 0)
                mMap.addPolyline(lineOptions);
        }
    }
    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Setting mode
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyCaJkhrPTf4TDErrAJ1dxX9ghU2X9RHOBg";

        return url;
    }
    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    private String createReservationToken(String login, String id) {
        byte [] data = new byte[0];
        data = (login + ":" + id).getBytes(StandardCharsets.UTF_8);
        System.out.println(data);
        return Base64.encodeToString(data,Base64.NO_WRAP);
    }
    private void giveaway_order(String leaseToken) {
        Retrofit retrofit = RetrofitInstance.getRetrofit();
        final InterfaceAPI api = retrofit.create(InterfaceAPI.class);

        Call<String> call = api.giveOrder(leaseToken);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    System.out.println("HUI "+response.body());
                    if (response.body().matches("Success")){
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                        new CountDownTimer(2000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                finish();
                            }
                        }.start();
                    }
                    else {
                        if (response.body().matches("Wrong")){
                            Toast.makeText(getApplicationContext(),"Заказ уже занят",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("TAG", t.toString());
                t.printStackTrace();

            }
        });
    }
}
