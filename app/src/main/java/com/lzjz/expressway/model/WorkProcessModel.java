package com.lzjz.expressway.model;

import com.lzjz.expressway.base.BaseModel;
import com.lzjz.expressway.bean.WorkingBean;

/**
 * Create dell By 2018/6/13 16:41
 */

public class WorkProcessModel extends BaseModel {
    private WorkingBean data;

    public WorkingBean getData() {
        return data == null ? new WorkingBean() : data;
    }

    public void setData(WorkingBean data) {
        this.data = data;
    }
}
