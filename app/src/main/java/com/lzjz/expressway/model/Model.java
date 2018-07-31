package com.lzjz.expressway.model;

import com.lzjz.expressway.base.BaseModel;

/**
 * Create dell By 2018/6/14 21:27
 */

public class Model<T> extends BaseModel{
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
