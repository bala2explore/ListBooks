package com.bala.listbooks.data;

import com.bala.listbooks.domain.BookParser;
import com.bala.listbooks.data.response.APIResponse;
import com.bala.listbooks.data.response.error.ErrorResponse;
import com.bala.listbooks.ui.model.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BookRepository {

    public Result<List<Book>> makeSynchronousRequest(String urlstring) {
        String response = "";
        Gson gson = new GsonBuilder().serializeNulls().create();
        boolean isError = true;
        try {
            URL url = new URL(urlstring);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");

            InputStream is = httpConnection.getErrorStream();
            if (is == null) {
                isError = false;
                is = httpConnection.getInputStream();
            }
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int length; (length = is.read(buffer)) != -1; ) {
                result.write(buffer, 0, length);
            }
            response =  result.toString("UTF-8");

            if(isError){
                ErrorResponse errorResponse = gson.fromJson(response,ErrorResponse.class);
                return new Result.Error<>(errorResponse.fault.faultstring);
            }
            else {
                JSONObject responseJson = new JSONObject(response);
                int bookCount = responseJson.getInt("num_results");
                if(bookCount>0) {
                    APIResponse apiResponse = gson.fromJson(response, APIResponse.class);
                    List<Book> bookList = BookParser.parseBooks(apiResponse);
                    return new Result.Success<List<Book>>(bookList);
                }
                else{
                    return new Result.Error<>("No Books found in the selected date.");
                }
            }
        } catch (Exception e) {
            if(!response.isEmpty()){
                ErrorResponse errorResponse = gson.fromJson(response,ErrorResponse.class);
                return new Result.Error<>("UnExpected Error, Please try again later.");
            }
            return new Result.Error<>("UnExpected Error, Please try again later.");
        }
    }

}