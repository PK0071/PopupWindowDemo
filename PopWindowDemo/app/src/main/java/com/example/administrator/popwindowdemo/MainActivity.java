package com.example.administrator.popwindowdemo;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private String[] mAgeList;
    private TextView mTvWheel;
    private TextView mTvCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAgeList = getResources().getStringArray(R.array.ageStatus);
        mTvWheel = (TextView) findViewById(R.id.tv_wheel);
        mTvCustom = (TextView) findViewById(R.id.tv_custom);
        mTvWheel.setOnClickListener(this);
        mTvCustom.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wheel://WheelPopupWindow
                WheelPopupWindow wheelPopupWindow = new WheelPopupWindow(this, mAgeList);
                wheelPopupWindow.setBtnCallBack(new WheelPopupWindow.BtnCallBack() {
                    @Override
                    public void confirm(int index) {
                        Toast.makeText(MainActivity.this, "index = " + index, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public boolean cancle() {
                        return true;
                    }
                });
//                wheelPopupWindow.setSelectDefault(0);//初始化选中第一项，可选
//                wheelPopupWindow.setSelectColor(0xffff3366);//选中颜色id，可选
//                wheelPopupWindow.setShowOffset(2);//选中位置上下方可以显示数量，可选
                wheelPopupWindow.show();
                break;

            case R.id.tv_custom://CustomViewPopupWindow使用
                final CustomViewPopupWindow customViewPopupWindow = new CustomViewPopupWindow(this, R.layout.popup_getpic);
                customViewPopupWindow.show();
                //自己处理view的点击事件
                View myView = customViewPopupWindow.getCustomView();
                Button button_takePhoto = (Button) myView.findViewById(R.id.btn_takePhoto);
                Button button_cancle = (Button) myView.findViewById(R.id.btn_cancel);

                button_takePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "拍照", Toast.LENGTH_SHORT).show();
                    }
                });

                button_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customViewPopupWindow.dismiss();
                    }
                });
                break;
        }

    }
}
