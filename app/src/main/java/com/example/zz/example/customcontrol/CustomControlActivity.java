package com.example.zz.example.customcontrol;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.zz.example.LogUtils;
import com.example.zz.example.R;

public class CustomControlActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("onCreate");
        setContentView(R.layout.activity_custom_control);
        IconView iconView = findViewById(R.id.start_icon_view);
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconView.startToMove();
                Point point = new Point(0, 0);
                point = null;
                try{
                    point.getClass();
                } catch (Exception e){
                    LogUtils.i("e = " + e);
                }
            }
        });

        Button rectToRing = findViewById(R.id.rect_to_ring);
        rectToRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconView.ringChangeToRect(false);
            }
        });

        findViewById(R.id.ring_to_rect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconView.ringChangeToRect(true);
                addView();
            }
        });

    }

    private void addView() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog,null);    //加载View视图，这个就是我们要显示的内容
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);    //获取WindowManage
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        //设置LayoutParams的属性
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;       //该Type描述的是形成的窗口的层级关系，下面会详细列出它的属性
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |       //该flags描述的是窗口的模式，是否可以触摸，可以聚焦等
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        layoutParams.gravity = Gravity.TOP;                                       //设置窗口的位置
        layoutParams.format = PixelFormat.TRANSLUCENT;                               //不设置这个弹出框的透明遮罩显示为黑色
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;                //窗口的宽
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;               //窗口的高
//        layoutParams.token = ((View)findViewById(R.id.ring_to_rect)).getWindowToken();           //获取当前Activity中的View中的TOken,来依附Activity，因为设置了该值，纳闷写的这些代码不能出现在onCreate();否则会报错。
        windowManager.addView(view,layoutParams);                                   //显示窗口
    }
}
