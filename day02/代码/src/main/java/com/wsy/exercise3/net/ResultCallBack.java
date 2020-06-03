package com.wsy.exercise3.net;

public interface ResultCallBack<T> {
    void onSuccess(T t);
    void onFail(String msg);
}
