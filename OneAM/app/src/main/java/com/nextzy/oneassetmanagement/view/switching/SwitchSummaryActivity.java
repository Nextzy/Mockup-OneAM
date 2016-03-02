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

package com.nextzy.oneassetmanagement.view.switching;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.common.BaseToolbarActivity;
import com.nextzy.oneassetmanagement.network.NextzyService;
import com.nextzy.oneassetmanagement.network.model.FundProduct;
import com.nextzy.oneassetmanagement.network.model.PurchasedFund;
import com.nextzy.oneassetmanagement.view.TransactionSuccessfulActivity;

import org.parceler.Parcels;

public class SwitchSummaryActivity extends BaseToolbarActivity implements View.OnClickListener, NextzyService.SwitchCallback {
    private static final String KEY_SWITCH_OUT_FUND = "SwitchOutFund";
    private static final String KEY_SWITCH_OUT_VOLUME = "SwitchOutVolume";
    private static final String KEY_SWITCH_IN_FUND = "SwitchInFund";
    private static final String KEY_SWITCH_IN_VOLUME = "SwitchInVolume";

    private TextView tvSwitchOutSymbol;
    private TextView tvSwitchOutName;
    private TextView tvSwitchOutVolume;
    private TextView tvSwitchInSymbol;
    private TextView tvSwitchInName;
    private TextView tvSwitchInVolume;
    private Button btnSwitch;

    private PurchasedFund switchOutFund;
    private FundProduct switchInFund;
    private float switchOutVolume;
    private float switchInVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_summary);

        checkBundleFromIntent();
        initToolbar();
        prepareView();
        serveData();
    }

    public void prepareView() {
        tvSwitchOutSymbol = (TextView) findViewById(R.id.switch_summary_tv_switch_out_symbol);
        tvSwitchOutName = (TextView) findViewById(R.id.switch_summary_tv_switch_out_name);
        tvSwitchInSymbol = (TextView) findViewById(R.id.switch_summary_tv_switch_in_symbol);
        tvSwitchInName = (TextView) findViewById(R.id.switch_summary_tv_switch_in_name);
        tvSwitchOutVolume = (TextView) findViewById(R.id.switch_summary_tv_switch_out_volume);
        tvSwitchInVolume = (TextView) findViewById(R.id.switch_summary_tv_switch_in_volume);
        btnSwitch = (Button) findViewById(R.id.switch_summary_btn_switch);
    }

    public void serveData() {
        setToolbarTitle(getString(R.string.title_switch));
        setBackNavigation();
        btnSwitch.setOnClickListener(this);
        updateSwitchOutFund(switchOutFund);
        updateSwitchOutVolume(switchOutVolume);
        updateSwitchInFund(switchInFund);
        updateSwitchInVolume(switchInVolume);
    }

    private void checkBundleFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            switchOutFund = Parcels.unwrap(bundle.getParcelable(KEY_SWITCH_OUT_FUND));
            switchOutVolume = bundle.getFloat(KEY_SWITCH_OUT_VOLUME);
            switchInFund = Parcels.unwrap(bundle.getParcelable(KEY_SWITCH_IN_FUND));
            switchInVolume = bundle.getFloat(KEY_SWITCH_IN_VOLUME);
        }
    }

    public void updateSwitchOutFund(PurchasedFund switchOutFund) {
        if(switchOutFund != null) {
            tvSwitchOutSymbol.setText(switchOutFund.getSymbol());
            tvSwitchOutName.setText(switchOutFund.getName());
        }
    }

    public void updateSwitchInFund(FundProduct switchInFund) {
        if(switchInFund != null) {
            tvSwitchInSymbol.setText(switchInFund.getSymbol());
            tvSwitchInName.setText(switchInFund.getName());
        }
    }

    public void updateSwitchOutVolume(float switchOutVolume) {
        tvSwitchOutVolume.setText((int) switchOutVolume + "");
    }

    public void updateSwitchInVolume(float switchInVolume) {
        tvSwitchInVolume.setText((int) switchInVolume + "");
    }

    @Override
    public void onClick(View v) {
        if(v == btnSwitch) {
            switchIt();
        }
    }

    public void switchIt() {
        showLoadingDialog();
        NextzyService.switching(this);
    }

    @Override
    public void onSwitchSuccess() {
        dismissDialog();
        goToTransactionSuccessful();
    }

    public void goToTransactionSuccessful() {
        openActivity(TransactionSuccessfulActivity.class);
    }

    @Override
    public void onNavigationClick() {
        onBackPressed();
    }
}
