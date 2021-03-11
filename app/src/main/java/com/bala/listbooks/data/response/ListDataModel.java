package com.bala.listbooks.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListDataModel {

    @SerializedName("list_id")
    public int list_id;

    @SerializedName("list_name")
    public String list_name;

    @SerializedName("list_name_encoded")
    public String list_name_encoded;

    @SerializedName("display_name")
    public String display_name;

    @SerializedName("updated")
    public String updated;

    @SerializedName("list_image")
    public String list_image;

    @SerializedName("list_image_width")
    public int list_image_width;

    @SerializedName("list_image_height")
    public int list_image_height;

    @SerializedName("books")
    public List<BookDataModel> books;
}
