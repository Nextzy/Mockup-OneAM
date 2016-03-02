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

package com.nextzy.oneassetmanagement.view.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.github.ppamorim.dragger.DraggerActivity;
import com.github.ppamorim.dragger.DraggerPosition;
import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.view.alert.AlertActivity;
import com.nextzy.oneassetmanagement.view.dashboard.DashboardActivity;
import com.nextzy.oneassetmanagement.view.report.ReportActivity;


/**
 * Created by Akexorcist on 1/19/2016 AD.
 */
public class MenuActivity extends DraggerActivity implements View.OnClickListener {
    private ImageButton btnBack;
    private LinearLayout layoutDashboard;
    private LinearLayout layoutReport;
    private LinearLayout layoutAlert;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        setDraggerPosition(DraggerPosition.LEFT);

        prepareView();
        serveData();
    }

    public void prepareView() {
        btnBack = (ImageButton) findViewById(R.id.menu_btn_back);
        layoutDashboard = (LinearLayout) findViewById(R.id.menu_layout_dashboard);
        layoutReport = (LinearLayout) findViewById(R.id.menu_layout_report);
        layoutAlert = (LinearLayout) findViewById(R.id.menu_layout_alert);
    }

    public void serveData() {
        btnBack.setOnClickListener(this);
        layoutDashboard.setOnClickListener(this);
        layoutReport.setOnClickListener(this);
        layoutAlert.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnBack) {
            closeActivity();
        } else if (v == layoutDashboard) {
            openActivityAndClearHistory(DashboardActivity.class);
        } else if (v == layoutReport) {
            openActivityAndClearHistory(ReportActivity.class);
        } else if (v == layoutAlert) {
            openActivityAndClearHistory(AlertActivity.class);
        }
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
}
