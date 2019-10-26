package com.example.zz.example.memorylink;

import android.content.Context;

class TestUtil {
    private static TestUtil instance;
    private final Context mContext;

    public TestUtil(Context context) {
        this.mContext = context;
    }

    public static TestUtil getInstance(Context context) {
        if (instance == null) {
            instance = new TestUtil(context);
        }
        return instance;
    }
}
