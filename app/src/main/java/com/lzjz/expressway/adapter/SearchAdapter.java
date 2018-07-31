package com.lzjz.expressway.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzjz.expressway.R;
import com.lzjz.expressway.bean.ContractorBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by HaiJun on 2017/11/9 17:22
 */

public class SearchAdapter extends BaseAdapter {
    private Context mContext;
    private List<ContractorBean> contractorBeenList;
    private LayoutInflater inflater;

    public SearchAdapter(Context mContext, List<ContractorBean> contractorBeenList) {
        this.mContext = mContext;
        this.contractorBeenList = contractorBeenList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return contractorBeenList == null ? 0 : contractorBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final SelectAuditorsHandler selectAuditorsHandler;
        if (view == null) {
            selectAuditorsHandler = new SelectAuditorsHandler();
            view = inflater.inflate(R.layout.item_select_auditors, null);
            x.view().inject(selectAuditorsHandler, view);

            view.setTag(selectAuditorsHandler);
        } else {
            selectAuditorsHandler = (SelectAuditorsHandler) view.getTag();
        }

        boolean isSelect = contractorBeenList.get(position).isSelect();
        Drawable drawable;
        if (isSelect) {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.btn_check);
        } else {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.btn_un_check);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        selectAuditorsHandler.txtAuditors.setCompoundDrawables(null, null, drawable, null);

        selectAuditorsHandler.txtAuditors.setText(contractorBeenList.get(position).getParentNameAll().replaceAll(",", "→"));

        return view;
    }

    private class SelectAuditorsHandler {
        @ViewInject(R.id.txtAuditors)
        private TextView txtAuditors;
    }
}
