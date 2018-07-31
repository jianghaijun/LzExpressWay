package com.lzjz.expressway.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzjz.expressway.R;
import com.lzjz.expressway.activity.ContractorDetailsActivity;
import com.lzjz.expressway.bean.WorkingBean;
import com.lzjz.expressway.utils.ConstantsUtil;

import java.util.List;

/**
 * Created by HaiJun on 2018/6/11 17:11
 * 工序列表适配器
 */
public class ProcessListAdapter extends RecyclerView.Adapter<ProcessListAdapter.ProcessHolder> {
    private Activity mContext;
    private List<WorkingBean> workingBeanList;

    /**
     * 重载
     *
     * @param mContext
     * @param workingBeanList
     */
    public ProcessListAdapter(Context mContext, List<WorkingBean> workingBeanList) {
        this.mContext = (Activity) mContext;
        this.workingBeanList = workingBeanList;
    }

    @Override
    public ProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProcessHolder(LayoutInflater.from(mContext).inflate(R.layout.item_to_do_take_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(final ProcessHolder holder, final int position) {
        holder.bind(workingBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return workingBeanList == null ? 0 : workingBeanList.size();
    }

    public class ProcessHolder extends RecyclerView.ViewHolder {
        private TextView txtReviewProgress; // 审核状态
        private TextView txtProcedureName;  // 工序名称
        private TextView txtProcedurePath;  // 工序部位
        /* private ImageView imgViewProgress;  // 拍照
         private ImageView imgViewTakePhoto; // 拍照
         private TextView txtProcedureState; // 拍照状态
         private TextView txtPersonals;      // 审核人员
         private TextView txtCheckTime;      // 检查时间*/
        private RelativeLayout rlProcedurePath;      // 检查时间
        /*private RelativeLayout rlBottom;      //*/

        public ProcessHolder(View itemView) {
            super(itemView);
            txtReviewProgress = (TextView) itemView.findViewById(R.id.txtReviewProgress);
            txtProcedureName = (TextView) itemView.findViewById(R.id.txtProcedureName);
            txtProcedurePath = (TextView) itemView.findViewById(R.id.txtProcedurePath);
            /*txtProcedureState = (TextView) itemView.findViewById(R.id.txtProcedureState);
            txtPersonals = (TextView) itemView.findViewById(R.id.txtPersonals);
            txtCheckTime = (TextView) itemView.findViewById(R.id.txtCheckTime);
            imgViewTakePhoto = (ImageView) itemView.findViewById(R.id.imgViewTakePhoto);
            imgViewProgress = (ImageView) itemView.findViewById(R.id.imgViewProgress);*/
            rlProcedurePath = (RelativeLayout) itemView.findViewById(R.id.rlProcedurePath);
            /*rlBottom = (RelativeLayout) itemView.findViewById(R.id.rlBottom);*/
        }

        public void bind(WorkingBean data) {
            //txtProcedureState.setText("未提交");
            txtProcedureName.setText(data.getProcessName());
            txtProcedurePath.setText(data.getLevelNameAll().replaceAll(",", "→"));
            txtReviewProgress.setText("待拍照");
            rlProcedurePath.setOnClickListener(new onClick(data));

            /*txtPersonals.setText(StrUtil.isEmpty(data.getCheckNameAll()) ? "未审核" : data.getCheckNameAll());
            txtCheckTime.setText(DateUtil.format(DateUtil.date(data.getEnterTime() == 0 ? System.currentTimeMillis() : data.getEnterTime()), "yyyy-MM-dd HH:mm:ss"));
            imgViewTakePhoto.setOnClickListener(new onClick(data));
            rlBottom.setOnClickListener(new onClick(data));
            txtReviewProgress.setOnClickListener(new onClick(data));
            imgViewProgress.setOnClickListener(new onClick(data));*/
        }
    }

    /**
     * 点击事件
     */
    private class onClick implements View.OnClickListener {
        private WorkingBean workingBean;

        public onClick(WorkingBean workingBean) {
            this.workingBean = workingBean;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rlProcedurePath:
                    takePhotoActivity(workingBean, false);
                    break;
            }
        }
    }

    /**
     * 跳转到详情
     */
    private void takePhotoActivity(WorkingBean bean, boolean isPopTakePhoto) {
        Intent intent = new Intent(mContext, ContractorDetailsActivity.class);
        intent.putExtra("flowId", ConstantsUtil.flowId);
        intent.putExtra("workId", bean.getWorkId());
        intent.putExtra("isLocalAdd", bean.getIsLocalAdd());
        intent.putExtra("mainTablePrimaryId", bean.getProcessId());
        intent.putExtra("processId", bean.getProcessId());
        intent.putExtra("isToDo", false);
        intent.putExtra("isPopTakePhoto", isPopTakePhoto);
        mContext.startActivity(intent);
    }

    /**
     * 跳转审核进度界面
     */
    /*private void reviewProgressActivity(String processId) {
        Intent intent = new Intent(mContext, ReviewProgressActivity.class);
        intent.putExtra("processId", processId);
        intent.putExtra("workId", "");
        mContext.startActivity(intent);
    }*/
}
