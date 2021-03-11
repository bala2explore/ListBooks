package com.bala.listbooks.domain;

import com.bala.listbooks.data.response.APIResponse;
import com.bala.listbooks.data.response.BookDataModel;
import com.bala.listbooks.data.response.ListDataModel;
import com.bala.listbooks.data.response.ResultDataModel;
import com.bala.listbooks.ui.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookParser {


    public static List<Book> parseBooks(APIResponse apiResponse) {
        List<Book> allBooks = new ArrayList<Book>();

        ResultDataModel resultDataModel = apiResponse.results;
        List<ListDataModel> listDataModels = resultDataModel.lists;

        for (ListDataModel listDataModel : listDataModels) {

            List<BookDataModel> bookDataModels = listDataModel.books;
            for (BookDataModel bookDataModel : bookDataModels) {
                allBooks.add(mapBookDataModelToBook(bookDataModel));
            }

        }
        return allBooks;
    }

    public static Book mapBookDataModelToBook(BookDataModel bookDataModel){
        return new Book.BookBuilder()
                .setTitle(bookDataModel.title)
                .setPublisher(bookDataModel.publisher)
                .setDescription(bookDataModel.description)
                .setContributor(bookDataModel.contributor)
                .setAuthor(bookDataModel.author)
                .build();
    }

    // PARSER WITHOUT GSON - USING NATIVE JSONOBJECT AND JSONARRAY
    public static List<Book> parseBooks(String json) {
        List<Book> allBooks = new ArrayList<Book>();

        try {
            JSONObject responseJsonObject = new JSONObject(json);
            JSONObject resultsJsonObject = responseJsonObject.getJSONObject("results");
            JSONArray listsJsonArray = resultsJsonObject.getJSONArray("lists");

            for (int i = 0; i < listsJsonArray.length(); i++) {

                JSONObject booklistJsonObject = listsJsonArray.getJSONObject(i);
                JSONArray books = booklistJsonObject.getJSONArray("books");

                for (int j = 0; j < books.length(); j++) {

                    JSONObject bookJsonObject = books.getJSONObject(j);
                    String title = bookJsonObject.getString("title");
                    String author = bookJsonObject.getString("author");
                    String contributor = bookJsonObject.getString("contributor");
                    String publisher = bookJsonObject.getString("publisher");
                    String description = bookJsonObject.getString("description");

                    Book book = new Book.BookBuilder()
                            .setTitle(title)
                            .setAuthor(author)
                            .setContributor(contributor)
                            .setDescription(description)
                            .setPublisher(publisher)
                            .build();
                    allBooks.add(book);
                }
            }
        } catch (JSONException e) {
            System.out.println();
        }

        return allBooks;
    }
}
