package com.example.myapplication.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.takenorders.TakenOrders;
import com.example.myapplication.contract.MainContract;
import com.example.myapplication.model.UserData;
import com.example.myapplication.adapter.OrdersAdapter;
import com.example.myapplication.model.OrdersData;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View  {
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static final String TAG = "MainActivity";
    private MainContract.Presenter mPresenter;

    private Button btn_busy_orders;


    ArrayList<OrdersData> ordersData=new ArrayList<>();
    ArrayList<UserData> userData=new ArrayList<>();
    private OrdersAdapter ordersAdapter;
    private RecyclerView orders_recyclerview;
    String username;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Отменяем анимацию обновления
                        mSwipeRefreshLayout.setRefreshing(false);
                        mPresenter = new MainPresenter(MainActivity.this,username);
                        mPresenter.activityOnCreate();
                        System.out.println("REFRESH");
                    }
                }, 0);
            }
        });

        orders_recyclerview = (RecyclerView)findViewById(R.id.orders_recyclerview);
        btn_busy_orders = (Button)findViewById(R.id.btn_taken_orders);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс MainContract.View
        mPresenter = new MainPresenter(this,username);
        mPresenter.activityOnCreate();


        btn_busy_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String username = "marley";
                Intent intent = new Intent(getApplicationContext(), TakenOrders.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSuccess(List<OrdersData> data,String username) {
        ordersData=new ArrayList<>(data);
        ordersAdapter=new OrdersAdapter(MainActivity.this,ordersData,username);
        orders_recyclerview.setAdapter(ordersAdapter);
        orders_recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public List<UserData> onSuccessUser(List<UserData> data) {
        userData = new ArrayList<>(data);
        return userData;
    }

}