package com.lzjz.expressway.model;

import com.lzjz.expressway.bean.SyncLinkageMenuBean;

import java.util.List;

/**
 * Create dell By 2018/7/2 13:33
 */

public class SyncLinkageMenuSecondModel extends SyncLinkageMenuBean {
    private List<SyncLinkageMenuThirdModel> secondLevelList;

    public List<SyncLinkageMenuThirdModel> getSecondLevelList() {
        return secondLevelList;
    }

    public void setSecondLevelList(List<SyncLinkageMenuThirdModel> secondLevelList) {
        this.secondLevelList = secondLevelList;
    }
}
