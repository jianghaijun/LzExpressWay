package com.lzjz.expressway.model;

import com.lzjz.expressway.base.BaseModel;
import com.lzjz.expressway.bean.MainPageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Create dell By 2018/7/11 11:21
 */

public class MainPageModel extends BaseModel {
    private MainPageModel data;
    private String todayFinishNum;
    private String todoCount;
    private String hasTodoCount;
    private String version;
    private String unReadNum;
    private String unSubmitted;
    private long fileLength;
    private List<MainPageModel> zxHwHomeLargeModuleList; // 大模块
    private List<MainPageBean> zxHwHomeSmallModuleList; // 小模块
    private MainPageBean zxHwHomeHintInformation; // 小模块
    private List<MainPageBean> newsList;
    private List<MainPageBean> viewList;

    public MainPageBean getZxHwHomeHintInformation() {
        return zxHwHomeHintInformation;
    }

    public void setZxHwHomeHintInformation(MainPageBean zxHwHomeHintInformation) {
        this.zxHwHomeHintInformation = zxHwHomeHintInformation;
    }

    public List<MainPageModel> getZxHwHomeLargeModuleList() {
        return zxHwHomeLargeModuleList;
    }

    public void setZxHwHomeLargeModuleList(List<MainPageModel> zxHwHomeLargeModuleList) {
        this.zxHwHomeLargeModuleList = zxHwHomeLargeModuleList;
    }

    public List<MainPageBean> getZxHwHomeSmallModuleList() {
        return zxHwHomeSmallModuleList;
    }

    public void setZxHwHomeSmallModuleList(List<MainPageBean> zxHwHomeSmallModuleList) {
        this.zxHwHomeSmallModuleList = zxHwHomeSmallModuleList;
    }

    public String getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(String unReadNum) {
        this.unReadNum = unReadNum;
    }

    public String getUnSubmitted() {
        return unSubmitted;
    }

    public void setUnSubmitted(String unSubmitted) {
        this.unSubmitted = unSubmitted;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public MainPageModel getData() {
        return data == null ? new MainPageModel() : data;
    }

    public void setData(MainPageModel data) {
        this.data = data;
    }

    public String getTodayFinishNum() {
        return todayFinishNum;
    }

    public void setTodayFinishNum(String todayFinishNum) {
        this.todayFinishNum = todayFinishNum;
    }

    public String getTodoCount() {
        return todoCount;
    }

    public void setTodoCount(String todoCount) {
        this.todoCount = todoCount;
    }

    public String getHasTodoCount() {
        return hasTodoCount;
    }

    public void setHasTodoCount(String hasTodoCount) {
        this.hasTodoCount = hasTodoCount;
    }

    public List<MainPageBean> getNewsList() {
        return newsList == null ? new ArrayList<MainPageBean>() : newsList;
    }

    public void setNewsList(List<MainPageBean> newsList) {
        this.newsList = newsList;
    }

    public List<MainPageBean> getViewList() {
        return viewList;
    }

    public void setViewList(List<MainPageBean> viewList) {
        this.viewList = viewList;
    }
}
