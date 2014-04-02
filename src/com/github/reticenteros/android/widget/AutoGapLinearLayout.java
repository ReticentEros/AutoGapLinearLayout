package com.github.reticenteros.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import com.github.reticenteros.android.R;

public class AutoGapLinearLayout extends LinearLayout {
    /* Constants */
    private static final int LOG_PRIORITY = Log.VERBOSE;
    private static final String LOG_TAG = "widget.AutoGapLinearLayout";

    /* Public Constructors */
    public AutoGapLinearLayout(Context context) {
        super(context);
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "AutoGapLinearLayout(Context)");
        setFirstGap(1);
    }

    public AutoGapLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "AutoGapLinearLayout(Context, AttributeSet)");
    }

    public AutoGapLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "AutoGapLinearLayout(Context, AttributeSet, int)");

        TypedArray a = context.obtainStyledAttributes(
                attrs,
                R.styleable.AutoGapLinearLayout,
                defStyle,
                0);

        try {
            setFirstGap(a.getFloat(R.styleable.AutoGapLinearLayout_firstGap, 1));
        } finally {
            a.recycle();
        }
    }

    /* Inherited Methods */
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "addView(View, int, ViewGroup.LayoutParams)");

        if (index >= 0 && index % 2 == 0) {
            if (LOG_PRIORITY <= Log.WARN) {
                Log.w(LOG_TAG, "addView(View, int, ViewGroup.LayoutParams) does nothing since indicated a gap index");
            }

            return;
        } else {
            super.addView(child, index, params);

            Space gap = generateGap();
            gap.setTag(child);

            if (index != -1) index++;

            float weight = 1;
            if (checkLayoutParams(params)) weight = ((LayoutParams) params).gap;

            super.addView(gap, index, generateGapLayoutParams(weight));
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "generateLayoutParams(AttributeSet)");
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    public void removeAllViews() {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "removeAllViews()");
        super.removeAllViews();
        setFirstGap(1);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "checkLayoutParams(ViewGroup.LayoutParams)");
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "generateDefaultLayoutParams()");
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "generateLayoutParams(ViewGroup.LayoutParams)");
        return new LayoutParams(p);
    }

    /* Public Methods */
    public void removeAllViews(float firstGapWeight) {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "removeAllViews(float)");
        super.removeAllViews();
        setFirstGap(firstGapWeight);
    }

    public void setFirstGap(float weight) {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "setFirstGap(float)");

        View firstGap = getChildAt(0);
        LayoutParams params = generateGapLayoutParams(weight);

        if (firstGap == null) super.addView(generateGap(), 0, params);
        else firstGap.setLayoutParams(params);
    }

    /* Private Methods */
    private Space generateGap() {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "generateGap()");
        return new Space(getContext());
    }

    private LayoutParams generateGapLayoutParams(float weight) {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "generateGapLayoutParams(float)");

        int orientation = getOrientation();

        if (orientation == HORIZONTAL) return new LayoutParams(0, LayoutParams.MATCH_PARENT, weight);
        else if (orientation == VERTICAL) return new LayoutParams(LayoutParams.MATCH_PARENT, 0, weight);

        return null;
    }

    /* Nested Classes */
    public static class LayoutParams extends LinearLayout.LayoutParams {
        /* Constants */
        private static final String LOG_TAG = "widget.AutoGapLinearLayout.LayoutParams";

        /* Fields */
        public float gap;

        /* Public Constructors */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "LayoutParams(Context, AttributeSet)");

            TypedArray a = c.obtainStyledAttributes(
                    attrs,
                    R.styleable.AutoGapLinearLayout_LayoutParams);

            try {
                gap = a.getFloat(R.styleable.AutoGapLinearLayout_LayoutParams_layout_gap, 1);
            } finally {
                a.recycle();
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "LayoutParams(int, int)");
            gap = 1;
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height, weight);
            if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "LayoutParams(int, int, float)");
            gap = 1;
        }

        public LayoutParams(int width, int height, float weight, float gap) {
            super(width, height, weight);
            if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "LayoutParams(int, int, float, float)");
            this.gap = gap;
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "LayoutParams(ViewGroup.LayoutParams)");
            gap = 1;
        }
    }
}
