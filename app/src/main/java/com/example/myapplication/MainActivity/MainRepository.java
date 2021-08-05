package com.example.myapplication.MainActivity;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.OrdersAdapter;
import com.example.myapplication.model.OrdersData;
import com.example.myapplication.api.InterfaceAPI;
import com.example.myapplication.contract.MainContract;
import com.example.myapplication.model.UserData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRepository implements MainContract.Repository {

    private static final String TAG = "MainRepository";

    @Override
    public void getOrdersResponse(MainContract.View mView,String username){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://courier110.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        InterfaceAPI requestInteface=retrofit.create(InterfaceAPI.class);
        Call<List<OrdersData>> call = requestInteface.getOrdersJson();

        call.enqueue(new Callback<List<OrdersData>>() {
            @Override
            public void onResponse(Call<List<OrdersData>> call, Response<List<OrdersData>> response) {
                mView.onSuccess(response.body(),username);
            }

            @Override
            public void onFailure(Call<List<OrdersData>> call, Throwable t) {
                System.out.println("Error  "+t);
            }
        });
    }

    @Override
    public void getUserData(MainContract.View mView,String username) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://courier110.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        InterfaceAPI requestInteface=retrofit.create(InterfaceAPI.class);
        Call<List<UserData>> call = requestInteface.getUserJson(username);

        call.enqueue(new Callback<List<UserData>>() {
            @Override
            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                System.out.println("ABOBA" + response.body());
                System.out.println("ABOBA2"+response.body().get(0).getFirstName());
                mView.onSuccessUser(response.body());

            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                System.out.println("HUI Error  "+t);
            }
        });
    }
}
