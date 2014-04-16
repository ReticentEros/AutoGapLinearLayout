package com.github.reticenteros.android.sample;

import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.github.reticenteros.android.widget.AutoGapLinearLayout;

public class MainActivity extends Activity {
    private static final String DIALOG_TAG = "Dialog";

    private FragmentManager mFragmentManager;
    private AutoGapLinearLayout mMainLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mFragmentManager = getFragmentManager();
        mMainLayout = (AutoGapLinearLayout) findViewById(R.id.mainLayout);
    }

    public void modifyFirstGap(float weight) {
        mMainLayout.setFirstGap(weight);
    }

    public void modifyLastGap(float weight) {
        View lastChild = mMainLayout.getChildAt(mMainLayout.getChildCount() - 2);
        AutoGapLinearLayout.LayoutParams params =
                (AutoGapLinearLayout.LayoutParams) lastChild.getLayoutParams();

        params.gap = weight;
        lastChild.invalidate();
        lastChild.requestLayout();
    }

    public void addChild(float gapWeight) {
        AutoGapLinearLayout.LayoutParams params = new AutoGapLinearLayout.LayoutParams(
                AutoGapLinearLayout.LayoutParams.WRAP_CONTENT,
                AutoGapLinearLayout.LayoutParams.WRAP_CONTENT,
                0,
                gapWeight
        );

        ImageView child = new ImageView(this);
        child.setLayoutParams(params);
        child.setImageResource(R.drawable.ic_launcher);

        mMainLayout.addView(child);
    }

    public void changeOrientation(View view) {
        mMainLayout.setOrientation(mMainLayout.getOrientation()^1);
    }

    public void showFirstGapDialog(View view) {
        FragmentTransaction t = mFragmentManager.beginTransaction();
        Fragment f = mFragmentManager.findFragmentByTag(DIALOG_TAG);

        if (f == null) {
            t.addToBackStack(null);
            
            DialogFragment d = InputDialogFragment.newInstance(
                    "First Gap",
                    "put weight in float type",
                    new InputDialogFragment.OnConfirmListener() {
                @Override
                public void onConfirm(Editable text) {
                    modifyFirstGap(Float.valueOf(text.toString()));
                }
            });
            d.show(t, DIALOG_TAG);
        } else {
            t.remove(f);
        }
    }

    public void showLastGapDialog(View view) {
        FragmentTransaction t = mFragmentManager.beginTransaction();
        Fragment f = mFragmentManager.findFragmentByTag(DIALOG_TAG);

        if (mMainLayout.getChildCount() < 2) {
            Toast oops = Toast.makeText(this, "There's no child", Toast.LENGTH_LONG);
            oops.setGravity(Gravity.CENTER, 0, 0);
            oops.show();
            return;
        }

        if (f == null) {
            t.addToBackStack(null);

            DialogFragment d = InputDialogFragment.newInstance(
                    "Gap of Last Child",
                    "put weight in float type",
                    new InputDialogFragment.OnConfirmListener() {
                        @Override
                        public void onConfirm(Editable text) {
                            modifyLastGap(Float.valueOf(text.toString()));
                        }
                    });
            d.show(t, DIALOG_TAG);
        } else {
            t.remove(f);
        }
    }

    public void showChildAdditionDialog(View view) {
        FragmentTransaction t = mFragmentManager.beginTransaction();
        Fragment f = mFragmentManager.findFragmentByTag(DIALOG_TAG);

        if (f == null) {
            t.addToBackStack(null);

            DialogFragment d = InputDialogFragment.newInstance(
                    "Add Child",
                    "put weight in float type",
                    new InputDialogFragment.OnConfirmListener() {
                        @Override
                        public void onConfirm(Editable text) {
                            addChild(Float.valueOf(text.toString()));
                        }
                    });
            d.show(t, DIALOG_TAG);
        } else {
            t.remove(f);
        }
    }

    public void showChildRemovalDialog(View view) {
        mMainLayout.removeViewAt(mMainLayout.getChildCount() - 2);
    }
}
