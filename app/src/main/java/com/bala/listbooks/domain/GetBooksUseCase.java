package com.bala.listbooks.domain;

import android.os.Handler;

import com.bala.listbooks.ui.model.Book;
import com.bala.listbooks.data.RepositoryCallback;
import com.bala.listbooks.data.Result;
import com.bala.listbooks.data.BookRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class GetBooksUseCase {

    private final Handler resultHandler;
    Executor executor;
    private final BookRepository bookRepository;

    public GetBooksUseCase(Executor executor,
                          Handler resultHandler) {
        this.executor = executor;
        this.resultHandler = resultHandler;
        bookRepository = new BookRepository();
    }

    public void makeGetRequest(
            String url,
            final RepositoryCallback<List<Book>> callback
    ) {
        executor.execute(() -> {
            try {
                Result<List<Book>> result = bookRepository.makeSynchronousRequest(url);
                notifyResult(result, callback);
            } catch (Exception e) {
                Result<List<Book>> errorResult = new Result.Error<>("");
                notifyResult(errorResult, callback);
            }
        });
    }

    private void notifyResult(
            final Result<List<Book>> result,
            final RepositoryCallback<List<Book>> callback
    ) {
        resultHandler.post(() -> callback.onComplete(result));
    }
}
