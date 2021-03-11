package com.bala.listbooks.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookDataModel{
    public String age_group;
    public String amazon_product_url;
    public String article_chapter_link;
    public String author;
    public String book_image;
    public int book_image_width;
    public int book_image_height;
    public String book_review_link;
    public String contributor;
    public String contributor_note;
    public String created_date;
    public String description;
    public String first_chapter_link;
    public String price;
    public String primary_isbn10;
    public String primary_isbn13;
    public String book_uri;
    public String publisher;
    public int rank;
    public int rank_last_week;
    public String sunday_review_link;
    public String title;
    public String updated_date;
    public int weeks_on_list;
    @SerializedName("buy_links")
    public List<BuyLinkDataModel> buy_links;
}