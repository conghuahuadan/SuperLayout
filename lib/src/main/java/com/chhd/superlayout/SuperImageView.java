package com.chhd.superlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class SuperImageView extends android.support.v7.widget.AppCompatImageView {

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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHelper.onSizeChanged(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mHelper.mClipBackground) {
            canvas.save();
            canvas.clipPath(mHelper.mClipPath);
            super.draw(canvas);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(mHelper.mLayer, null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        mHelper.onClipDraw(canvas);
        canvas.restore();
    }
}
