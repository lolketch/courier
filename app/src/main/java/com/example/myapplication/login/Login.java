package com.example.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.signup.SignUp;
import com.example.myapplication.contract.MainContract;
import com.example.myapplication.model.OrdersData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import com.example.myapplication.R;

public class Login extends AppCompatActivity implements MainContract.LoginView {
    TextInputEditText textInputEditTextUsername,textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;
    String username,password;

    private MainContract.LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        mPresenter = new LoginPresenter(this);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = textInputEditTextUsername.getText().toString();
                password = textInputEditTextPassword.getText().toString();
                mPresenter.btn_pressed(username,password,Login.this);
            }
        });
    }


    @Override
    public void onSuccess(List<OrdersData> data) {

    }
}
