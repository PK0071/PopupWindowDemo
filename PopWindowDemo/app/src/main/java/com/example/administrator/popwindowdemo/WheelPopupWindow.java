package com.example.administrator.popwindowdemo;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.Arrays;

/**
 * 底部滑出的滚动选择器
 * Author: chenyouren
 * Date: 2016/5/13.
 */
public class WheelPopupWindow {

    private int mShowOffset = 1;//选中位置上下方可以显示数量
    private int mSelectDefault = 0;//初始选择项
    private int mSelectColor = 0xff666666;//选中颜色

    private Context mContext;
    private View mWindow;
    private WheelView mWheelView;
    private PopupWindow mPopupWindow;
    private BtnCallBack mCallBack;
    private String[] mData;

    /**
     * @param context
     * @param data 数据
     */
    public WheelPopupWindow(Context context, String[] data) {
        this.mContext = context;
        this.mData = data;
        //initView(data);
    }

    private void initView(String[] data) {
        mWindow = View.inflate(mContext, R.layout.popup_setsex, null);
        mWheelView = (WheelView) mWindow.findViewById(R.id.wl_sex);
        TextView btn_cancel = (TextView) mWindow.findViewById(R.id.btn_cancel);
        TextView btn_sure = (TextView) mWindow.findViewById(R.id.btn_sure);

        mPopupWindow = new PopupWindow(mWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);//获取焦点防止弹出多个

        //弹出背景变灰色
        if (mContext instanceof Activity) {
            WindowManager.LayoutParams params = ((Activity)mContext).getWindow().getAttributes();
            params.alpha = 0.7f;
            ((Activity)mContext).getWindow().setAttributes(params);
        }

        mWheelView.setOffset(mShowOffset);
        mWheelView.setSeletion(mSelectDefault);
        mWheelView.setSelectColor(mSelectColor);
        mWheelView.setItems(Arrays.asList(data));

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    mCallBack.confirm(mWheelView.getSeletedIndex());//回调选中的index
                    dismiss();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    if (mCallBack.cancle()) {
                        dismiss();
                    }
                }
            }
        });

    }
    
    //显示
    public void show() {
        initView(mData);
        mPopupWindow.setAnimationStyle(R.style.PWAnimationFade);//弹出动画
        mPopupWindow.showAtLocation(mWindow.findViewById(R.id.main), Gravity.BOTTOM, 0, 0);
    }

    //消失
    private void dismiss() {
        mPopupWindow.dismiss();
        mPopupWindow = null;
        //恢复背景
        if (mContext instanceof  Activity) {
            WindowManager.LayoutParams params = ((Activity)mContext).getWindow().getAttributes();
            params.alpha = 1.0f;
            ((Activity)mContext).getWindow().setAttributes(params);
        }
    }

    public void setSelectDefault(int mSelectDefault) {
        this.mSelectDefault = mSelectDefault;
    }

    public void setShowOffset(int mShowOffset) {
        this.mShowOffset = mShowOffset;
    }

    public void setSelectColor(int mSelectColor) {
        this.mSelectColor = mSelectColor;
    }

    public void setBtnCallBack(BtnCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    /**
     * 两个按钮回调
     */
    public interface BtnCallBack {
        public void confirm(int reusltIndex);
        public boolean cancle();
    }
}
