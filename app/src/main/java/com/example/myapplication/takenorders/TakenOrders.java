package com.example.myapplication.takenorders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.MainActivity.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.TakenOrdersAdapter;
import com.example.myapplication.contract.MainContract;
import com.example.myapplication.model.OrdersData;

import java.util.ArrayList;
import java.util.List;

public class TakenOrders extends AppCompatActivity implements MainContract.TakenView{
    ListView listView;
    TextView usernameTxt;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private MainContract.TakenPresenter mPresenter;


    ArrayList<OrdersData> ordersData=new ArrayList<>();
    private TakenOrdersAdapter TakenOrdersAdapter;
    private RecyclerView orders_recyclerview;
    String username;
    Button btn_free_orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taken_orders);

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
                        mPresenter = new TakenPresenter(TakenOrders.this,username);
                        mPresenter.activityOnCreate();
                        System.out.println("REFRESH");
                    }
                }, 0);
            }
        });
        orders_recyclerview = (RecyclerView)findViewById(R.id.orders_recyclerview);

//        usernameTxt = (TextView) findViewById(R.id.username);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        System.out.println("ABAS3" + username);
        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс MainContract.View
        mPresenter = new TakenPresenter(this,username);
        mPresenter.activityOnCreate();
//        usernameTxt.setText(username.toString());

        btn_free_orders = (Button)findViewById(R.id.btn_free_orders);
        btn_free_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onSuccess(List<OrdersData> data) {
        ordersData=new ArrayList<>(data);
        System.out.println("pidor" + username);
        TakenOrdersAdapter=new TakenOrdersAdapter(TakenOrders.this,ordersData,username);
        orders_recyclerview.setAdapter(TakenOrdersAdapter);
        orders_recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}
