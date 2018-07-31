package com.lzjz.expressway.model;

import com.lzjz.expressway.base.BaseModel;
import com.lzjz.expressway.bean.ProcessDicBaseBean;

import java.util.List;

/**
 * Create dell By 2018/7/2 11:03
 */

public class ProcessDictionaryModel extends BaseModel {
    private List<ProcessDicBaseBean> data;

    public List<ProcessDicBaseBean> getData() {
        return data;
    }

    public void setData(List<ProcessDicBaseBean> data) {
        this.data = data;
    }
}
