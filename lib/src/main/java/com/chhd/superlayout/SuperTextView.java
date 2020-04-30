package com.chhd.superlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.chhd.superlayout.util.AutoFitHelper;
import com.chhd.superlayout.util.SuperHelper;

/**
 * SuperTextView
 *
 * @author 陈伟强 (2019/6/26)
 */
public class SuperTextView extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = SuperTextView.class.getSimpleName();

    private SuperHelper mHelper;
    private AutoFitHelper mAutoFitHelper;

    public SuperTextView(Context context) {
        super(context);
        init(context, null);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mHelper = new SuperHelper(this);
        mHelper.initAttrs(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SuperTextView);
        boolean textSizeAutoFix =
                ta.getBoolean(R.styleable.SuperTextView_text_size_auto_fix, false);
        mAutoFitHelper = AutoFitHelper.create(this, attrs);
        mAutoFitHelper.setEnabled(textSizeAutoFix);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHelper.onSizeChanged(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(mHelper.mLayer, null, Canvas.ALL_SAVE_FLAG);
        mHelper.onClipDraw(canvas);
        canvas.restore();
        super.onDraw(canvas);
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

    public AutoFitHelper getAutoFitHelper() {
        return mAutoFitHelper;
    }

    public void setStrokeColor(int color) {
        mHelper.setStrokeColor(color);
    }

    public void setFillBackgroundColor(int color) {
        mHelper.setFillBackgroundColor(color);
    }
}
