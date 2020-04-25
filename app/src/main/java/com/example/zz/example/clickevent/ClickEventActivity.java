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

public class ClickEventActivity extends AppCompatActivity {

    private static final String TAG = "ClickEventActivity";
    @BindView(R.id.button_click)
    Button mButtonClick;
    @BindView(R.id.imageview_click)
    ImageView mImageviewClick;
//    private View mButtonClick;
//    private View mImageViewClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_event);
        ButterKnife.bind(this);

        View activity = findViewById(R.id.contentView);
        View myRelativeLayout = findViewById(R.id.MyRelativeLayout);
        View myButton = findViewById(R.id.MyButton);

        activity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "activity onTouch = " + event);
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

        myButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "MyButton onTouch = " + v);
                return false;
            }
        });
//        myButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "MyButton onClick = " + v);
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
