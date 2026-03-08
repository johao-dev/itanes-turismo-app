package com.senati.itanesturismo.data.repository;

public interface RepositoryCallback<T> {

    void onSuccess(T data);
    void onError(Throwable error);
}
