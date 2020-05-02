package com.example.zz.example.pattern.responsibilitychain;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.zz.example.pattern.responsibilitychain.Interceptor.DialogInterceptor;
import com.example.zz.example.pattern.responsibilitychain.Interceptor.OverlyInterceptor;
import com.example.zz.example.pattern.responsibilitychain.Interceptor.PermissionInterceptor;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/16
 */
public class ResponsibilityChainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intercept();
    }

    private void Intercept() {
        OverlyInterceptor overlyInterceptor = new OverlyInterceptor(null);
        PermissionInterceptor permissionInterceptor = new PermissionInterceptor(overlyInterceptor);
        DialogInterceptor dialogInterceptor = new DialogInterceptor(permissionInterceptor);
        dialogInterceptor.intercept(this);
    }
}

