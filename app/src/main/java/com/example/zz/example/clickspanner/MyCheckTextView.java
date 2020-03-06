package com.example.zz.example.clickspanner;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/3/6
 */
public class MyCheckTextView extends ClickableSpan {
    private Context context;
    public MyCheckTextView(Context context) {
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        //设置文本的颜色
        ds.setColor(Color.parseColor("#1A7DC2"));
        //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线,其实默认也是true，如果要下划线的话可以不设置
        ds.setUnderlineText(true);
    }

    //点击事件，自由操作
    @Override
    public void onClick(View widget) {
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.setData(Uri.parse("https://www.hao123.com/"));
        context.startActivity(it);
    }
}
