package com.lzjz.expressway.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.lzjz.expressway.R;
import com.lzjz.expressway.adapter.AppInfoAdapter;
import com.lzjz.expressway.base.BaseActivity;
import com.lzjz.expressway.bean.MainPageBean;
import com.lzjz.expressway.loader.GlideImageLoader;
import com.lzjz.expressway.utils.ConstantsUtil;
import com.lzjz.expressway.utils.JudgeNetworkIsAvailable;
import com.lzjz.expressway.utils.SpUtil;

import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HaiJun on 2018/6/11 16:32
 * 应用图标列表
 */
public class AppActivity extends BaseActivity {
    private AppHold hold;
    private Activity mContext;
    private AppInfoAdapter appInfoAdapter;

    public AppActivity(Activity mContext, View layoutApp) {
        this.mContext = mContext;
        hold = new AppHold();
        x.view().inject(hold, layoutApp);
    }

    /**
     * 赋值
     *
     * @param objList
     */
    public void setDate(List<MainPageBean> objList, List<MainPageBean> appIconList) {
        List<String> urlList = new ArrayList<>();
        final List<String> strList = new ArrayList<>();
        final List<String> idList = new ArrayList<>();
        if (objList != null && objList.size() != 0) {
            for (MainPageBean bean : objList) {
                idList.add(bean.getViewId());
                urlList.add(bean.getFileUrl());
                strList.add(bean.getViewSummary());
            }
        }

        //设置banner样式
        hold.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        hold.banner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        hold.banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        hold.banner.isAutoPlay(true);
        //设置轮播时间
        hold.banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        hold.banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置图片集合
        hold.banner.setImages(urlList);
        hold.banner.start();
        // 设置图片下文字信息
        hold.txtEnterPriseInfo.setText(strList.size() > 0 ? strList.get(0) : "");
        // 活动监听
        hold.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                hold.txtEnterPriseInfo.setText(strList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // 点击事件
        hold.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(mContext, EditScrollPhotoActivity.class);
                intent.putExtra("url", ConstantsUtil.Scroll_Photo + idList.get(position) + "/" + SpUtil.get(mContext, ConstantsUtil.TOKEN, ""));
                intent.putExtra("title", "详情");
                mContext.startActivity(intent);
            }
        });

        // 待审批点击
        hold.llToBeExamined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkingProcedureActivity.class);
                SpUtil.put(mContext, ConstantsUtil.PROCESS_LIST_TYPE, "4");
                SpUtil.put(mContext, "MANAGER_TYPE", "1");
                SpUtil.put(mContext, "PROCESS_TYPE", "4");
                mContext.startActivity(intent);
            }
        });

        // 已审批点击
        hold.llHaveBeenApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkingProcedureActivity.class);
                SpUtil.put(mContext, ConstantsUtil.PROCESS_LIST_TYPE, "4");
                SpUtil.put(mContext, "MANAGER_TYPE", "2");
                SpUtil.put(mContext, "PROCESS_TYPE", "4");
                mContext.startActivity(intent);
            }
        });

        // 删除---保持应用信息
        if (JudgeNetworkIsAvailable.isNetworkAvailable(mContext) && appIconList != null) {
            DataSupport.deleteAll(MainPageBean.class, "type=1");
            for (MainPageBean bean : appIconList) {
                bean.setType("1");
                bean.save();
            }
        }

        // 添加图片、标题
        appInfoAdapter = new AppInfoAdapter(mContext, appIconList);
        hold.rvAppInfo.setLayoutManager(new GridLayoutManager(mContext, 4));
        hold.rvAppInfo.setAdapter(appInfoAdapter);
    }

    /**
     * 开始轮播
     */
    public void startBanner() {
        //开始轮播
        if (hold != null && hold.banner != null) {
            hold.banner.startAutoPlay();
        }
    }

    /**
     * 停止轮播
     */
    public void stopBanner() {
        if (hold != null && hold.banner != null) {
            hold.banner.stopAutoPlay();
        }
    }

    /**
     * 容纳器
     */
    private class AppHold {
        @ViewInject(R.id.banner)
        private Banner banner;

        @ViewInject(R.id.rvAppInfo)
        private RecyclerView rvAppInfo;
        @ViewInject(R.id.txtEnterPriseInfo)
        private TextView txtEnterPriseInfo;
        @ViewInject(R.id.llToBeExamined)
        private LinearLayout llToBeExamined;
        @ViewInject(R.id.llHaveBeenApproved)
        private LinearLayout llHaveBeenApproved;

    }
}
