package com.chhd.superlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.chhd.superlayout.util.SuperHelper;

/**
 * SuperImageView
 *
 * @author 陈伟强 (2019/6/26)
 */
public class SuperImageView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = SuperImageView.class.getSimpleName();

    private SuperHelper mHelper;

    public SuperImageView(Context context) {
        super(context);
        init(context, null);
    }

    public SuperImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SuperImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(mHelper.mLayer, null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
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
