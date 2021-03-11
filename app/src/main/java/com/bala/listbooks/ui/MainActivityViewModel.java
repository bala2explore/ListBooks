package com.bala.listbooks.ui;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bala.listbooks.ui.model.Book;
import com.bala.listbooks.data.Result;
import com.bala.listbooks.domain.GetBooksUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Book>> bookData = new MutableLiveData<>();
    private MutableLiveData<Result> errorData = new MutableLiveData<>();
    private GetBooksUseCase getBooksUseCase;

    public MutableLiveData<List<Book>> getBookData() {
        return bookData;
    }

    public MutableLiveData<Result> getErrorData() {
        return errorData;
    }

    public MainActivityViewModel() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        getBooksUseCase = new GetBooksUseCase(executorService,mainThreadHandler);

    }

    public void getBooks(String url) {
        getBooksUseCase.makeGetRequest(url, result -> {
            if (result instanceof Result.Success) {

               List<Book> bookList = ((Result.Success<List<Book>>) result).data;
               bookData.setValue(bookList);
//                bookAdapter.addItems(bookList);
            } else {
                errorData.setValue(result);

            }
        });
    }


}