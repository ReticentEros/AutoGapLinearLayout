package com.github.reticenteros.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class AutoGapLinearLayout extends LinearLayout {
    /* Constants */
    private static final int LOG_PRIORITY = Log.VERBOSE;
    private static final String LOG_TAG = "widget.AutoGapLinearLayout";

    /* Public Constructors */
    public AutoGapLinearLayout(Context context) {
        super(context);
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "AutoGapLinearLayout(Context)");
    }

    public AutoGapLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "AutoGapLinearLayout(Context, AttributeSet)");
    }

    public AutoGapLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (LOG_PRIORITY <= Log.VERBOSE) Log.v(LOG_TAG, "AutoGapLinearLayout(Context, AttributeSet, int)");
    }
}
