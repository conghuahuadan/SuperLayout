package com.chhd.superlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.chhd.superlayout.util.SuperHelper;

/**
 * SuperLinearLayout
 *
 * 陈伟强 (2020/4/30)
 */
public class SuperLinearLayout extends LinearLayout {

    private SuperHelper mHelper;

    public SuperLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public SuperLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SuperLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mHelper = new SuperHelper(this);
        mHelper.initAttrs(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHelper.onSizeChanged(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(mHelper.mLayer, null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        mHelper.onClipDraw(canvas);
        canvas.restore();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mHelper.mClipBackground) {
            canvas.saveLayer(mHelper.mLayer, null, Canvas.ALL_SAVE_FLAG);
            super.draw(canvas);
            mHelper.handleCorner(canvas);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }

    public SuperHelper getSuperHelper() {
        return mHelper;
    }

    public void setStrokeColor(int color) {
        mHelper.setStrokeColor(color);
    }

    public void setFillBackgroundColor(int color) {
        mHelper.setFillBackgroundColor(color);
    }
}
