package com.bala.listbooks.data.response;

import com.google.gson.annotations.SerializedName;

public class APIResponse {
    public String status;
    public String copyright;
    public int num_results;
    @SerializedName("results")
    public ResultDataModel results;
}
