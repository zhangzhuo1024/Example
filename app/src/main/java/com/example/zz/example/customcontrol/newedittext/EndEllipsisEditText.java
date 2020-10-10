package com.example.zz.example.customcontrol.newedittext;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import com.example.zz.example.R;

/**
 * Created by zz
 */

public class EndEllipsisEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {

    private String mRealStr;
    private String mFakeStr;
    private String mStartString;
    private boolean mIsFirstSet = true;
    private boolean mIsFocusChangeText = false;
    private boolean mIsTextRight = false;

    public EndEllipsisEditText(Context context) {
        super(context);
        init(null);
    }

    public EndEllipsisEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EndEllipsisEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EllipsisEdit);
            mIsTextRight = typedArray.getBoolean(com.example.customcontrollib.R.styleable.EllipsisEdit_text_right_gravity, false);
            typedArray.recycle();
        }
        this.setOnFocusChangeListener(this);
        this.addTextChangedListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setTheEditText(hasFocus);
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mStartString = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mIsTextRight) {
            updateGravityAndSelection(s);
        }
    }

    private void updateGravityAndSelection(Editable s) {
        String endString = s.toString();
        int newStringLength = getCharacterWidth(endString, getTextSize());
        int oldStringLength = getCharacterWidth(mStartString, getTextSize());
        int usefulWidth = getWidth() - getCharacterWidth("AAA", getTextSize());
        if (mIsFocusChangeText) {
            mIsFocusChangeText = false;
        } else {
            if ((endString != null) && (!endString.equals(mStartString))) {
                int selectionStart = getSelectionStart();
                int selectionEnd = getSelectionEnd();
                if (oldStringLength > usefulWidth && newStringLength > usefulWidth) {
                    setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                } else if (oldStringLength < usefulWidth && newStringLength < usefulWidth) {
                    setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                } else if (oldStringLength > usefulWidth && newStringLength < usefulWidth) {
                    setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                } else {
                    setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                }
                setSelection(selectionStart, selectionEnd);
            }
        }
    }

    private int getCharacterWidth(String text, float size) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(size);
        int text_width = (int) paint.measureText(text);
        return text_width;
    }

    private void setTheEditText(boolean hasFocus) {
        mFakeStr = getText() != null ? getText().toString() : "";
        if (hasFocus) {
            setGravityAndSelection();
            setText(mRealStr);
        } else {
            mRealStr = getText() != null ? getText().toString() : "";
            if (getCharacterWidth(mRealStr, getTextSize()) > getWidth()) {
                mFakeStr = getEllipsisStr(mRealStr);
            } else {
                mFakeStr = mRealStr;
            }
            setGravityAndSelection();
            setText(mFakeStr);
        }
        mIsFocusChangeText = true;
    }

    private void setGravityAndSelection() {
        if (mIsTextRight) {
            int selectionStart = getSelectionStart();
            int selectionEnd = getSelectionEnd();
            if (getCharacterWidth(mRealStr, getTextSize()) > getWidth()) {
                setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            } else {
                setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
            }
            setSelection(selectionStart, selectionEnd);
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

    public void setTextRightDisplay(boolean isTextRight) {
        this.mIsTextRight = isTextRight;
    }

    public String getRealString() {
        return mRealStr;
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
