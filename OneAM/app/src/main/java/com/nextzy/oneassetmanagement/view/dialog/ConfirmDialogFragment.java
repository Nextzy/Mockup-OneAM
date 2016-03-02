/*
Copyright 2016 Nextzy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.nextzy.oneassetmanagement.view.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.nextzy.oneassetmanagement.R;

/**
 * Created by Akexorcist on 8/3/15 AD.
 */
public class ConfirmDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final int TYPE_CONFIRM = 0;
    public static final int TYPE_CANCEL = 1;

    private static final String KEY_TITLE = "key_title";
    private static final String KEY_MESSAGE = "key_message";

    private OnDialogClickListener onConfirmListener;
    private OnDialogClickListener onCancelListener;
    private OnDismissListener onDismissListener;

    private Button btnConfirm;
    private Button btnCancel;

    public static ConfirmDialogFragment newInstance(String title, String message) {
        ConfirmDialogFragment fragment = new ConfirmDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_MESSAGE, message);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);

        Bundle bundle = getArguments();
        String title = bundle.getString(KEY_TITLE);
        String message = bundle.getString(KEY_MESSAGE);

        View view = inflater.inflate(R.layout.dialog_confirm, container);

        TextView tvDialogMessage = (TextView) view.findViewById(R.id.dialog_confirm_tv_message);
        tvDialogMessage.setText(message);

        TextView tvTitle = (TextView) view.findViewById(R.id.dialog_confirm_tv_title);
        tvTitle.setText(title);

        btnConfirm = (Button) view.findViewById(R.id.dialog_confirm_btn_confirm);
        btnConfirm.setOnClickListener(this);

        btnCancel = (Button) view.findViewById(R.id.dialog_confirm_btn_cancel);
        btnCancel.setOnClickListener(this);

        return view;
    }

    public void setOnConfirmListener(OnDialogClickListener listener) {
        onConfirmListener = listener;
    }

    public void setOnCancelListener(OnDialogClickListener listener) {
        onCancelListener = listener;
    }

    public void setOnDismissListener(OnDismissListener listener) {
        onDismissListener = listener;
    }

    public void performPositiveButtonClick() {
        if (btnConfirm != null)
            btnConfirm.performClick();
    }

    public void performNegativeButtonClick() {
        if (btnCancel != null)
            btnCancel.performClick();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialog_confirm_btn_confirm && onConfirmListener != null) {
            onConfirmListener.onDialogClick(this, TYPE_CONFIRM);
        } else if (id == R.id.dialog_confirm_btn_cancel && onCancelListener != null) {
            onCancelListener.onDialogClick(this, TYPE_CANCEL);
        }
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null)
            onDismissListener.onDismiss(this);
    }

    public interface OnDialogClickListener {
        void onDialogClick(ConfirmDialogFragment dialog, int which);
    }

    public interface OnDismissListener {
        void onDismiss(ConfirmDialogFragment dialog);
    }

    public static class Builder {
        private OnDialogClickListener onConfirmListener;
        private OnDialogClickListener onCancelListener;
        private OnDismissListener onDismissListener;

        private String message;
        private String title;

        public Builder() {
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setOnConfirmListener(OnDialogClickListener listener) {
            onConfirmListener = listener;
            return this;
        }

        public Builder setOnCancelListener(OnDialogClickListener listener) {
            onCancelListener = listener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener listener) {
            onDismissListener = listener;
            return this;
        }

        public ConfirmDialogFragment build() {
            ConfirmDialogFragment fragment = ConfirmDialogFragment.newInstance(title, message);
            fragment.setOnConfirmListener(onConfirmListener);
            fragment.setOnCancelListener(onCancelListener);
            fragment.setOnDismissListener(onDismissListener);
            return fragment;
        }
    }
}
