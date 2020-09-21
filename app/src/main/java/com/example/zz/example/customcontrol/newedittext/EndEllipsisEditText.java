package com.example.zz.example.customcontrol.newedittext;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * Created by zz
 */

public class EndEllipsisEditText extends EditText implements View.OnFocusChangeListener {

    private String mOldStr;

    public EndEllipsisEditText(Context context) {
        this(context, null);
    }

    public EndEllipsisEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EndEllipsisEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOnFocusChangeListener(this);
        mOldStr = getText() != null ? getText().toString() : "";
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setText(mOldStr);
        } else {
            mOldStr = getText() != null ? getText().toString() : "";
            if (getCharacterWidth(mOldStr, getTextSize()) > getWidth()) {
                setText(getEllipsisStr(mOldStr));
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

    private String getEllipsisStr(String text) {
        String total = "";
        for (int i = 0; i < text.length(); i++) {
            total = text.substring(0, i);
            if (getCharacterWidth(total, getTextSize()) > getWidth()) {
                break;
            }
        }
        total = total.substring(0, total.length() - 3) + "...";
        return total;
    }
}
