package com.lzjz.expressway.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzjz.expressway.R;
import com.lzjz.expressway.activity.AuditManagementActivity;
import com.lzjz.expressway.activity.EditScrollPhotoActivity;
import com.lzjz.expressway.activity.ProcessReportActivity;
import com.lzjz.expressway.activity.QrCodeScanActivity;
import com.lzjz.expressway.activity.QualityInspectionActivity;
import com.lzjz.expressway.activity.WorkingProcedureActivity;
import com.lzjz.expressway.bean.MainPageBean;
import com.lzjz.expressway.model.SameDayModel;
import com.lzjz.expressway.utils.ChildThreadUtil;
import com.lzjz.expressway.utils.ConstantsUtil;
import com.lzjz.expressway.utils.JsonUtils;
import com.lzjz.expressway.utils.JudgeNetworkIsAvailable;
import com.lzjz.expressway.utils.LoadingUtils;
import com.lzjz.expressway.utils.SpUtil;
import com.lzjz.expressway.utils.ToastUtil;
import com.lzjz.expressway.view.TouchHighlightImageButton;

import java.io.IOException;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HaiJun on 2018/6/11 17:01
 * 应用信息适配器
 */
public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.AppInfoHold> {
    private Activity mContext;
    private List<MainPageBean> appInfoBeanList;

    public AppInfoAdapter(Context mContext, List<MainPageBean> appInfoBeanList) {
        this.mContext = (Activity) mContext;
        this.appInfoBeanList = appInfoBeanList;
    }

    @Override
    public AppInfoHold onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppInfoHold(LayoutInflater.from(mContext).inflate(R.layout.item_app_info, parent, false));
    }

    @Override
    public void onBindViewHolder(AppInfoHold holder, final int position) {
        // 应用图标
        Glide.with(mContext).load(appInfoBeanList.get(position).getSmallModuleIcon()).into(holder.imgView);
        // 应用名称
        holder.txtTitle.setText(appInfoBeanList.get(position).getSmallModuleTitle());
        // 应用通知数量
        String unReadNum = appInfoBeanList.get(position).getSmallModuleCount();
        int num = Integer.valueOf(unReadNum);
        if (num != 0) {
            holder.txtSubmitPhoneNum.setVisibility(View.VISIBLE);
            if (num > 99) {
                holder.txtSubmitPhoneNum.setTextSize(6);
            }
            holder.txtSubmitPhoneNum.setText(num > 99 ? "99+" : num + "");
        }
        // 图标点击事件
        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (appInfoBeanList.get(position).getSmallModuleLink()) {
                    // 工序检查
                    case "concealmentProjectActivity":
                        intent = new Intent(mContext, AuditManagementActivity.class);
                        SpUtil.put(mContext, ConstantsUtil.PROCESS_LIST_TYPE, "1");
                        SpUtil.put(mContext, "PROCESS_TYPE", "1");
                        SpUtil.put(mContext, "showSelectBtn", true);
                        mContext.startActivity(intent);
                        break;
                    // 质量
                    case "qualityTestingActivity":
                        intent = new Intent(mContext, QualityInspectionActivity.class);
                        SpUtil.put(mContext, ConstantsUtil.PROCESS_LIST_TYPE, "2");
                        SpUtil.put(mContext, "PROCESS_TYPE", "2");
                        SpUtil.put(mContext, "showSelectBtn", true);
                        mContext.startActivity(intent);
                        break;
                    // 安全巡查
                    case "hiddenTroubleInvestigationActivity":
                        intent = new Intent(mContext, QualityInspectionActivity.class);
                        SpUtil.put(mContext, ConstantsUtil.PROCESS_LIST_TYPE, "3");
                        SpUtil.put(mContext, "PROCESS_TYPE", "3");
                        SpUtil.put(mContext, "showSelectBtn", true);
                        mContext.startActivity(intent);
                        break;
                    // 审核管理
                    case "auditManagementActivity":
                        intent = new Intent(mContext, WorkingProcedureActivity.class);
                        SpUtil.put(mContext, ConstantsUtil.PROCESS_LIST_TYPE, "4");
                        SpUtil.put(mContext, "PROCESS_TYPE", "4");
                        SpUtil.put(mContext, "MANAGER_TYPE", "1");
                        mContext.startActivity(intent);
                        /*intent = new Intent(mContext, EditScrollPhotoActivity.class);
                        intent.putExtra("url", ConstantsUtil.audit_management);
                        intent.putExtra("title", "审核管理");
                        mContext.startActivity(intent);*/
                        break;
                    // 工序报表
                    case "dataReportActivity":
                        if (JudgeNetworkIsAvailable.isNetworkAvailable(mContext)) {
                            getSameDayData();
                        } else {
                            ToastUtil.showShort(mContext, mContext.getString(R.string.not_network));
                        }
                        break;
                    // 工序管理
                    /*case 5:
                        intent = new Intent(mContext, ProcessManagerActivity.class);
                        mContext.startActivity(intent);
                        break;*/
                    // 二维码扫描
                    case "laborServicePersonnelActivity":
                        intent = new Intent(mContext, QrCodeScanActivity.class);
                        mContext.startActivity(intent);
                        break;
                    // 地图
                    case "mapDisplayActivity":
                        intent = new Intent(mContext, EditScrollPhotoActivity.class);
                        intent.putExtra("url", ConstantsUtil.Map);
                        intent.putExtra("title", "地图");
                        mContext.startActivity(intent);
                        break;
                    // 资料库 resourceCenterActivity
                    default:
                        ToastUtil.showShort(mContext, "该功能正在开发中，敬请期待!");
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appInfoBeanList == null ? 0 : appInfoBeanList.size();
    }

    /**
     * 获取数据
     */
    private void getSameDayData() {
        LoadingUtils.showLoading(mContext);
        JSONObject obj = new JSONObject();
        obj.put("startDate", System.currentTimeMillis());
        obj.put("endDate", DateUtil.tomorrow().getTime());
        Request request = ChildThreadUtil.getRequest(mContext, ConstantsUtil.PROCESS_REPORT_TODAY, obj.toString());
        ConstantsUtil.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ChildThreadUtil.toastMsgHidden(mContext, mContext.getString(R.string.server_exception));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string().toString();
                if (JsonUtils.isGoodJson(jsonData)) {
                    Gson gson = new Gson();
                    SameDayModel model = gson.fromJson(jsonData, SameDayModel.class);
                    if (model.isSuccess()) {
                        ConstantsUtil.sameDayBean = model.getData();
                        mContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(mContext, ProcessReportActivity.class);
                                mContext.startActivity(intent);
                                LoadingUtils.hideLoading();
                            }
                        });
                    } else {
                        ChildThreadUtil.checkTokenHidden(mContext, model.getMessage(), model.getCode());
                    }
                } else {
                    ChildThreadUtil.toastMsgHidden(mContext, mContext.getString(R.string.json_error));
                }
            }
        });
    }

    /**
     * 容纳器
     */
    public class AppInfoHold extends RecyclerView.ViewHolder {
        private TouchHighlightImageButton imgView;
        private TextView txtTitle;
        private TextView txtSubmitPhoneNum;

        public AppInfoHold(View itemView) {
            super(itemView);
            imgView = (TouchHighlightImageButton) itemView.findViewById(R.id.imgInfo);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtSubmitPhoneNum = (TextView) itemView.findViewById(R.id.txtSubmitPhoneNum);
        }
    }

}
