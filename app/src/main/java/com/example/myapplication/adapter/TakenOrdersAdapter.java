package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.infoactivity.InfoActivityLease;
import com.example.myapplication.model.OrdersData;
import com.example.myapplication.R;

import java.util.ArrayList;

public class TakenOrdersAdapter extends RecyclerView.Adapter<TakenOrdersAdapter.ViewHolder>{
    private ArrayList<OrdersData> ordersData=new ArrayList<>();
    private Context context;
    private String username;

    public TakenOrdersAdapter(Context context, ArrayList<OrdersData> ordersData,String username){
        this.ordersData=ordersData;
        this.context=context;
        this.username=username;
    }

    @NonNull
    @Override
    public TakenOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orders_list_item,viewGroup,false);
        return new TakenOrdersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TakenOrdersAdapter.ViewHolder viewholder, int i) {
        viewholder.cost.setText(ordersData.get(i).getCost());
        viewholder.id_order.setText(ordersData.get(i).getId());
        viewholder.address.setText(ordersData.get(i).getOutAddress());
        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ЭЛЕМЕНТ: "+ ordersData.get(i).getCost());
                String id = ordersData.get(i).getId();
                String description = ordersData.get(i).getDescription();
                String in_address = ordersData.get(i).getInAddress();
                String out_address = ordersData.get(i).getOutAddress();
                String cost = ordersData.get(i).getCost();
                Intent intent = new Intent(context, InfoActivityLease.class);

                intent.putExtra("id", id);
                intent.putExtra("description", description);
                intent.putExtra("in_address", in_address);
                intent.putExtra("out_address", out_address);
                intent.putExtra("cost", cost);
                System.out.println("HUILA1" + username);
                intent.putExtra("username", username);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cost,id_order,address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cost = (TextView)itemView.findViewById(R.id.id_cost);
            id_order = (TextView)itemView.findViewById(R.id.id_order);
            address = (TextView)itemView.findViewById(R.id.address);
        }
    }
}
