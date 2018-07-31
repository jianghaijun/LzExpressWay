package com.lzjz.expressway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzjz.expressway.R;
import com.lzjz.expressway.bean.HistoryBean;
import com.lzjz.expressway.utils.DateUtils;

import java.util.List;

/**
 * Created by HaiJun on 2018/6/11 17:03
 * 瀑布流格式时间轴
 */
public class WaterfallFlowTimeLineAdapter extends RecyclerView.Adapter<WaterfallFlowTimeLineAdapter.ViewHolder> {
    private Context mContext;
    private List<HistoryBean> mList;

    public WaterfallFlowTimeLineAdapter(Context context, List<HistoryBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resource;
        if (mList.size() == 1) {
            resource = R.layout.item_one_dot_time_line;
        } else {
            resource = R.layout.item_dot_time_line;
        }
        View view = LayoutInflater.from(mContext).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryBean historyBean = mList.get(position);
        holder.txtUser.setText(historyBean.getRealName() + "   " + historyBean.getNodeName());
        holder.txtArriveDate.setText("到达时间：" + DateUtils.setDataToStr(historyBean.getActionTime()) + "");
        holder.txtUseTime.setText("累计时长：" + historyBean.getDoTimeShow() + "");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUser;
        TextView txtArriveDate;
        TextView txtUseTime;

        public ViewHolder(View view) {
            super(view);
            txtUser = (TextView) view.findViewById(R.id.txtUser);
            txtArriveDate = (TextView) view.findViewById(R.id.txtArriveDate);
            txtUseTime = (TextView) view.findViewById(R.id.txtUseTime);
        }
    }

}
