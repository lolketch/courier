package com.example.myapplication.takenorders;

import com.example.myapplication.api.InterfaceAPI;
import com.example.myapplication.contract.MainContract;
import com.example.myapplication.model.OrdersData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TakenRepository implements MainContract.TakenRepository {

    private static final String TAG = "TakenRepository";

    @Override
    public void getOrdersResponse(MainContract.TakenView mView,String usernameToken){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://courier110.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        InterfaceAPI requestInteface=retrofit.create(InterfaceAPI.class);
        Call<List<OrdersData>> call = requestInteface.getTakenOrdersJson(usernameToken);

        call.enqueue(new Callback<List<OrdersData>>() {
            @Override
            public void onResponse(Call<List<OrdersData>> call, Response<List<OrdersData>> response) {
                mView.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<OrdersData>> call, Throwable t) {
                System.out.println("HUI Error  "+t);
            }
        });
    }
}
