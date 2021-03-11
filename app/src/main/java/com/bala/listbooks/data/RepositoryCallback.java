package com.bala.listbooks.data;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
