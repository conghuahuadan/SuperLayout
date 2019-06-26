package com.chhd.superlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class SuperTextView extends android.support.v7.widget.AppCompatTextView {

    private SuperHelper mHelper;

    private AutoFitHelper autofitHelper;

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
        autofitHelper = AutoFitHelper.create(this, attrs);
        autofitHelper.setEnabled(textSizeAutoFix);
        ta.recycle();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        mHelper.onSizeChanged(width, height);
    }

    @Override
    public void draw(Canvas canvas) {
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
