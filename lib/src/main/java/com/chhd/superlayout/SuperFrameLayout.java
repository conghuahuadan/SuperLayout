package com.chhd.superlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.chhd.superlayout.util.SuperHelper;

public class SuperFrameLayout extends FrameLayout {

    private SuperHelper mHelper;

    public SuperFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public SuperFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    protected void dispatchDraw(Canvas canvas) {
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
}
