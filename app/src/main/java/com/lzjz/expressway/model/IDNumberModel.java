package com.lzjz.expressway.model;

import com.lzjz.expressway.base.BaseModel;
import com.lzjz.expressway.bean.IDNumberBean;

/**
 * Created by wdplus02 on 2018/7/18.
 */

public class IDNumberModel extends BaseModel {
    private IDNumberBean data;

    public IDNumberBean getData() {
        return data;
    }

    public void setData(IDNumberBean data) {
        this.data = data;
    }
}
