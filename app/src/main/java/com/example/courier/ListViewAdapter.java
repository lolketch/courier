package com.example.courier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewAdapter  extends ArrayAdapter<JSONObject> {
    int listLayout;
    ArrayList<JSONObject> OrdersList;
    Context context;

    public ListViewAdapter(Context context, int listLayout , int field, ArrayList<JSONObject> OrdersList) {
        super(context, listLayout, field, OrdersList);
        this.context = context;
        this.listLayout=listLayout;
        this.OrdersList = OrdersList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(listLayout, null, false);
        TextView id = listViewItem.findViewById(R.id.textViewName);
        TextView description = listViewItem.findViewById(R.id.textViewEmail);
        try{
            id.setText(OrdersList.get(position).getString("id"));
            description.setText(OrdersList.get(position).getString("description"));
        }catch (JSONException je){
            je.printStackTrace();
        }
        return listViewItem;
    }


}
