package com.chhd.superlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

import com.chhd.superlayout.util.SuperHelper;

/**
 * SuperConstraintLayout
 *
 * @author 陈伟强 (2019/8/8)
 */
public class SuperConstraintLayout extends ConstraintLayout {

    private SuperHelper mHelper;

    public SuperConstraintLayout(Context context) {
        super(context);
        init(context, null);
    }

    public SuperConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SuperConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
