package com.example.zz.example.customcontrol.newedittext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;

/**
 * Created by zz
 */

public class EndEllipsisEditText extends EditText implements View.OnFocusChangeListener {

    private String mRealStr;
    private String mFakeStr;
    private boolean mIsFirstSet = true;

    public EndEllipsisEditText(Context context) {
        super(context);
        init();
    }

    public EndEllipsisEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EndEllipsisEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        this.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setTheEditText(hasFocus);
    }

    @Override
    protected boolean getDefaultEditable() {
        return super.getDefaultEditable();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (mIsFirstSet) {
            mRealStr = getText() != null ? getText().toString() : "";
            mIsFirstSet = false;
        }
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
    }

    @Override
    public Editable getText() {
        return super.getText();
    }

    private int getCharacterWidth(String text, float size) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(size);
        int text_width = (int) paint.measureText(text);// 得到总体长度
        return text_width;
    }

    public String getRealString(){
        return mRealStr;
    }

    private void setTheEditText(boolean hasFocus) {
        mFakeStr = getText() != null ? getText().toString() : "";
        if (hasFocus) {

            setText(mRealStr);
        } else {
            mRealStr = getText() != null ? getText().toString() : "";
            if (getCharacterWidth(mRealStr, getTextSize()) > getWidth()) {
                mFakeStr = getEllipsisStr(mRealStr);
            } else {
                mFakeStr = mRealStr;
            }
            setText(mFakeStr);
        }
    }

    private String getEllipsisStr(String text) {
        String total = "";
        for (int i = 0; i < text.length(); i++) {
            total = text.substring(0, i);
            if (getCharacterWidth(total, getTextSize()) > getWidth()) {
                break;
            }
        }
        total = total.substring(0, total.length() - 2) + "...";
        return total;
    }

    public void setListener(Activity activity, OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
        SoftKeyBoardListener(activity);
    }


    private View rootView;//activity的根视图
    private int rootViewVisibleHeight;//纪录根视图的显示高度
    private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;

    public void SoftKeyBoardListener(Activity activity) {
        //获取activity的根视图
        rootView = activity.getWindow().getDecorView();

        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获取当前根视图在屏幕上显示的大小
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);

                int visibleHeight = r.height();
                System.out.println("" + visibleHeight);
                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }

                //根视图显示高度变小超过200，可以看作软键盘显示了
                if (rootViewVisibleHeight - visibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度变大超过200，可以看作软键盘隐藏了
                if (visibleHeight - rootViewVisibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

            }
        });
    }

    private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
    }

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);

        void keyBoardHide(int height);
    }
}
