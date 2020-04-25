package com.example.zz.example.opscreenrecorder.Interceptor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/16
 */
public class DialogInterceptor extends BaseInterceptor {

    public DialogInterceptor(Interceptor interceptor) {
        super(interceptor);
    }

    @Override
    public void intercept(final Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("自定义弹框测试")
                .setMessage("弹框信息声明")
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNextInterceptor.intercept(activity);
                    }
                })
                .setNegativeButton("取消，退出界面", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });
        builder.create();
        builder.show();

    }
}
