package com.example.myapplication.signup;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.contract.MainContract;

public class SignUpPresenter implements MainContract.SignUpPresenter {
    private static final String TAG = "MainPresenter";

    //Компоненты MVP приложения
    private MainContract.SignUpView mView;
    private MainContract.SignUpRepository mRepository;

    //Обратите внимание на аргументы конструктора - мы передаем экземпляр View
    public SignUpPresenter(MainContract.SignUpView mView) {
        this.mView = mView;
        this.mRepository = new SignUpRepository();
        Log.d(TAG, "Constructor");
    }

    @Override
    public void activityOnCreate(){
    }

    @Override
    public void btn_pressed(String first_name,String sec_name,String username,String password,String email, Context context){
        String regToken = mRepository.createAuthToken(first_name,sec_name,username,password,email);
        mRepository.registrationDetails(regToken,mView,context);
    }



    @Override
    public void onDestroy() {
        /**
         * Если бы мы работали например с RxJava, в этом классе стоило бы отписываться от подписок
         * Кроме того, при работе с другими методами асинхронного андроида здесь мы боремся с утечкой контекста
         */

        Log.d(TAG, "onDestroy()");
    }
}
