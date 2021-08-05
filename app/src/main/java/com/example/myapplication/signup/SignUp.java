package com.example.myapplication.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.login.Login;
import com.example.myapplication.contract.MainContract;
import com.google.android.material.textfield.TextInputEditText;

import com.example.myapplication.R;

public class SignUp extends AppCompatActivity implements MainContract.SignUpView {
    TextInputEditText textInputEditTextFirst_name,textInputEditTextSec_name,textInputEditTextUsername,textInputEditTextPassword,textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    String username,first_name,sec_name,password,email;

    private MainContract.SignUpPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextFirst_name = findViewById(R.id.firstname);
        textInputEditTextSec_name = findViewById(R.id.secname);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        mPresenter = new SignUpPresenter(this);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = textInputEditTextUsername.getText().toString();
                password = textInputEditTextPassword.getText().toString();
                first_name = textInputEditTextFirst_name.getText().toString();
                sec_name = textInputEditTextSec_name.getText().toString();
                email = textInputEditTextEmail.getText().toString();

                mPresenter.btn_pressed(first_name,sec_name,username,password,email,SignUp.this);


//                String regToken = createAuthToken(first_name,sec_name,username,password,email);
//                registrationDetails(regToken);
            }
        });
    }
//    private void registrationDetails(String regToken) {
//        Retrofit retrofit = RetrofitInstance.getRetrofit();
//        final InterfaceAPI api = retrofit.create(InterfaceAPI.class);
//
//        Call<String> call = api.registration(regToken);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()){
//                    if (response.body().matches("Success2")){
//                        Toast.makeText(getApplicationContext(),"SuccessSUKA",Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(getApplicationContext(),Login.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                    else {
//                        if (response.body().matches("Wrong")){
//                            Toast.makeText(getApplicationContext(),"Username or Password wrong",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.e("TAG", t.toString());
//                t.printStackTrace();
//
//            }
//        });
//    }

//    private String createAuthToken(String first_name, String sec_name,String username, String password, String email) {
//        byte [] data = new byte[0];
//        data = (first_name + ":" + sec_name + ":" + username + ":" + password + ":" + email).getBytes(StandardCharsets.UTF_8);
//        return Base64.encodeToString(data,Base64.NO_WRAP);
//    }
}
