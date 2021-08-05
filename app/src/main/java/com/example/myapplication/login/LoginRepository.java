package com.example.myapplication.login;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.RetrofitInstance;
import com.example.myapplication.MainActivity.MainActivity;
import com.example.myapplication.api.InterfaceAPI;
import com.example.myapplication.contract.MainContract;

import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRepository implements MainContract.LoginRepository {

    private static final String TAG = "MainRepository";

    @Override
    public void checkLoginDetails(String authToken, MainContract.LoginView mView, Context context,String username){
        Retrofit retrofit = RetrofitInstance.getRetrofit();
        final InterfaceAPI api = retrofit.create(InterfaceAPI.class);

        Call<String> call = api.checkLogin(authToken);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    if (response.body().matches("success")){
                        Toast.makeText(context,"Login success",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("username",username);
                        context.startActivity(intent);
//                        context.finish();
                    }
                    else {
                        if (response.body().matches("Wrong")){
                            Toast.makeText(context,"Username or Password wrong",Toast.LENGTH_LONG).show();
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

    @Override
    public String createAuthToken(String login, String password) {
        byte [] data = new byte[0];
        data = (login + ":" + password).getBytes(StandardCharsets.UTF_8);
        System.out.println(data);
        return Base64.encodeToString(data,Base64.NO_WRAP);
    }
}
