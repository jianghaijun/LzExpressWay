package com.lzjz.expressway.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.speech.asr.SpeechConstant;
import com.google.gson.Gson;
import com.lzjz.expressway.R;
import com.lzjz.expressway.adapter.PhotosListAdapter;
import com.lzjz.expressway.adapter.TimeLineAdapter;
import com.lzjz.expressway.application.MyApplication;
import com.lzjz.expressway.base.BaseActivity;
import com.lzjz.expressway.bean.HistoryBean;
import com.lzjz.expressway.bean.PhotosBean;
import com.lzjz.expressway.bean.WorkingBean;
import com.lzjz.expressway.bean.Working_Bean;
import com.lzjz.expressway.control.MyRecognizer;
import com.lzjz.expressway.dialog.PromptDialog;
import com.lzjz.expressway.dialog.UpLoadPhotosDialog;
import com.lzjz.expressway.listener.GPSLocationListener;
import com.lzjz.expressway.listener.PermissionListener;
import com.lzjz.expressway.listener.PromptListener;
import com.lzjz.expressway.listener.ShowPhotoListener;
import com.lzjz.expressway.manager.GPSLocationManager;
import com.lzjz.expressway.model.ButtonListModel;
import com.lzjz.expressway.model.WorkModel;
import com.lzjz.expressway.model.WorkProcessModel;
import com.lzjz.expressway.popwindow.H5PopupWindow;
import com.lzjz.expressway.recognization.ChainRecogListener;
import com.lzjz.expressway.service.LocationService;
import com.lzjz.expressway.ui.BaiduASRDigitalDialog;
import com.lzjz.expressway.ui.DigitalDialogInput;
import com.lzjz.expressway.utils.AppInfoUtil;
import com.lzjz.expressway.utils.ChildThreadUtil;
import com.lzjz.expressway.utils.ConstantsUtil;
import com.lzjz.expressway.utils.DateUtils;
import com.lzjz.expressway.utils.FileUtil;
import com.lzjz.expressway.utils.ImageUtil;
import com.lzjz.expressway.utils.JsonUtils;
import com.lzjz.expressway.utils.JudgeNetworkIsAvailable;
import com.lzjz.expressway.utils.LoadingUtils;
import com.lzjz.expressway.utils.ProviderUtil;
import com.lzjz.expressway.utils.ScreenManagerUtil;
import com.lzjz.expressway.utils.SpUtil;
import com.lzjz.expressway.utils.ToastUtil;

import org.litepal.crud.DataSupport;
import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 详情
 */
public class ContractorDetailsActivity extends BaseActivity {
    @ViewInject(R.id.imgBtnLeft)
    private ImageButton imgBtnLeft;
    @ViewInject(R.id.txtTitle)
    private TextView txtTitle;
    /*数据信息*/
    @ViewInject(R.id.txtWorkingNo)
    private TextView txtWorkingNo;
    @ViewInject(R.id.txtWorkingName)
    private TextView txtWorkingName;
    @ViewInject(R.id.txtEntryTime)
    private TextView txtEntryTime;
    @ViewInject(R.id.txtLocation)
    private TextView txtLocation;
    @ViewInject(R.id.txtDistanceAngle)
    private TextView txtDistanceAngle;
    @ViewInject(R.id.txtTakePhotoNum)
    private TextView txtTakePhotoNum;
    @ViewInject(R.id.txtTakePhotoRequirement)
    private TextView txtTakePhotoRequirement;
    @ViewInject(R.id.txtRejectPhoto)
    private TextView txtRejectPhoto;
    @ViewInject(R.id.txtBidsPath)
    private TextView txtBidsPath;
    @ViewInject(R.id.llButtons)
    private LinearLayout llButtons;
    @ViewInject(R.id.imgBtnAdd)
    private ImageButton imgBtnAdd;
    @ViewInject(R.id.rvContractorDetails)
    private RecyclerView rvContractorDetails;
    @ViewInject(R.id.rlRemarks)
    private RelativeLayout rlRemarks;
    @ViewInject(R.id.edtRemarks)
    private EditText edtRemarks;
    @ViewInject(R.id.llOptions)
    private LinearLayout llOptions;
    @ViewInject(R.id.view)
    private View view;
    // 时间轴
    @ViewInject(R.id.rvTimeMarker)
    private RecyclerView rvTimeMarker;
    private TimeLineAdapter timeLineAdapter;
    // 图片列表
    private PhotosListAdapter photosAdapter;
    private List<PhotosBean> photosList = new ArrayList<>();
    // 定位信息
    private LocationService locationService;
    private final int SDK_PERMISSION_REQUEST = 127;
    private GPSLocationManager gpsLocationManager;
    // 拍照
    private String fileUrlName, strFilePath;
    private PhotosBean addPhotoBean;
    private File imgFile;
    private Activity mContext;
    private String workId, flowId, processId, mainTablePrimaryId, sLocation, processPath, jsonData, buttonId, mainTableId;
    private double longitude, latitude;
    private Gson gson = new Gson();
    private WorkModel model;
    WorkProcessModel workProcessModel = null;
    private boolean isToDo;
    private int leastTakePhotoNum, isLocalAdd; // 最少拍照数量
    private String selectLevelId = ""; // 选中层级id
    private String levelIdAll = ""; // 选中层级id
    private String levelNameAll = ""; // 选中层级id
    private H5PopupWindow p;
    private MyRecognizer myRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contractor_details);

        mContext = this;
        x.view().inject(this);
        ScreenManagerUtil.pushActivity(this);

        imgBtnLeft.setVisibility(View.VISIBLE);
        imgBtnLeft.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.back_btn));
        txtTitle.setText(R.string.app_name);

        // 任务id
        workId = getIntent().getStringExtra("workId");
        flowId = getIntent().getStringExtra("flowId");
        processId = getIntent().getStringExtra("processId");
        mainTablePrimaryId = getIntent().getStringExtra("mainTablePrimaryId");
        isToDo = getIntent().getBooleanExtra("isToDo", false);
        isLocalAdd = getIntent().getIntExtra("isLocalAdd", 2);

        initFilePath();
        getPermissions();

        if (JudgeNetworkIsAvailable.isNetworkAvailable(this) && isLocalAdd != 1) {
            getData(isToDo);
        } else {
            List<WorkingBean> workingBeanList = DataSupport.where("processId = ? order by createTime desc", isToDo ? workId : processId).find(WorkingBean.class);
            WorkingBean workingBean = ObjectUtil.isNull(workingBeanList) || workingBeanList.size() == 0 ? new WorkingBean() : workingBeanList.get(0);
            if (!isToDo) {
                workingBean.setFileOperationFlag("1");
            }
            setImgData(new ArrayList<PhotosBean>());
            List<ButtonListModel> buttons = new ArrayList<>();
            if (StrUtil.isNotEmpty(workingBean.getFileOperationFlag()) && workingBean.getFileOperationFlag().equals("1")) {
                ButtonListModel btnModel = new ButtonListModel();
                btnModel.setButtonId("save");
                btnModel.setButtonName("本地保存");
                buttons.add(btnModel);
            }
            setShowButton(buttons);
            setTableData(workingBean);
            List<HistoryBean> flowHistoryList = DataSupport.where("processId=?", isToDo ? workId : processId).find(HistoryBean.class);
            initTimeLineView(ObjectUtil.isNull(flowHistoryList) ? new ArrayList<HistoryBean>() : flowHistoryList);
        }

        // 是否直接弹出相机
        boolean isPopTakePhoto = getIntent().getBooleanExtra("isPopTakePhoto", false);
        if (isPopTakePhoto) {
            checkPhotosPermission();
        }
    }

    @Override
    protected void onStop() {
        if (locationService != null) {
            locationService.unregisterListener(mListener); //注销掉监听
            locationService.stop(); //停止定位服务
        }
        super.onStop();
    }

    /**
     * 设置列表方向
     *
     * @return
     */
    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    /**
     * 初始化时间轴
     *
     * @param flowHistoryList
     */
    private void initTimeLineView(List<HistoryBean> flowHistoryList) {
        if (flowHistoryList == null) {
            return;
        }

        for (HistoryBean history : flowHistoryList) {
            history.setProcessId(isToDo ? workId : processId);
            history.saveOrUpdate("actionTime=? and processId=?", history.getActionTime() + "", isToDo ? workId : processId);
        }

        rvTimeMarker.setLayoutManager(getLinearLayoutManager());
        rvTimeMarker.setHasFixedSize(true);
        timeLineAdapter = new TimeLineAdapter(flowHistoryList);
        rvTimeMarker.setAdapter(timeLineAdapter);
        rvTimeMarker.setNestedScrollingEnabled(false);
    }

    /**
     * 初始化照片存储位置
     */
    private void initFilePath() {
        strFilePath = mContext.getExternalCacheDir().getAbsolutePath() + "/";
        imgFile = new File(strFilePath);
        if (!imgFile.exists()) {
            imgFile.mkdirs();
        }
    }

    /**
     * 获取表单数据
     *
     * @param isToDo 待办已办？
     */
    private void getData(final boolean isToDo) {
        LoadingUtils.showLoading(mContext);
        JSONObject obj = new JSONObject();
        obj.put("processId", processId);
        String url = "";
        if (isToDo) {
            obj.put("flowId", flowId);
            obj.put("workId", workId);
            obj.put("apiName", "getZxHwGxProcessDetailsByFlowWorkId");
            obj.put("apiType", "POST");
            url += ConstantsUtil.openFlow;
        } else {
            obj.put("apih5FlowStatus", "0");
            url += ConstantsUtil.getZxHwGxProcessDetails;
        }
        Request request = ChildThreadUtil.getRequest(mContext, url, obj.toString());
        ConstantsUtil.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ChildThreadUtil.toastMsgHidden(mContext, getString(R.string.server_exception));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                jsonData = response.body().string().toString();
                if (JsonUtils.isGoodJson(jsonData)) {
                    // 解析
                    if (isToDo) {
                        model = gson.fromJson(jsonData, WorkModel.class);
                    } else {
                        workProcessModel = gson.fromJson(jsonData, WorkProcessModel.class);
                    }
                    if (isToDo ? model.isSuccess() : workProcessModel.isSuccess()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isToDo) {
                                    if (model.getData().getApiData() != null) {
                                        Gson gson = new Gson();
                                        WorkingBean flowBean = gson.fromJson(model.getData().getApiData(), WorkingBean.class);
                                        Working_Bean flow_Bean = gson.fromJson(model.getData().getApiData(), Working_Bean.class);
                                        processId = flowBean.getProcessId();
                                        flowBean.setFileOperationFlag(model.getData().getNodeVars().getFileOperationFlag());
                                        flowBean.setOpinionField(model.getData().getNodeVars().getOpinionField());
                                        flowBean.setOpinionFieldName(model.getData().getFlowVars().getOpinionFieldName());
                                        flowBean.setRemarks(flowBean.getOpinionContent());
                                        setImgData(flow_Bean.getGxAttachmentList());
                                        List<ButtonListModel> buttons = new ArrayList<>();
                                        if (!isToDo) {
                                            ButtonListModel btnModel = new ButtonListModel();
                                            btnModel.setButtonId("localSubmit");
                                            btnModel.setButtonName("确认提交");
                                            buttons.add(btnModel);
                                        } else {
                                            buttons = model.getData().getFlowButtons();
                                        }
                                        setShowButton(buttons);
                                        setTableData(flowBean);
                                        initTimeLineView(model.getData().getFlowHistoryList());
                                        levelNameAll = flowBean.getLevelNameAll();
                                        selectLevelId = flowBean.getLevelId();
                                        levelIdAll = flowBean.getLevelIdAll();
                                    }
                                    LoadingUtils.hideLoading();
                                } else {
                                    WorkingBean flowBean = workProcessModel.getData();
                                    flowBean.setFileOperationFlag("1");
                                    flowBean.setOpinionShowFlag("");
                                    mainTableId = processId;
                                    setImgData(null);
                                    List<ButtonListModel> buttons = new ArrayList<>();
                                    ButtonListModel btnModel = new ButtonListModel();
                                    btnModel.setButtonId("localSubmit");
                                    btnModel.setButtonName("确认提交");
                                    buttons.add(btnModel);

                                    levelNameAll = flowBean.getLevelNameAll();
                                    selectLevelId = flowBean.getLevelId();
                                    levelIdAll = flowBean.getLevelIdAll();
                                    setShowButton(buttons);
                                    setTableData(flowBean);
                                    initTimeLineView(null);
                                    LoadingUtils.hideLoading();

                                    /*WorkingBean flowBean = model.getData().getMainTableObject();
                                    flowBean.setFileOperationFlag(model.getData().getFileOperationFlag());
                                    flowBean.setOpinionShowFlag(model.getData().getOpinionShowFlag());
                                    mainTableId = model.getData().getMainTablePrimaryId();
                                    setImgData(model.getData().getSubTableObject().getZxHwGxAttachment().getSubTableObject());
                                    List<ButtonListModel> buttons = new ArrayList<>();
                                    if (!isToDo) {
                                        ButtonListModel btnModel = new ButtonListModel();
                                        btnModel.setButtonId("localSubmit");
                                        btnModel.setButtonName("确认提交");
                                        buttons.add(btnModel);
                                    } else {
                                        buttons = model.getData().getButtonList();
                                    }

                                    levelNameAll = flowBean.getLevelNameAll();
                                    selectLevelId = flowBean.getLevelId();
                                    levelIdAll = flowBean.getLevelIdAll();
                                    setShowButton(buttons);
                                    setTableData(flowBean);
                                    initTimeLineView(model.getData().getFlowHistoryList());
                                    LoadingUtils.hideLoading();*/
                                }
                            }
                        });
                    } else {
                        ChildThreadUtil.checkTokenHidden(mContext,isToDo ? model.getMessage() : workProcessModel.getMessage(), isToDo ? model.getCode() : workProcessModel.getCode());
                    }
                } else {
                    ChildThreadUtil.toastMsgHidden(mContext, getString(R.string.json_error));
                }
            }
        });
    }

    /**
     * 设置工序信息
     *
     * @param flowBean
     */
    private void setTableData(WorkingBean flowBean) {
        // 保存
        flowBean.setProcessId(isToDo ? workId : processId);
        flowBean.saveOrUpdate("processId=?", isToDo ? workId : processId);
        txtBidsPath.setText(flowBean.getLevelNameAll().replaceAll(",", "→"));   // 工序名称
        txtWorkingName.setText(flowBean.getProcessName());   // 工序名称
        txtWorkingNo.setText(flowBean.getProcessCode());     // 工序编号
        txtTakePhotoRequirement.setText(flowBean.getPhotoContent());     // 拍照要求
        txtDistanceAngle.setText(flowBean.getPhotoDistance());   // 距离角度
        if (StrUtil.equals("0", flowBean.getPhotoNumber())) {
            txtTakePhotoNum.setText("无要求");
        } else {
            txtTakePhotoNum.setText("最少拍照" + flowBean.getPhotoNumber() + "张");  // 拍照张三
        }

        if (llButtons.getVisibility() == View.GONE) {
            edtRemarks.setFocusable(false);
            edtRemarks.setClickable(false);
        }
        edtRemarks.setText(flowBean.getRemarks());

        leastTakePhotoNum = Integer.valueOf(StrUtil.isEmpty(flowBean.getPhotoNumber()) ? "3" : flowBean.getPhotoNumber());
        txtEntryTime.setText(DateUtils.setDataToStr(flowBean.getEnterTime()));    // 检查时间
        txtLocation.setText(flowBean.getLocation());    // 拍照位置
        txtRejectPhoto.setText(flowBean.getDismissal()); // 驳回原因
        processPath = flowBean.getLevelNameAll().replace(",", "→")/* + "→" + flowBean.getProcessName()*/;
        // 显示意见
        if (StrUtil.isNotEmpty(flowBean.getOpinionFieldName())) {
            llOptions.setVisibility(View.VISIBLE);
            Map<String, Object> jsonObjectOpinionField = new HashMap<>();
            jsonObjectOpinionField.put("opinionField1", flowBean.getOpinionField1());
            jsonObjectOpinionField.put("opinionField2", flowBean.getOpinionField2());
            jsonObjectOpinionField.put("opinionField3", flowBean.getOpinionField3());
            jsonObjectOpinionField.put("opinionField4", flowBean.getOpinionField4());
            jsonObjectOpinionField.put("opinionField5", flowBean.getOpinionField5());
            jsonObjectOpinionField.put("opinionField6", flowBean.getOpinionField6());
            jsonObjectOpinionField.put("opinionField7", flowBean.getOpinionField7());
            jsonObjectOpinionField.put("opinionField8", flowBean.getOpinionField8());
            jsonObjectOpinionField.put("opinionField9", flowBean.getOpinionField9());
            jsonObjectOpinionField.put("opinionField10", flowBean.getOpinionField10());
            String flowVars = flowBean.getOpinionFieldName();
            // 多个
            if(flowVars.indexOf("_")>=0) {
                // 循环添加
                String[] opinionFieldNames = flowVars.split("_");
                for (int i = 0; i < opinionFieldNames.length; i++) {
                    String opinionFieldNameKey = StrUtil.subBefore(opinionFieldNames[i], "|", true);
                    String opinionFieldNameTitle = StrUtil.subAfter(opinionFieldNames[i], "|", true);
                    if(StrUtil.isNotEmpty(String.valueOf(jsonObjectOpinionField.get(opinionFieldNameKey)))) {
                        setOptions(opinionFieldNameTitle, i, String.valueOf(jsonObjectOpinionField.get(opinionFieldNameKey)));
                    }
                }
                // 单个
            } else {
                String opinionFieldNameKey = StrUtil.subBefore(flowBean.getOpinionFieldName(), "|", true);
                String opinionFieldNameTitle = StrUtil.subAfter(flowBean.getOpinionFieldName(), "|", true);
                if(StrUtil.isNotEmpty(String.valueOf(jsonObjectOpinionField.get(opinionFieldNameKey)))) {
                    setOptions(opinionFieldNameTitle, 1, String.valueOf(jsonObjectOpinionField.get(opinionFieldNameKey)));
                }
            }
        }

        // 控制拍照按钮是否显示
        if (!StrUtil.equals("1", flowBean.getFileOperationFlag())) {
            imgBtnAdd.setVisibility(View.GONE);
        }
        // 控制意见栏是否显示
        if (StrUtil.isEmpty(flowBean.getOpinionField())) {
            rlRemarks.setVisibility(View.GONE);
        }
    }

    /**
     * 动态添加意见
     * @param sTitle
     * @param i
     * @param sContent
     */
    private void setOptions(String sTitle, int i, String sContent) {
        // 添加外层布局
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.setBackground(null);
        // 向RelativeLayout中添加子布局--->分割线
        View v = new View(this);
        v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray));
        RelativeLayout.LayoutParams vl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 1);
        vl.leftMargin = DensityUtil.dip2px(10);
        vl.rightMargin = DensityUtil.dip2px(10);
        relativeLayout.addView(v, vl);
        // 向RelativeLayout中添加子布局--->标题
        TextView txtTitle = new TextView(this);
        txtTitle.setId(1000000 + i);
        RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(DensityUtil.dip2px(80), ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.addRule(RelativeLayout.CENTER_VERTICAL);
        txtTitle.setBackground(null);
        txtTitle.setGravity(Gravity.RIGHT);
        txtTitle.setText(sTitle);
        txtTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        txtTitle.setTextSize(14);
        relativeLayout.addView(txtTitle, ll);
        // 向RelativeLayout中添加子布局--->内容
        TextView txtContent = new TextView(this);
        RelativeLayout.LayoutParams llContent = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        llContent.addRule(RelativeLayout.LEFT_OF, 1000000 + i);
        txtContent.setBackground(null);
        txtContent.setGravity(Gravity.LEFT|Gravity.CENTER);
        txtContent.setMinHeight(DensityUtil.dip2px(40));
        txtContent.setPadding(0, DensityUtil.dip2px(5), DensityUtil.dip2px(10), DensityUtil.dip2px(5));
        txtContent.setText(sContent);
        txtContent.setTextColor(ContextCompat.getColor(mContext, R.color.dark_b));
        txtContent.setTextSize(14);
        relativeLayout.addView(txtContent, llContent);
        llOptions.addView(relativeLayout, rl);
    }

    /**
     * 设置照片信息
     *
     * @param subTableObject
     */
    private void setImgData(List<PhotosBean> subTableObject) {
        // 查询本地保存照片
        List<PhotosBean> localPhoneList = DataSupport.where("isToBeUpLoad = 1 AND userId = ? AND processId = ? order by createTime desc", (String) SpUtil.get(mContext, ConstantsUtil.USER_ID, ""), isToDo ? workId : processId).find(PhotosBean.class);

        // 添加本地照片
        if (localPhoneList != null && localPhoneList.size() > 0) {
            photosList.addAll(localPhoneList);
        }
        // 添加服务器照片
        if (subTableObject != null && subTableObject.size() > 0) {
            photosList.addAll(subTableObject);
        }

        // 图片列表
        photosAdapter = new PhotosListAdapter(mContext, photosList, clickPhotoListener, "", "1");
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvContractorDetails.setLayoutManager(ms);
        rvContractorDetails.setAdapter(photosAdapter);
    }

    /**
     * 设置显示按钮
     *
     * @param buttons
     */
    private void setShowButton(List<ButtonListModel> buttons) {
        llButtons.removeAllViews();
        if (buttons == null || buttons.size() == 0) {
            llButtons.setVisibility(View.GONE);
            return;
        }

        for (int i = buttons.size(); i > 0; i--) {
            ButtonListModel buttonModel = buttons.get(i-1);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(DensityUtil.dip2px(5), 0, DensityUtil.dip2px(5), 0);
            lp.weight = 1;
            Button button = new Button(this);
            button.setText(buttonModel.getButtonName());
            button.setTextSize(14);
            button.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_blue));
            button.setOnClickListener(new ButtonClick(buttonModel));
            llButtons.addView(button, lp);
        }


        /*for (ButtonListModel buttonModel : buttons) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(DensityUtil.dip2px(5), 0, DensityUtil.dip2px(5), 0);
            lp.weight = 1;
            Button button = new Button(this);
            button.setText(buttonModel.getButtonName());
            button.setTextSize(14);
            button.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_blue));
            button.setOnClickListener(new ButtonClick(buttonModel));
            llButtons.addView(button, lp);
        }*/
    }

    /**
     * 点击事件
     */
    private class ButtonClick implements View.OnClickListener {
        private ButtonListModel buttonModel;

        public ButtonClick(ButtonListModel buttonModel) {
            this.buttonModel = buttonModel;
        }

        @Override
        public void onClick(View v) {
            buttonId = buttonModel.getButtonId();
            /*if (buttonModel.getButtonId().contains("reject")) {
                boolean isEdit = buttonModel.getNextShowFlowInfoList() == null || buttonModel.getNextShowFlowInfoList().size() == 0 ? false : buttonModel.getNextShowFlowInfoList().get(0).isEdit();
                Intent intent = new Intent(mContext, PersonnelSelectionActivity.class);
                ConstantsUtil.buttonModel = buttonModel;
                intent.putExtra("isEdit", isEdit);
                startActivityForResult(intent, 201);
            } else*/ if (buttonModel.getButtonId().contains("save")) {
                ToastUtil.showShort(mContext, "保存成功！");
            } else if (buttonModel.getButtonId().contains("localSubmit")) {
                if (isLocalAdd == 1) {
                    ToastUtil.showShort(mContext, "请先将工序同步至服务器后再进行提交！");
                } else if (photosList.size() < leastTakePhotoNum) {
                    ToastUtil.showShort(mContext, "拍照数量不能小于最少拍照张数！");
                } else if (!JudgeNetworkIsAvailable.isNetworkAvailable(mContext)) {
                    ToastUtil.showShort(mContext, getString(R.string.not_network));
                } else if (!JudgeNetworkIsAvailable.GetNetworkType(mContext).equals("WIFI")) {
                    PromptDialog promptDialog = new PromptDialog(mContext, new PromptListener() {
                        @Override
                        public void returnTrueOrFalse(boolean trueOrFalse) {
                            if (trueOrFalse) {
                                Gson gson = new Gson();
                                SpUtil.put(mContext, "actionDataTwo", gson.toJson(buttonModel));
                                SpUtil.put(mContext, "actionData", gson.toJson(buttonModel));
                                toExaminePhoto(false);
                            }
                        }
                    }, "提示", "当前网络为移动网络,是否继续上传?", "否", "是");
                    promptDialog.setCancelable(false);
                    promptDialog.setCanceledOnTouchOutside(false);
                    promptDialog.show();
                } else {
                    Gson gson = new Gson();
                    SpUtil.put(mContext, "actionDataTwo", gson.toJson(buttonModel));
                    SpUtil.put(mContext, "actionData", gson.toJson(buttonModel));
                    toExaminePhoto(false);
                }
            } else {
                Gson gson = new Gson();
                SpUtil.put(mContext, "actionDataTwo", gson.toJson(buttonModel));
                SpUtil.put(mContext, "actionData", gson.toJson(buttonModel));
                toExaminePhoto(true);
            }/* if (buttonModel.getButtonId().contains("submit") || buttonModel.getButtonId().contains("rejectSubmit")) {
                if (imgBtnAdd.getVisibility() == View.VISIBLE) {
                    ConstantsUtil.buttonModel = buttonModel;
                    toExaminePhoto(true);
                } else {
                    boolean isEdit = buttonModel.getNextShowFlowInfoList() == null || buttonModel.getNextShowFlowInfoList().size() == 0 ? false : buttonModel.getNextShowFlowInfoList().get(0).isEdit();
                    Intent intent = new Intent(mContext, PersonnelSelectionActivity.class);
                    ConstantsUtil.buttonModel = buttonModel;
                    intent.putExtra("isEdit", isEdit);
                    startActivityForResult(intent, 201);
                }
            } else if (buttonModel.getButtonId().contains("getback")) {
                ToastUtil.showShort(mContext, "未知功能按钮");
            } else if (buttonModel.getButtonId().contains("localSubmit")) {
                if (isLocalAdd == 1) {
                    ToastUtil.showShort(mContext, "请先将工序同步至服务器后再进行提交！");
                } else if (photosList.size() < leastTakePhotoNum) {
                    ToastUtil.showShort(mContext, "拍照数量不能小于最少拍照张数！");
                } else if (!JudgeNetworkIsAvailable.isNetworkAvailable(mContext)) {
                    ToastUtil.showShort(mContext, getString(R.string.not_network));
                } else if (!JudgeNetworkIsAvailable.GetNetworkType(mContext).equals("WIFI")) {
                    PromptDialog promptDialog = new PromptDialog(mContext, new PromptListener() {
                        @Override
                        public void returnTrueOrFalse(boolean trueOrFalse) {
                            if (trueOrFalse) {
                                toExaminePhoto(false);
                            }
                        }
                    }, "提示", "当前网络为移动网络,是否继续上传?", "否", "是");
                    promptDialog.setCancelable(false);
                    promptDialog.setCanceledOnTouchOutside(false);
                    promptDialog.show();
                } else {
                    toExaminePhoto(false);
                }
            } else {
                ToastUtil.showShort(mContext, "未知按钮");
            }*/
        }
    }

    /**
     * 提交、驳回
     */
    private void submitData(final boolean isToDoType) {
        Map<String, Object> tableDataMap = new HashMap<>();
        tableDataMap.put("levelNameAll", levelNameAll);
        tableDataMap.put("levelId", selectLevelId);
        tableDataMap.put("levelIdAll", levelIdAll);
        tableDataMap.put("processId", processId);
        tableDataMap.put("opinionContent", edtRemarks.getText().toString());
        tableDataMap.put("processName", txtWorkingName.getText().toString());
        tableDataMap.put("processCode", txtWorkingNo.getText().toString());
        tableDataMap.put("photoContent", txtTakePhotoRequirement.getText().toString());
        tableDataMap.put("photoDistance", txtDistanceAngle.getText().toString());
        tableDataMap.put("photoNumber", StrUtil.equals(txtTakePhotoNum.getText().toString(), "无要求") ? 0 : leastTakePhotoNum);
        tableDataMap.put("enterTime", DateUtil.parse(txtEntryTime.getText().toString()).getTime());
        tableDataMap.put("location", txtLocation.getText().toString());
        JSONArray jsonArr = new JSONArray(SpUtil.get(mContext, "uploadImgData", "[]"));
        tableDataMap.put("gxAttachmentList", jsonArr);
        if (!isToDoType) {
            Map<String, Object> newobj = new HashMap<>();
            Map<String, Object> updataObj = new HashMap<>();
            newobj.put("apiName", "updateZxHwGxProcess");
            newobj.put("apiNameByCreate", "updateZxHwGxProcessByCreate");
            updataObj.put("apiName", "updateZxHwGxProcess");
            updataObj.put("apiNameByCreate", "updateZxHwGxProcess");
            newobj.put("flowId", "zxHwGxProcess");
            updataObj.put("flowId", "zxHwGxProcess");

            newobj.put("apiType", "POST");
            updataObj.put("apiType", "POST");
            newobj.put("title", levelNameAll);
            updataObj.put("title", levelNameAll);

            newobj.put("apiBody", tableDataMap);
            updataObj.put("apiBody", tableDataMap);

            SpUtil.put(mContext, "startFlowData", new Gson().toJson(newobj));
            SpUtil.put(mContext, "updateFlowData", new Gson().toJson(updataObj));

            // 调用h5
            p = new H5PopupWindow(mContext, false, processId, null, ConstantsUtil.star, promptListener);
            p.setTouchable(true);
            p.setFocusable(true); //设置点击menu以外其他地方以及返回键退出
            p.setOutsideTouchable(true);   //设置触摸外面时消失
            p.showAtDropDownRight(view);
        } else {
            JSONObject object = new JSONObject(jsonData);
            JSONObject newObject = new JSONObject(object.getObj("data").toString());
            newObject.put("apiName", "updateZxHwGxProcess");
            newObject.put("apiBody", new Gson().toJson(tableDataMap));
            SpUtil.put(mContext, "updateFlowData", new Gson().toJson(newObject));

            // 调用h5
            p = new H5PopupWindow(mContext, false, processId, null, ConstantsUtil.update/* + flowId + "/" + workId*/, promptListener);
            p.setTouchable(true);
            p.setFocusable(true); //设置点击menu以外其他地方以及返回键退出
            p.setOutsideTouchable(true);   //设置触摸外面时消失
            p.showAtDropDownRight(view);
        }





        /*LoadingUtils.showLoading(mContext);
        JSONObject object = new JSONObject(jsonData);
        String newJsonData, url;
        if (isToDoType) {
            url = ConstantsUtil.submitFlow;
            newJsonData = String.valueOf(object.getJSONObject("data").put("subTableObject", ""));
        } else {
            url = ConstantsUtil.startFlow;
            JSONObject obj = object.getJSONObject("data").put("title", processPath);
            obj.getJSONObject("mainTableDataObject").remove("location");
            newJsonData = String.valueOf(obj);
        }
        Request request = ChildThreadUtil.getRequest(mContext, url, newJsonData);
        ConstantsUtil.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ChildThreadUtil.toastMsgHidden(mContext, getString(R.string.server_exception));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (isToDoType) {
                    String jsonData = response.body().string().toString();
                    LoadingUtils.hideLoading();
                    if (JsonUtils.isGoodJson(jsonData)) {
                        BaseModel model = gson.fromJson(jsonData, BaseModel.class);
                        if (model.isSuccess()) {
                            ChildThreadUtil.toastMsgHidden(mContext, model.getMessage());
                            ConstantsUtil.isLoading = true;
                            ContractorDetailsActivity.this.finish();
                        } else {
                            ChildThreadUtil.checkTokenHidden(mContext, model.getMessage(), model.getCode());
                        }
                    } else {
                        ChildThreadUtil.toastMsgHidden(mContext, getString(R.string.json_error));
                    }
                } else {
                    jsonData = response.body().string().toString();
                    if (JsonUtils.isGoodJson(jsonData)) {
                        // 解析
                        model = gson.fromJson(jsonData, WorkModel.class);
                        if (model.isSuccess()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ConstantsUtil.isLoading = true;
                                    WorkingBean flowBean = model.getData().getMainTableObject();
                                    flowBean.setFileOperationFlag(model.getData().getFileOperationFlag());
                                    flowBean.setOpinionShowFlag(model.getData().getOpinionShowFlag());
                                    setTableData(flowBean);
                                    setImgData(model.getData().getSubTableObject().getZxHwGxAttachment().getSubTableObject());
                                    setShowButton(model.getData().getButtonList());
                                    initTimeLineView(model.getData().getFlowHistoryList());

                                    Intent intent = new Intent(mContext, PersonnelSelectionActivity.class);
                                    ConstantsUtil.buttonModel = model.getData().getButtonList().get(0);
                                    buttonId = ConstantsUtil.buttonModel.getButtonId();
                                    startActivityForResult(intent, 201);
                                    LoadingUtils.hideLoading();
                                }
                            });
                        } else {
                            ChildThreadUtil.checkTokenHidden(mContext, model.getMessage(), model.getCode());
                        }
                    } else {
                        ChildThreadUtil.toastMsgHidden(mContext, getString(R.string.json_error));
                    }
                }
            }
        });*/
    }

    PromptListener promptListener = new PromptListener() {
        @Override
        public void returnTrueOrFalse(boolean trueOrFalse) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    p.dismiss();
                }
            });
        }
    };

    /**
     * 图片点击事件监听--->全屏预览图片
     */
    private ShowPhotoListener clickPhotoListener = new ShowPhotoListener() {
        @Override
        public void selectWayOrShowPhoto(boolean isShowPhoto, String point, String photoUrl, int isUpLoad) {
            // 图片浏览
            ArrayList<String> urls = new ArrayList<>();
            int len = photosList.size();
            for (int i = 0; i < len; i++) {
                String fileUrl = photosList.get(i).getUrl();
                urls.add(fileUrl);
            }
            Intent intent = new Intent(mContext, ShowPhotosActivity.class);
            intent.putExtra(ShowPhotosActivity.EXTRA_IMAGE_URLS, urls);
            intent.putExtra(ShowPhotosActivity.EXTRA_IMAGE_INDEX, Integer.valueOf(point));
            startActivity(intent);
        }
    };

    /**
     * 获取定位权限
     */
    @TargetApi(23)
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<>();
            // 定位精确位置
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            // 读写权限
            if (addPermission(permissions, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            }
            // 读取电话状态权限
            if (addPermission(permissions, android.Manifest.permission.READ_PHONE_STATE)) {
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            } else {
                if (JudgeNetworkIsAvailable.isNetworkAvailable(this)) {
                    locationService = ((MyApplication) getApplication()).locationService;
                    locationService.registerListener(mListener);
                    //注册监听
                    int type = getIntent().getIntExtra("from", 0);
                    if (type == 0) {
                        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                    } else if (type == 1) {
                        locationService.setLocationOption(locationService.getOption());
                    }
                    locationService.start();// 定位SDK
                } else {
                    //开启GPS定位
                    gpsLocationManager = GPSLocationManager.getInstances(this);
                    gpsLocationManager.start(new GpsListener());
                }
            }
        } else {
            if (JudgeNetworkIsAvailable.isNetworkAvailable(this)) {
                locationService = ((MyApplication) getApplication()).locationService;
                locationService.registerListener(mListener);
                //注册监听
                int type = getIntent().getIntExtra("from", 0);
                if (type == 0) {
                    locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                } else if (type == 1) {
                    locationService.setLocationOption(locationService.getOption());
                }
                locationService.start();// 定位SDK
            } else {
                //开启GPS定位
                gpsLocationManager = GPSLocationManager.getInstances(this);
                gpsLocationManager.start(new GpsListener());
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (JudgeNetworkIsAvailable.isNetworkAvailable(this)) {
            locationService = ((MyApplication) getApplication()).locationService;
            locationService.registerListener(mListener);
            //注册监听
            int type = getIntent().getIntExtra("from", 0);
            if (type == 0) {
                locationService.setLocationOption(locationService.getDefaultLocationClientOption());
            } else if (type == 1) {
                locationService.setLocationOption(locationService.getOption());
            }
            locationService.start();// 定位SDK
        } else {
            //开启GPS定位
            gpsLocationManager = GPSLocationManager.getInstances(this);
            gpsLocationManager.start(new GpsListener());
        }
    }

    /**
     * GPS定位监听
     */
    private class GpsListener implements GPSLocationListener {
        @Override
        public void UpdateLocation(Location location) {
            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                sLocation = "";
            }
        }

        @Override
        public void UpdateStatus(String provider, int status, Bundle extras) {
        }

        @Override
        public void UpdateGPSProviderStatus(int gpsStatus) {
        }
    }

    /**
     * 定位结果回调
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append(location.getAddrStr());
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                sLocation = sb.toString() == null || TextUtils.isEmpty(sb.toString()) || sb.toString().equals("null") ? "" : sb.toString();
            }
        }
    };

    /**
     * 上传照片--->提交审核
     */
    private void toExaminePhoto(final boolean isToDoType) {
        if (photosList.size() < leastTakePhotoNum) {
            ToastUtil.showShort(mContext, "拍照数量不能小于最少张数！");
            return;
        }

        final List<PhotosBean> submitPictureList = new ArrayList<>();
        // 检查是否有新拍摄的照片
        for (PhotosBean photo : photosList) {
            if (photo.getIsToBeUpLoad() == 1) {
                submitPictureList.add(photo);
            }
        }

        if (submitPictureList.size() > 0) {
            // 上传
            UpLoadPhotosDialog upLoadPhotosDialog = new UpLoadPhotosDialog(mContext, 1, submitPictureList, new PromptListener() {
                @Override
                public void returnTrueOrFalse(boolean trueOrFalse) {
                    if (trueOrFalse) {
                        // 修改已上传照片状态
                        for (PhotosBean phone : photosList) {
                            phone.setIsToBeUpLoad(-1); // 已上传
                        }
                        // 更新适配器
                        if (null != photosAdapter) {
                            photosAdapter.notifyDataSetChanged();
                        }

                        /*if (isToDoType) {
                            boolean isEdit = ConstantsUtil.buttonModel.getNextShowFlowInfoList() == null || ConstantsUtil.buttonModel.getNextShowFlowInfoList().size() == 0 ? false : ConstantsUtil.buttonModel.getNextShowFlowInfoList().get(0).isEdit();
                            Intent intent = new Intent(mContext, PersonnelSelectionActivity.class);
                            intent.putExtra("isEdit", isEdit);
                            startActivityForResult(intent, 201);
                        } else {
                            submitData(isToDoType);
                        }*/
                        submitData(isToDoType);
                    }
                }
            });
            upLoadPhotosDialog.setCanceledOnTouchOutside(false);
            upLoadPhotosDialog.show();
        } else {
            /*if (isToDoType) {
                boolean isEdit = ConstantsUtil.buttonModel.getNextShowFlowInfoList() == null || ConstantsUtil.buttonModel.getNextShowFlowInfoList().size() == 0 ? false : ConstantsUtil.buttonModel.getNextShowFlowInfoList().get(0).isEdit();
                Intent intent = new Intent(mContext, PersonnelSelectionActivity.class);
                intent.putExtra("isEdit", isEdit);
                startActivityForResult(intent, 201);
            } else {
                submitData(isToDoType);
            }*/
            submitData(isToDoType);
        }
    }

    /**
     * 检查拍照权限
     */
    private void checkPhotosPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestAuthority(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, new PermissionListener() {
                @Override
                public void agree() {
                    takePictures();
                }

                @Override
                public void refuse(List<String> refusePermission) {
                    ToastUtil.showLong(mContext, "您已拒绝拍照权限!");
                }
            });
        } else {
            takePictures();
        }
    }

    /**
     * 拍照
     */
    private void takePictures() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUrlName = System.currentTimeMillis() + ".png";
        Uri photoUri = FileProvider.getUriForFile(mContext, ProviderUtil.getFileProviderName(mContext), new File(strFilePath + fileUrlName));
        openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(openCameraIntent, 1);
    }

    /**
     * 保存本地文件
     */
    private void saveLocalFile() {
        LoadingUtils.showLoading(mContext);
        // 向LitePal数据库中添加一条数据
        addPhotoBean = new PhotosBean();
        addPhotoBean.setIsToBeUpLoad(1);
        addPhotoBean.setUrl(ConstantsUtil.SAVE_PATH + fileUrlName);
        addPhotoBean.setProcessId(isToDo ? workId : processId);
        addPhotoBean.setWorkId(workId);
        addPhotoBean.setOtherId(mainTableId);
        addPhotoBean.setThumbUrl(ConstantsUtil.SAVE_PATH + fileUrlName);
        addPhotoBean.setPhotoDesc(processPath); //描述换成rootNodeName
        addPhotoBean.setPhotoName(fileUrlName);
        addPhotoBean.setCheckFlag("-1");
        addPhotoBean.setIsNewAdd(1);
        addPhotoBean.setRoleFlag("1");
        addPhotoBean.setLatitude(String.valueOf(latitude));
        addPhotoBean.setLongitude(String.valueOf(longitude));
        addPhotoBean.setLocation(sLocation);
        addPhotoBean.setUserId((String) SpUtil.get(mContext, ConstantsUtil.USER_ID, ""));
        addPhotoBean.setCreateTime(System.currentTimeMillis());
        String[] strings = new String[]{processPath};
        addPhotoBean.save();
        // 添加图片按钮
        photosList.add(0, addPhotoBean);
        // 异步将图片存储到SD卡指定文件夹下
        new StorageTask().execute(strings);
    }

    /**
     * 将照片存储到SD卡
     */
    private class StorageTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            Bitmap bitmap = BitmapFactory.decodeFile(strFilePath + fileUrlName);
            // 压缩图片
            //bitmap = FileUtil.compressBitmap(bitmap);
            // 在图片上添加水印
            bitmap = ImageUtil.createWaterMaskLeftTop(mContext, bitmap, params[0], addPhotoBean);
            // 保存到SD卡指定文件夹下
            saveBitmapFile(bitmap, fileUrlName);
            // 删除拍摄的照片
            FileUtil.deleteFile(strFilePath + fileUrlName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LoadingUtils.hideLoading();
            if (null != photosAdapter) {
                photosAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 保存图片到SD卡指定目录下
     *
     * @param bitmap
     * @param fileName
     */
    public void saveBitmapFile(Bitmap bitmap, String fileName) {
        // 将要保存图片的路径
        File imgFile = new File(ConstantsUtil.SAVE_PATH);
        if (!imgFile.exists()) {
            imgFile.mkdirs();
        }

        File file = new File(ConstantsUtil.SAVE_PATH + fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @return Bitmap
     */
    public static Bitmap rotateImageView(int angle, String path) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        // 此处采样，导致分辨率降到1/4,否则会报OOM
        bitmapOptions.inSampleSize = 1;
        Bitmap cameraBitmap = BitmapFactory.decodeFile(path, bitmapOptions);
        // 旋转图片 动作
        Matrix matrix = new Matrix();

        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(), cameraBitmap.getHeight(), matrix, true);
        cameraBitmap.recycle();
        return resizedBitmap;
    }

    /**
     * 保存图片
     *
     * @param bm
     * @param path
     * @param filename
     * @return
     */
    private String saveBitmap(Bitmap bm, String path, String filename) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

        path = path + "/" + filename;
        File f2 = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(f2);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    saveLocalFile();
                    break;
                case 201:
                    JSONObject object = new JSONObject(jsonData);
                    object.getJSONObject("data").put("buttonId", buttonId);
                    object.getJSONObject("data").put("reviewNodeId", data.getStringExtra("reviewNodeId"));
                    object.getJSONObject("data").put("opinionContent", edtRemarks.getText().toString().trim());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", data.getStringExtra("userId"));
                    jsonObject.put("label", data.getStringExtra("userName"));
                    jsonObject.put("type", data.getStringExtra("type"));
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.add(jsonObject);
                    object.getJSONObject("data").put("reviewUserObjectList", jsonArray);
                    jsonData = object.toString();
                    submitData(true);
                    break;
                case 202:
                    if (myRecognizer != null) {
                        myRecognizer.release();
                    }

                    String message = edtRemarks.getText().toString();
                    ArrayList results = data.getStringArrayListExtra("results");
                    edtRemarks.setText(message);
                    break;
                default:
                    break;
            }
        } else {
            if (myRecognizer != null) {
                myRecognizer.release();
            }
        }
    }

    /**
     * 申请语音输入权限
     */
    private void initVoicePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            String permissions[] = {android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            requestAuthority(permissions, new PermissionListener() {
                @Override
                public void agree() {
                    speechInput();
                }

                @Override
                public void refuse(List<String> refusePermission) {
                    ToastUtil.showShort(mContext, "您拒绝了语音输入所需权限权限!");
                }
            });
        } else {
            speechInput();
        }
    }

    /**
     * 语音输入
     */
    private void speechInput() {
        // 1. 确定识别参数
        Map<String, Object> params = new LinkedHashMap<>();
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0); // 长语音
        ChainRecogListener listener = new ChainRecogListener();
        myRecognizer = new MyRecognizer(mContext, listener);
        DigitalDialogInput input = new DigitalDialogInput(myRecognizer, listener, params);
        Intent intent = new Intent(mContext, BaiduASRDigitalDialog.class);
        ((MyApplication) mContext.getApplicationContext()).setDigitalDialogInput(input);
        mContext.startActivityForResult(intent, 202);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Event({R.id.imgBtnLeft, R.id.imgBtnAdd, R.id.ibRemarks})
    private void onClick(View v) {
        switch (v.getId()) {
            // 返回
            case R.id.imgBtnLeft:
                this.finish();
                break;
            // 拍照
            case R.id.imgBtnAdd:
                String sdCardSize = AppInfoUtil.getSDAvailableSize();
                if (Integer.valueOf(sdCardSize) < 10) {
                    ToastUtil.showShort(mContext, "当前手机内存卡已无可用空间，请清理后再进行拍照！");
                } else {
                    if (sLocation == null || sLocation.length() < 7 || sLocation.contains("正在定位")) {
                        PromptDialog promptDialog = new PromptDialog(mContext, new PromptListener() {
                            @Override
                            public void returnTrueOrFalse(boolean trueOrFalse) {
                                if (trueOrFalse) {
                                    checkPhotosPermission();
                                }
                            }
                        }, "提示", "未定位到当前位置，拍照后会导致拍摄照片无地理位置信息。是否继续拍照？", "否", "是");
                        promptDialog.show();
                    } else {
                        checkPhotosPermission();
                    }
                }
                break;
            // 意见语音
            case R.id.ibRemarks:
                initVoicePermissions();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (p != null) {
            p.dismiss();
        }
        ScreenManagerUtil.popActivity(this);    // 退出当前activity
        // 终止定位
        if (gpsLocationManager != null) {
            gpsLocationManager.stop();
        }
        if (locationService != null) {
            locationService.stop();
        }
    }
}
