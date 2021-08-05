package com.example.myapplication.login;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.contract.MainContract;

public class LoginPresenter implements MainContract.LoginPresenter {
    private static final String TAG = "MainPresenter";

    //Компоненты MVP приложения
    private MainContract.LoginView mView;
    private MainContract.LoginRepository mRepository;

    //Обратите внимание на аргументы конструктора - мы передаем экземпляр View
    public LoginPresenter(MainContract.LoginView mView) {
        this.mView = mView;
        this.mRepository = new LoginRepository();
        Log.d(TAG, "Constructor");
    }

    @Override
    public void activityOnCreate(){
    }

    @Override
    public void btn_pressed(String username,String password, Context context){
        String authToken = mRepository.createAuthToken(username,password);
        mRepository.checkLoginDetails(authToken,mView,context,username);
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
