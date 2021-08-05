package com.example.myapplication.signup;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.RetrofitInstance;
import com.example.myapplication.login.Login;
import com.example.myapplication.api.InterfaceAPI;
import com.example.myapplication.contract.MainContract;

import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpRepository implements MainContract.SignUpRepository {

    private static final String TAG = "MainRepository";

    @Override
    public void registrationDetails(String regToken, MainContract.SignUpView mView, Context context) {
        Retrofit retrofit = RetrofitInstance.getRetrofit();
        final InterfaceAPI api = retrofit.create(InterfaceAPI.class);

        Call<String> call = api.registration(regToken);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    if (response.body().matches("Success2")){
                        Toast.makeText(context,"SuccessSUKA",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, Login.class);
                        context.startActivity(intent);
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
    public String createAuthToken(String first_name, String sec_name,String username, String password, String email) {
        byte [] data = new byte[0];
        data = (first_name + ":" + sec_name + ":" + username + ":" + password + ":" + email).getBytes(StandardCharsets.UTF_8);
        return Base64.encodeToString(data,Base64.NO_WRAP);
    }
}
