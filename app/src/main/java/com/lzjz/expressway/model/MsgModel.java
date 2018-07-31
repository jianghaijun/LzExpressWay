package com.lzjz.expressway.model;

import com.lzjz.expressway.base.BaseModel;
import com.lzjz.expressway.bean.MsgBean;

import java.util.List;

/**
 * Create dell By 2018/7/13 13:54
 */

public class MsgModel extends BaseModel {
    private int totalNumber;
    private List<MsgBean> data;

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public List<MsgBean> getData() {
        return data;
    }

    public void setData(List<MsgBean> data) {
        this.data = data;
    }
}
