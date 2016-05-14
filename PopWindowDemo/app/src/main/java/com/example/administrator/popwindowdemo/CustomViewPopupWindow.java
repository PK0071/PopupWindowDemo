package com.example.administrator.popwindowdemo;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * 底部滑出的自定义view的popupwindow
 * Author: chenyouren
 * Date: 2016/5/14.
 */
public class CustomViewPopupWindow {

    private Context mContext;
    private PopupWindow mPopupWindow;
    private View mWindow;
    private int mLayoutId;

    /**
     * @param mContext
     * @param mLayoutId 需要显示的布局id
     */
    public CustomViewPopupWindow(Context mContext, int mLayoutId) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
    }

    public void initView() {
        mWindow = View.inflate(mContext, mLayoutId, null);

        mPopupWindow = new PopupWindow(mWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);//获取焦点防止弹出多个

        //弹出背景变灰色
        if (mContext instanceof Activity) {
            WindowManager.LayoutParams params = ((Activity)mContext).getWindow().getAttributes();
            params.alpha = 0.7f;
            ((Activity)mContext).getWindow().setAttributes(params);
        }
    }

    //显示
    public void show() {
        initView();
        mPopupWindow.setAnimationStyle(R.style.PWAnimationFade);//弹出动画
        mPopupWindow.showAtLocation(mWindow.findViewById(R.id.main), Gravity.BOTTOM, 0, 0);
    }

    //消失
    public void dismiss() {
        mPopupWindow.dismiss();
        mPopupWindow = null;
        //恢复背景
        if (mContext instanceof  Activity) {
            WindowManager.LayoutParams params = ((Activity)mContext).getWindow().getAttributes();
            params.alpha = 1.0f;
            ((Activity)mContext).getWindow().setAttributes(params);
        }
    }

    /**
     * 获取自定义view对象
     * @return
     */
    public View getCustomView() {
        return mWindow;
    }
}
