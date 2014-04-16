package com.github.reticenteros.android.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.WindowManager;
import android.widget.EditText;

import java.io.Serializable;

public class InputDialogFragment extends DialogFragment {
    private CharSequence mTitle;
    private CharSequence mHint;
    private OnConfirmListener mOnConfirmListener;
    private AlertDialog.Builder mAlertDialogBuilder;
    private EditText mContentView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mTitle = args.getCharSequence("title");
        mHint = args.getCharSequence("hint");
        mOnConfirmListener = (OnConfirmListener) args.getSerializable("listener");
        mAlertDialogBuilder = new AlertDialog.Builder(getActivity());
        mContentView = new EditText(mAlertDialogBuilder.getContext());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContentView.setHint(mHint);

        mAlertDialogBuilder
                .setTitle(mTitle)
                .setView(mContentView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mOnConfirmListener.onConfirm(mContentView.getText());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return mAlertDialogBuilder.create();
    }

    public static InputDialogFragment newInstance(CharSequence title, CharSequence hint, OnConfirmListener listener) {
        InputDialogFragment f = new InputDialogFragment();

        Bundle args = new Bundle();
        args.putCharSequence("title", title);
        args.putCharSequence("hint", hint);
        args.putSerializable("listener", listener);

        f.setArguments(args);

        return f;
    }

    public static interface OnConfirmListener extends Serializable {
        public void onConfirm(Editable text);
    }
}
