package com.lzjz.expressway.InJavaScript;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.lzjz.expressway.activity.MainActivity;
import com.lzjz.expressway.base.BaseInJavaScript;
import com.lzjz.expressway.utils.ConstantsUtil;
import com.lzjz.expressway.utils.ScreenManagerUtil;
import com.lzjz.expressway.utils.SpUtil;

/**
 * Create dell By 2018/7/9 9:37
 */

public class MailListInJavaScript extends BaseInJavaScript {
    private Activity mContext;
    private WebView mWebView = null;

    public MailListInJavaScript(Context mContext, WebView webView) {
        super(mContext);
        this.mWebView = webView;
        this.mContext = (Activity) mContext;
    }

    @JavascriptInterface
    public String getLoginToken() {
        String token = (String) SpUtil.get(mContext, ConstantsUtil.TOKEN, "");
        return token;
    }

    @JavascriptInterface
    public void closeActivity() {
        ScreenManagerUtil.popAllActivityExceptOne(MainActivity.class);
    }

    @JavascriptInterface
    public String getSubmitData() {
        return (String) SpUtil.get(mContext, "JSONData", "");
    }
}
