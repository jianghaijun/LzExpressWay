package com.lzjz.expressway.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Create dell By 2018/7/11 11:23
 */

public class MainPageBean extends DataSupport implements Serializable {
    private String viewId;
    private String viewSummary;
    private String fileUrl;
    private String smallModuleTitle;
    private String smallModuleLink;
    private String smallModuleIcon;
    private String smallModuleEventIcon;
    private String smallModuleCount;
    private String icon;
    private String msg;
    private String type;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSmallModuleCount() {
        return smallModuleCount;
    }

    public void setSmallModuleCount(String smallModuleCount) {
        this.smallModuleCount = smallModuleCount;
    }

    public String getSmallModuleTitle() {
        return smallModuleTitle;
    }

    public void setSmallModuleTitle(String smallModuleTitle) {
        this.smallModuleTitle = smallModuleTitle;
    }

    public String getSmallModuleLink() {
        return smallModuleLink;
    }

    public void setSmallModuleLink(String smallModuleLink) {
        this.smallModuleLink = smallModuleLink;
    }

    public String getSmallModuleIcon() {
        return smallModuleIcon;
    }

    public void setSmallModuleIcon(String smallModuleIcon) {
        this.smallModuleIcon = smallModuleIcon;
    }

    public String getSmallModuleEventIcon() {
        return smallModuleEventIcon;
    }

    public void setSmallModuleEventIcon(String smallModuleEventIcon) {
        this.smallModuleEventIcon = smallModuleEventIcon;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getViewSummary() {
        return viewSummary == null ? "" : viewSummary;
    }

    public void setViewSummary(String viewSummary) {
        this.viewSummary = viewSummary;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
