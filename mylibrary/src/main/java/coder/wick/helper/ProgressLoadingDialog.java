package coder.wick.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import coder.mylibrary.R;


public class ProgressLoadingDialog extends AlertDialog {

    private String mMessage;
    private TextView mMessageText;
    private boolean mIndeterminate;
    private boolean mProgressVisiable;
    private ProgressBar mProgress;

    public ProgressLoadingDialog(Context context) {
        super(context, R.style.selectorDialogSS);
    }

    public ProgressLoadingDialog(Context context, String message) {
        super(context, R.style.selectorDialogSS);
        this.mMessage = message;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loading_dialog);
        setCanceledOnTouchOutside(false);
        mMessageText = (TextView) findViewById(R.id.tv_loading);
        mProgress = (ProgressBar) findViewById(android.R.id.progress);
    }

    private void setMessageAndView() {
        if (!TextUtils.isEmpty(mMessage)) {
            mMessageText.setText(mMessage);
        }
        mProgress.setVisibility(mProgressVisiable ? View.VISIBLE : View.GONE);
    }

    public void setProgressVisiable(boolean progressVisiable) {
        mProgressVisiable = progressVisiable;
    }

    public void setIndeterminate(boolean indeterminate) {
        if (mProgress != null) {
            mProgress.setIndeterminate(indeterminate);
        } else {
            mIndeterminate = indeterminate;
        }
    }
}
