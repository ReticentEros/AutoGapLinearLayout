package com.github.reticenteros.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
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
    public void removeAllViews() {
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "removeAllViews()");
        super.removeAllViews();
        setFirstGap(1);
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
}
