package com.example.zz.example.pattern.factory;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/2
 */
public class PhoneStateChangeMonitor implements BaseMonitor {

    private Context mContext;
    private TelephonyManager mTelephonyManager;
    private PhoneStateListener phoneStateListener = new PhoneStateChangeListener();

    public PhoneStateChangeMonitor(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        //反注册
        mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyManager != null) {
            mTelephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    @Override
    public void onDestory() {
        //反注册
        mTelephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
    }

    private class PhoneStateChangeListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:// 电话挂断
                    break;
                case TelephonyManager.CALL_STATE_RINGING:// 电话响铃
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK: // 来电接通 或者 去电，去电接通  但是没法区分
                    break;
                default:
                    break;
            }
        }
    }
}
