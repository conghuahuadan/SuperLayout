package com.chhd.superlayout.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.chhd.superlayout.R;

/**
 * Super布局帮助类
 *
 * @author 陈伟强 (2019/6/26)
 */
public class SuperHelper {

    public View mView; // 被关联的控件

    public float[] mRadii = new float[8];           // top-left, top-right, bottom-right, bottom-left
    public boolean mRoundAsCircle = false;          // 圆形
    public ColorStateList mStrokeColorStateList;    // 描边颜色的状态
    public int mStrokeColor;                        // 描边颜色
    public int mStrokeWidth;                        // 描边半径
    public ColorStateList mFillBackgroundColorStateList;            // 填充颜色的状态
    public int mFillBackgroundColor;                // 填充颜色
    public boolean mClipBackground;                 // 是否剪裁背景

    public Paint mPaint; // 画笔
    public Path mClipPath; // 剪裁区域路径
    public RectF mLayer; // 画布图层区域

    public SuperHelper(View view) {
        this.mView = view;
    }

    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SuperAttrs);

        mRoundAsCircle = ta.getBoolean(R.styleable.SuperAttrs_round_as_circle, false);

        int roundCorner = ta.getDimensionPixelSize(R.styleable.SuperAttrs_round_corner, 0);
        int roundCornerTopLeft = ta.getDimensionPixelSize(
                R.styleable.SuperAttrs_round_corner_top_left, roundCorner);
        int roundCornerTopRight = ta.getDimensionPixelSize(
                R.styleable.SuperAttrs_round_corner_top_right, roundCorner);
        int roundCornerBottomLeft = ta.getDimensionPixelSize(
                R.styleable.SuperAttrs_round_corner_bottom_left, roundCorner);
        int roundCornerBottomRight = ta.getDimensionPixelSize(
                R.styleable.SuperAttrs_round_corner_bottom_right, roundCorner);

        mStrokeColorStateList = ta.getColorStateList(R.styleable.SuperAttrs_stroke_color);
        if (null != mStrokeColorStateList) {
            mStrokeColor = mStrokeColorStateList.getDefaultColor();
        } else {
            mStrokeColor = Color.WHITE;
        }

        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.SuperAttrs_stroke_width, 0);

        mFillBackgroundColorStateList = ta.getColorStateList(R.styleable.SuperAttrs_fill_background_color);
        if (null != mFillBackgroundColorStateList) {
            mFillBackgroundColor = mFillBackgroundColorStateList.getDefaultColor();
        } else {
            mFillBackgroundColor = -1;
        }

        mClipBackground = ta.getBoolean(R.styleable.SuperAttrs_clip_background, false);

        ta.recycle();

        mRadii[0] = roundCornerTopLeft;
        mRadii[1] = roundCornerTopLeft;
        mRadii[2] = roundCornerTopRight;
        mRadii[3] = roundCornerTopRight;
        mRadii[4] = roundCornerBottomRight;
        mRadii[5] = roundCornerBottomRight;
        mRadii[6] = roundCornerBottomLeft;
        mRadii[7] = roundCornerBottomLeft;

        mLayer = new RectF();
        mClipPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
    }

    public void onSizeChanged(int w, int h) {
        mLayer.set(0, 0, w, h);
        refreshRegion();
    }

    private void refreshRegion() {
        int w = (int) mLayer.width();
        int h = (int) mLayer.height();
        RectF areas = new RectF();
        areas.left = mView.getPaddingLeft();
        areas.top = mView.getPaddingTop();
        areas.right = w - mView.getPaddingRight();
        areas.bottom = h - mView.getPaddingBottom();
        mClipPath.reset();
        if (mRoundAsCircle) {
            float d = areas.width() >= areas.height() ? areas.height() : areas.width(); // 圆的直径
            float r = d / 2; // 圆的半径
            PointF center = new PointF(w / 2, h / 2);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
                // CW：顺时针，CCW：逆时针
                mClipPath.addCircle(center.x, center.y, r, Path.Direction.CW);

                mClipPath.moveTo(0, 0);  // 通过空操作让Path区域占满画布
                mClipPath.moveTo(w, h);
            } else {
                mClipPath.addCircle(center.x, center.y, r, Path.Direction.CW);
            }
        } else {
            mClipPath.addRoundRect(areas, mRadii, Path.Direction.CW);
        }
    }

    /* PorterDuff.Mode 参考“https://www.jianshu.com/p/d11892bbe055” */

    public void onClipDraw(Canvas canvas) {
        /* 处理填充 */
        if (mFillBackgroundColor != -1) {
            Path path = new Path();
            path.addRect(mLayer, Path.Direction.CW);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mFillBackgroundColor);
            canvas.drawPath(path, mPaint);
        }

        /* 处理描边 */
        if (mStrokeWidth > 0) {
            // 支持半透明描边，将与描边区域重叠的内容裁剪掉
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mPaint.setColor(Color.WHITE);
            mPaint.setStrokeWidth(mStrokeWidth * 2);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mClipPath, mPaint);
            // 绘制描边
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            mPaint.setColor(mStrokeColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mClipPath, mPaint);
        }

        /* 处理圆角 */
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN)); // 保留相交的区域
            canvas.drawPath(mClipPath, mPaint);
        } else {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            Path path = new Path();
            path.addRect(0, 0, (int) mLayer.width(), (int) mLayer.height(), Path.Direction.CW);
            path.op(mClipPath, Path.Op.DIFFERENCE);
            canvas.drawPath(path, mPaint);
        }
    }
}
