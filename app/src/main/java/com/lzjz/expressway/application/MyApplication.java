package com.lzjz.expressway.application;


import android.app.Application;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.tencent.bugly.crashreport.CrashReport;
import com.lzjz.expressway.service.LocationService;
import com.lzjz.expressway.ui.DigitalDialogInput;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.xutils.x;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 *                     _ooOoo_
 *                    o8888888o
 *                    88" . "88
 *                    (| -_- |)
 *                    O\  =  /O
 *                 ____/`---'\____
 *               .'  \\|     |//  `.
 *              /  \\|||  :  |||//  \
 *             /  _||||| -:- |||||-  \
 *             |   | \\\  -  /// |   |
 *             | \_|  ''\---/''  |   |
 *             \  .-\__  `-`  ___/-. /
 *           ___`. .'  /--.--\  `. . __
 *        ."" '<  `.___\_<|>_/___.'  >'"".
 *       | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *       \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *                     `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * 			   佛祖保佑       永无BUG
 *       Created by HaiJun on 2018/6/11 17:16
 */
public class MyApplication extends LitePalApplication {
    public static Application instance;
    public LocationService locationService;
    private DigitalDialogInput digitalDialogInput;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);
        LitePal.initialize(this);
        instance = this;

        CrashReport.initCrashReport(getApplicationContext(), "29d5c5f537", false);

        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);
        locationService = new LocationService(getApplicationContext());
        // 身份证扫描
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {

            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
            }
        }, getApplicationContext(), "undHal7s14rjqngTPwQagAKS", "oaL6hP0H8shhiZ3K2iKh90LY2h3RLbRZ");
    }

    public DigitalDialogInput getDigitalDialogInput() {
        return digitalDialogInput;
    }

    public void setDigitalDialogInput(DigitalDialogInput digitalDialogInput) {
        this.digitalDialogInput = digitalDialogInput;
    }
}
