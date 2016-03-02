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

package com.nextzy.oneassetmanagement.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import com.nextzy.oneassetmanagement.view.dialog.ConfirmDialogFragment;
import com.nextzy.oneassetmanagement.view.dialog.LoadingDialogFragment;

/**
 * Created by Akexorcist on 1/7/2016 AD.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG_DIALOG_FRAGMENT = "dialog_fragment";

    private ConfirmDialogFragment confirmDialogFragment;
    private LoadingDialogFragment loadingDialogFragment;

    protected void openActivity(Class<?> cls) {
        openActivity(cls, null, false);
    }

    protected void openActivity(Class<?> cls, boolean finishActivity) {
        openActivity(cls, null, finishActivity);
    }

    protected void openActivity(Class<?> cls, Bundle bundle) {
        openActivity(cls, bundle, false);
    }

    protected void openActivity(Class<?> cls, Bundle bundle, boolean finishActivity) {
        Intent intent = new Intent(this, cls);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
        if (finishActivity)
            finish();
    }

    protected void openActivityAndClearHistory(Class<?> cls) {
        openActivityAndClearHistory(cls, null);
    }

    protected void openActivityAndClearHistory(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showConfirmDialog(int title, int message, ConfirmDialogFragment.OnDialogClickListener listener) {
        showConfirmDialog(getString(title), getString(message), listener);
    }

    public void showConfirmDialog(String title, String message, ConfirmDialogFragment.OnDialogClickListener listener) {
        ConfirmDialogFragment alertDialogFragment = new ConfirmDialogFragment.Builder()
                .setMessage(message)
                .setTitle(title)
                .setOnConfirmListener(listener)
                .setOnCancelListener(listener)
                .build();
        createFragmentDialog(alertDialogFragment);
    }

    private void createFragmentDialog(DialogFragment dialogFragment) {
        try {
            dialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_FRAGMENT);
        } catch (IllegalStateException e) {
        }
    }

    public void showLoadingDialog() {
        dismissDialog();
        loadingDialogFragment = new LoadingDialogFragment.Builder().build();
        createFragmentDialog(loadingDialogFragment);
    }

    public void dismissDialog() {
        try {
            if (confirmDialogFragment != null) {
                confirmDialogFragment.dismiss();
            }
            if (loadingDialogFragment != null) {
                loadingDialogFragment.dismiss();
            }
        } catch (IllegalStateException e) {
        }
    }

    public void showMessage(int message) {
        showMessage(getString(message));
    }

    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
