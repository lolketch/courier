package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdersData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("in_address")
    @Expose
    private String inAddress;
    @SerializedName("out_address")
    @Expose
    private String outAddress;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = 3988392566536061942L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInAddress() {
        return inAddress;
    }

    public void setInAddress(String inAddress) {
        this.inAddress = inAddress;
    }

    public String getOutAddress() {
        return outAddress;
    }

    public void setOutAddress(String outAddress) {
        this.outAddress = outAddress;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
