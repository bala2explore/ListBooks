package com.bala.listbooks.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultDataModel {

    public String bestsellers_date;
    public String published_date;
    public String published_date_description;
    public String previous_published_date;
    public String next_published_date;
    @SerializedName("lists")
    public List<ListDataModel> lists;

}
