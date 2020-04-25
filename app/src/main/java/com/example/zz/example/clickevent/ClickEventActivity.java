package com.example.zz.example.clickevent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zz.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
事件分发机制测试，csdn中写了详细结果
 */
public class ClickEventActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "ClickEventActivity";
    @BindView(R.id.button_click)
    Button mButtonClick;
    @BindView(R.id.imageview_click)
    ImageView mImageviewClick;
//    private View mButtonClick;
//    private View mImageViewClick;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "activity onTouch = " + event);
        return false;
    }


    /**
     * 在Activity的触摸屏事件派发中：
     *
     * 首先会触发Activity的dispatchTouchEvent方法。
     * dispatchTouchEvent方法中如果是ACTION_DOWN的情况下会接着触发onUserInteraction方法。
     * 接着在dispatchTouchEvent方法中会通过Activity的root View（id为content的FrameLayout），实质是ViewGroup，通过super.dispatchTouchEvent把touchevent派发给各个activity的子view，也就是我们再Activity.onCreat方法中setContentView时设置的view。
     * 若Activity下面的子view拦截了touchevent事件(返回true)则Activity.onTouchEvent方法就不会执行。
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "activity dispatchTouchEvent = " + ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "activity onTouchEvent = " + event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_event);
        ButterKnife.bind(this);

        View contentView = findViewById(R.id.contentView);
        View myRelativeLayout = findViewById(R.id.MyRelativeLayout);
        View myImageView = findViewById(R.id.MyImageView);

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "contentView onTouch = " + event);
                return false;
            }
        });

//        activity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "activity onClick = " + v);
//            }
//        });

        myRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "MyRelativeLayout onTouch = " + event);
                return false;
            }
        });
//        myRelativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "MyRelativeLayout onClick = " + v);
//            }
//        });

        myImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "MyImageView onTouch = " + v);
                return false;
            }
        });
//        myImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "MyImageView onClick = " + v);
//            }
//        });

















//        mButtonClick = findViewById(R.id.button_click);
//        mImageViewClick = findViewById(R.id.imageview_click);
//        mButtonClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i(TAG, "mButtonClick");
//            }
//        });
//        mButtonClick.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i(TAG, "mButtonTouch ACTION_DOWN");
//                        return false;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.i(TAG, "mButtonTouch ACTION_MOVE");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.i(TAG, "mButtononTouch ACTION_UP");
//                        break;
//                }
//                return false;
//            }
//        });
//        mImageviewClick.setClickable(false);
//        mImageviewClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i(TAG, "mImageViewClick");
//            }
//        });
//        mImageviewClick.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i(TAG, "mImageViewonTouch ACTION_DOWN");
//                        return false;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.i(TAG, "mImageViewonTouch ACTION_MOVE");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.i(TAG, "mImageViewonTouch ACTION_UP");
//                        break;
//                }
//                return false;
//            }
//        });
    }


}
