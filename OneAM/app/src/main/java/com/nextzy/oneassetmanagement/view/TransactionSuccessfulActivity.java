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

package com.nextzy.oneassetmanagement.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.common.BaseToolbarActivity;
import com.nextzy.oneassetmanagement.view.dashboard.DashboardActivity;

public class TransactionSuccessfulActivity extends BaseToolbarActivity implements View.OnClickListener {
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_successful);

        initToolbar();
        prepareView();
        serveData();
    }

    public void prepareView() {
        btnConfirm = (Button) findViewById(R.id.transaction_successful_btn_confirm);
    }

    public void serveData() {
        setToolbarTitle(getString(R.string.title_transaction_successful));
        hideMenuNavigation();
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnConfirm) {
            openActivityAndClearHistory(DashboardActivity.class);
        }
    }
}
