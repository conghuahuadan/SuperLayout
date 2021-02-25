package com.chhd.superlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.chhd.superlayout.util.SuperHelper;

/**
 * 作者：陈伟强
 * 日期：2021-02-25, 星期四
 * 描述：
 */
public class SuperEditText extends AppCompatEditText {

    private static final String TAG = SuperTextView.class.getSimpleName();

    private SuperHelper mHelper;

    public SuperEditText(Context context) {
        super(context);
        init(context, null);
    }

    public SuperEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SuperEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
}
