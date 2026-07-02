package com.topjohnwu.magicmask.net;

public interface ResponseListener<T> {
    void onResponse(T response);
}
