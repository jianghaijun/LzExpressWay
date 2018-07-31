package com.lzjz.expressway.bean;

public class NodeVarsBean {
    private String opinionField;
    private String opinionFieldName;
    private String fileOperationFlag;

    public String getFileOperationFlag() {
        return fileOperationFlag;
    }

    public void setFileOperationFlag(String fileOperationFlag) {
        this.fileOperationFlag = fileOperationFlag;
    }

    public String getOpinionField() {
        return opinionField;
    }

    public String getOpinionFieldName() {
        return opinionFieldName;
    }

    public void setOpinionFieldName(String opinionFieldName) {
        this.opinionFieldName = opinionFieldName;
    }

    public void setOpinionField(String opinionField) {
        this.opinionField = opinionField;
    }
}
