package com.lzjz.expressway.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by HaiJun on 2018/6/11 17:53
 * 工序bean
 */
public class WorkingBean extends DataSupport implements Serializable {
    private String processId;        // 工序ID
    private String processName;      // 工序名称createTime
    private String processCode;      // 编码
    private String photoContent;     // 拍照内容
    private String photoDistance;    // 距离及角度
    private String photoNumber;      // 拍照最少张数
    private String longitude;        // 经度
    private String latitude;         // 纬度
    private String location;         // 地理位置
    private String levelNameAll;     // 工序部位
    private String levelId;          // 工序部位
    private long enterTime;          // 录入时间
    private String actualNumber;     // 实际照片数量
    private String photoerAll;       // 拍照者all
    private String checkNameAll;     // 确认者姓名all
    private String createUser;       // 拍照者
    private String processState;     // 工序状态
    private String flowStatus;     // 工序状态
    private String dismissal;        // 驳回原因
    private long sendTime;
    private String content;
    private String taskId;
    private String personalNum;
    private String systemNum;
    private String createUserName;
    private String isRead;
    private String ext1;        // 层厚
    private String ext2;        // 层厚
    private String ext3;        // 层厚
    private String ext4;        // 层厚
    private String ext5;        // 层厚
    private String ext6;        // 层厚
    private String ext7;        // 层厚
    private String ext8;        // 层厚
    private String ext9;        // 层厚
    private String ext10;       // 层厚
    private String canCheck;
    private String title;
    private String sendUserName;
    private String nodeName;
    private String trackStatus;
    private String flowId;
    private String workId;
    private String flowName;
    private String mainTablePrimaryId;
    private String type;
    private String userId;
    private String submitter;
    private String lastSubmitter;
    private String delFlag;
    private String nextSubmitter;
    private String modifyUserName;
    private String modifyUser;
    private String secondLevelId;
    private long modifyTime;
    private String firstLevelId;
    private long createTime;
    private String levelIdAll;
    private String remarks;
    private String fileOperationFlag;
    private String opinionShowFlag;
    private String opinionContent;
    private String flowType;
    private String troubleTitle; // 标题
    private String troubleId; // 标题
    private String troubleType; // 标题
    private String dangerTitle;
    private String troubleLevel; // 隐患级别
    private String dangerLevel;
    private String dangerType;
    private long deadline; // 整改期限
    private String troubleRequire; // 整改内容
    private String dangerContent; // 整改内容
    private String dangerId; // 整改内容
    private String troubleContent; // 整改内容
    private String dangerRequire;
    private int isLocalAdd;     // 是否是本地添加 1：是 2：否
    private String levelLevel;     // 是否是本地添加 1：是 2：否
    private String opinionField;     // 是否显示意见栏
    private String opinionFieldName;     // 是否显示意见栏
    private String opinionField1;     // 是否显示意见栏
    private String opinionField2;     // 是否显示意见栏
    private String opinionField3;     // 是否显示意见栏
    private String opinionField4;     // 是否显示意见栏
    private String opinionField5;     // 是否显示意见栏
    private String opinionField6;     // 是否显示意见栏
    private String opinionField7;     // 是否显示意见栏
    private String opinionField8;     // 是否显示意见栏
    private String opinionField9;     // 是否显示意见栏
    private String opinionField10;     // 是否显示意见栏

    public String getTroubleId() {
        return troubleId;
    }

    public void setTroubleId(String troubleId) {
        this.troubleId = troubleId;
    }

    public String getDangerId() {
        return dangerId;
    }

    public void setDangerId(String dangerId) {
        this.dangerId = dangerId;
    }

    public String getOpinionFieldName() {
        return opinionFieldName;
    }

    public void setOpinionFieldName(String opinionFieldName) {
        this.opinionFieldName = opinionFieldName;
    }

    public String getOpinionField1() {
        return opinionField1;
    }

    public void setOpinionField1(String opinionField1) {
        this.opinionField1 = opinionField1;
    }

    public String getOpinionField2() {
        return opinionField2;
    }

    public void setOpinionField2(String opinionField2) {
        this.opinionField2 = opinionField2;
    }

    public String getOpinionField3() {
        return opinionField3;
    }

    public void setOpinionField3(String opinionField3) {
        this.opinionField3 = opinionField3;
    }

    public String getOpinionField4() {
        return opinionField4;
    }

    public void setOpinionField4(String opinionField4) {
        this.opinionField4 = opinionField4;
    }

    public String getOpinionField5() {
        return opinionField5;
    }

    public void setOpinionField5(String opinionField5) {
        this.opinionField5 = opinionField5;
    }

    public String getOpinionField6() {
        return opinionField6;
    }

    public void setOpinionField6(String opinionField6) {
        this.opinionField6 = opinionField6;
    }

    public String getOpinionField7() {
        return opinionField7;
    }

    public void setOpinionField7(String opinionField7) {
        this.opinionField7 = opinionField7;
    }

    public String getOpinionField8() {
        return opinionField8;
    }

    public void setOpinionField8(String opinionField8) {
        this.opinionField8 = opinionField8;
    }

    public String getOpinionField9() {
        return opinionField9;
    }

    public void setOpinionField9(String opinionField9) {
        this.opinionField9 = opinionField9;
    }

    public String getOpinionField10() {
        return opinionField10;
    }

    public void setOpinionField10(String opinionField10) {
        this.opinionField10 = opinionField10;
    }

    public String getOpinionField() {
        return opinionField;
    }

    public void setOpinionField(String opinionField) {
        this.opinionField = opinionField;
    }

    public String getDangerContent() {
        return dangerContent;
    }

    public void setDangerContent(String dangerContent) {
        this.dangerContent = dangerContent;
    }

    public String getTroubleContent() {
        return troubleContent;
    }

    public void setTroubleContent(String troubleContent) {
        this.troubleContent = troubleContent;
    }

    public String getTroubleType() {
        return troubleType;
    }

    public void setTroubleType(String troubleType) {
        this.troubleType = troubleType;
    }

    public String getDangerType() {
        return dangerType;
    }

    public void setDangerType(String dangerType) {
        this.dangerType = dangerType;
    }

    public String getLevelLevel() {
        return levelLevel;
    }

    public void setLevelLevel(String levelLevel) {
        this.levelLevel = levelLevel;
    }

    public int getIsLocalAdd() {
        return isLocalAdd;
    }

    public void setIsLocalAdd(int isLocalAdd) {
        this.isLocalAdd = isLocalAdd;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getTroubleTitle() {
        return troubleTitle;
    }

    public void setTroubleTitle(String troubleTitle) {
        this.troubleTitle = troubleTitle;
    }

    public String getDangerTitle() {
        return dangerTitle;
    }

    public void setDangerTitle(String dangerTitle) {
        this.dangerTitle = dangerTitle;
    }

    public String getTroubleLevel() {
        return troubleLevel;
    }

    public void setTroubleLevel(String troubleLevel) {
        this.troubleLevel = troubleLevel;
    }

    public String getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getTroubleRequire() {
        return troubleRequire;
    }

    public void setTroubleRequire(String troubleRequire) {
        this.troubleRequire = troubleRequire;
    }

    public String getDangerRequire() {
        return dangerRequire;
    }

    public void setDangerRequire(String dangerRequire) {
        this.dangerRequire = dangerRequire;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getFileOperationFlag() {
        return fileOperationFlag;
    }

    public void setFileOperationFlag(String fileOperationFlag) {
        this.fileOperationFlag = fileOperationFlag;
    }

    public String getOpinionShowFlag() {
        return opinionShowFlag;
    }

    public void setOpinionShowFlag(String opinionShowFlag) {
        this.opinionShowFlag = opinionShowFlag;
    }

    public String getOpinionContent() {
        return opinionContent;
    }

    public void setOpinionContent(String opinionContent) {
        this.opinionContent = opinionContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMainTablePrimaryId() {
        return mainTablePrimaryId;
    }

    public void setMainTablePrimaryId(String mainTablePrimaryId) {
        this.mainTablePrimaryId = mainTablePrimaryId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCanCheck() {
        return canCheck;
    }

    public void setCanCheck(String canCheck) {
        this.canCheck = canCheck;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getPersonalNum() {
        return personalNum;
    }

    public void setPersonalNum(String personalNum) {
        this.personalNum = personalNum;
    }

    public String getSystemNum() {
        return systemNum;
    }

    public void setSystemNum(String systemNum) {
        this.systemNum = systemNum;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExt1() {
        return ext1 == null ? "" : ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2 == null ? "" : ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3 == null ? "" : ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4 == null ? "" : ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    public String getExt5() {
        return ext5 == null ? "" : ext5;
    }

    public void setExt5(String ext5) {
        this.ext5 = ext5;
    }

    public String getExt6() {
        return ext6 == null ? "" : ext6;
    }

    public void setExt6(String ext6) {
        this.ext6 = ext6;
    }

    public String getExt7() {
        return ext7 == null ? "" : ext7;
    }

    public void setExt7(String ext7) {
        this.ext7 = ext7;
    }

    public String getExt8() {
        return ext8 == null ? "" : ext8;
    }

    public void setExt8(String ext8) {
        this.ext8 = ext8;
    }

    public String getExt9() {
        return ext9 == null ? "" : ext9;
    }

    public void setExt9(String ext9) {
        this.ext9 = ext9;
    }

    public String getExt10() {
        return ext10 == null ? "" : ext10;
    }

    public void setExt10(String ext10) {
        this.ext10 = ext10;
    }

    public String getDismissal() {
        return dismissal == null ? "" : dismissal;
    }

    public void setDismissal(String dismissal) {
        this.dismissal = dismissal;
    }

    public String getPhotoerAll() {
        return photoerAll == null ? "" : photoerAll;
    }

    public void setPhotoerAll(String photoerAll) {
        this.photoerAll = photoerAll;
    }

    public String getLevelId() {
        return levelId == null ? "" : levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getProcessId() {
        return processId == null ? "" : processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName == null ? "" : processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessCode() {
        return processCode == null ? "" : processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getPhotoContent() {
        return photoContent == null ? "" : photoContent;
    }

    public void setPhotoContent(String photoContent) {
        this.photoContent = photoContent;
    }

    public String getPhotoDistance() {
        return photoDistance == null ? "" : photoDistance;
    }

    public void setPhotoDistance(String photoDistance) {
        this.photoDistance = photoDistance;
    }

    public String getPhotoNumber() {
        return photoNumber == null ? "" : photoNumber;
    }

    public void setPhotoNumber(String photoNumber) {
        this.photoNumber = photoNumber;
    }

    public String getLongitude() {
        return longitude == null ? "" : longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude == null ? "" : latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location == null ? "" : location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLevelNameAll() {
        return levelNameAll == null ? "" : levelNameAll;
    }

    public void setLevelNameAll(String levelNameAll) {
        this.levelNameAll = levelNameAll;
    }

    public long getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(long enterTime) {
        this.enterTime = enterTime;
    }

    public String getActualNumber() {
        return actualNumber == null ? "" : actualNumber;
    }

    public void setActualNumber(String actualNumber) {
        this.actualNumber = actualNumber;
    }

    public String getCheckNameAll() {
        return checkNameAll == null ? "" : checkNameAll;
    }

    public void setCheckNameAll(String checkNameAll) {
        this.checkNameAll = checkNameAll;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getProcessState() {
        return processState == null ? "" : processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getLastSubmitter() {
        return lastSubmitter;
    }

    public void setLastSubmitter(String lastSubmitter) {
        this.lastSubmitter = lastSubmitter;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getNextSubmitter() {
        return nextSubmitter;
    }

    public void setNextSubmitter(String nextSubmitter) {
        this.nextSubmitter = nextSubmitter;
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getSecondLevelId() {
        return secondLevelId;
    }

    public void setSecondLevelId(String secondLevelId) {
        this.secondLevelId = secondLevelId;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getFirstLevelId() {
        return firstLevelId;
    }

    public void setFirstLevelId(String firstLevelId) {
        this.firstLevelId = firstLevelId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getLevelIdAll() {
        return levelIdAll;
    }

    public void setLevelIdAll(String levelIdAll) {
        this.levelIdAll = levelIdAll;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
