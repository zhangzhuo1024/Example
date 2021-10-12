package com.zz.zzbutterknife;

import android.app.Activity;

import com.zz.zzbutterknifeannotation.IBinder;

/**
 * @author: zhuozhang6
 * @date: 2021/10/11
 * @email: zhuozhang6@iflytek.com
 * @Description:
 */
public class ZzButterKnife {

    public static void bind(Activity activity) {
        String name = activity.getClass().getName() + "_ZzBindView";
        try {
            Class clazz = Class.forName(name);
            IBinder iBinder = (IBinder) clazz.newInstance();
            iBinder.bind(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
