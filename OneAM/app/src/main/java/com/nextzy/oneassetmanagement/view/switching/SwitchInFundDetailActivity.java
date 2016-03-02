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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.common.BaseToolbarActivity;
import com.nextzy.oneassetmanagement.network.model.FundProduct;
import com.nextzy.oneassetmanagement.network.model.PurchasedFund;
import com.nextzy.oneassetmanagement.view.dashboard.DashboardActivity;

import org.parceler.Parcels;

public class SwitchInFundDetailActivity extends BaseToolbarActivity implements View.OnClickListener {
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
    private TextView tvSwitchInRiskRating;
    private TextView tvSwitchInStarRating;
    private TextView tvSwitchInNav;
    private TextView tvSwitchInCutOffTime;
    private Button btnChangeSwitchOut;
    private Button btnSwitchInDownloadFactSheet;
    private Button btnSwitchIn;

    private PurchasedFund switchOutFund;
    private FundProduct switchInFund;
    private float switchOutVolume;
    private float switchInVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_in_fund_detail);

        checkBundleFromIntent();
        initToolbar();
        prepareView();
        serveData();
    }

    public void prepareView() {
        tvSwitchOutSymbol = (TextView) findViewById(R.id.switch_in_fund_detail_tv_switch_out_symbol);
        tvSwitchOutVolume = (TextView) findViewById(R.id.switch_in_fund_detail_tv_switch_out_volume);
        tvSwitchOutName = (TextView) findViewById(R.id.switch_in_fund_detail_tv_switch_out_name);
        tvSwitchInSymbol = (TextView) findViewById(R.id.switch_in_fund_detail_tv_switch_in_symbol);
        tvSwitchInName = (TextView) findViewById(R.id.switch_in_fund_detail_tv_switch_in_name);
        tvSwitchInRiskRating = (TextView) findViewById(R.id.switch_in_fund_detail_tv_switch_in_risk_rating);
        tvSwitchInStarRating = (TextView) findViewById(R.id.switch_in_fund_detail_tv_switch_in_star_rating);
        tvSwitchInNav = (TextView) findViewById(R.id.switch_in_fund_detail_tv_switch_in_nav);
        tvSwitchInCutOffTime = (TextView) findViewById(R.id.switch_in_fund_detail_tv_switch_in_cut_off_time);
        tvSwitchInVolume = (TextView) findViewById(R.id.switch_in_fund_detail_tv_switch_in_volume);
        btnChangeSwitchOut = (Button) findViewById(R.id.switch_in_fund_detail_btn_change_switch_out);
        btnSwitchInDownloadFactSheet = (Button) findViewById(R.id.switch_in_fund_detail_btn_switch_in_download_fact_sheet);
        btnSwitchIn = (Button) findViewById(R.id.switch_in_fund_detail_btn_switch_in);
    }

    public void serveData() {
        setToolbarTitle(getString(R.string.title_switch));
        setBackNavigation();
        btnChangeSwitchOut.setOnClickListener(this);
        btnSwitchInDownloadFactSheet.setOnClickListener(this);
        btnSwitchIn.setOnClickListener(this);
        updateSwitchOutFund(switchOutFund);
        updateSwitchOutVolume(switchOutVolume);
        updateSwitchInFund(switchInFund);
        updateSwitchInVolume(switchOutVolume, switchOutFund, switchInFund);
    }

    private void checkBundleFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            switchOutFund = Parcels.unwrap(bundle.getParcelable(KEY_SWITCH_OUT_FUND));
            switchOutVolume = bundle.getFloat(KEY_SWITCH_OUT_VOLUME);
            switchInFund = Parcels.unwrap(bundle.getParcelable(KEY_SWITCH_IN_FUND));
        }
    }

    public void updateSwitchOutFund(PurchasedFund switchOutFund) {
        if(switchOutFund != null) {
            tvSwitchOutSymbol.setText(switchOutFund.getSymbol());
            tvSwitchOutName.setText(switchOutFund.getName());
        }
    }

    public void updateSwitchOutVolume(float switchOutVolume) {
        tvSwitchOutVolume.setText((int) switchOutVolume + "");
    }

    public void updateSwitchInFund(FundProduct switchInFund) {
        if(switchInFund != null) {
            tvSwitchInSymbol.setText(switchInFund.getSymbol());
            tvSwitchInName.setText(switchInFund.getName());
            tvSwitchInRiskRating.setText(switchInFund.getRiskRating());
            tvSwitchInStarRating.setText(switchInFund.getStarRating());
            tvSwitchInNav.setText(switchInFund.getNavLatest());
            tvSwitchInCutOffTime.setText(switchInFund.getCutOffTime());
        }
    }

    public void updateSwitchInVolume(float switchOutVolume, PurchasedFund switchOutFund, FundProduct switchInFund) {
        if(switchOutFund != null && switchInFund != null) {
            switchInVolume = (switchOutVolume * Float.parseFloat(switchOutFund.getNavLatest())) / Float.parseFloat(switchInFund.getNavLatest());
            tvSwitchInVolume.setText((int) switchInVolume + "");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnSwitchInDownloadFactSheet) {
            downloadSwitchInFactSheet();
        } else if (v == btnSwitchIn) {
            switchIn();
        } else if (v == btnChangeSwitchOut) {
            goToChangeSwitchOut();
        }
    }

    public void downloadSwitchInFactSheet() {
        Uri uri = Uri.parse(switchInFund.getFactSheetLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void switchIn() {
        Bundle bundle = new Bundle();
        bundle.putFloat(KEY_SWITCH_OUT_VOLUME, switchOutVolume);
        bundle.putFloat(KEY_SWITCH_IN_VOLUME, switchInVolume);
        bundle.putParcelable(KEY_SWITCH_OUT_FUND, Parcels.wrap(switchOutFund));
        bundle.putParcelable(KEY_SWITCH_IN_FUND, Parcels.wrap(switchInFund));
        openActivity(SwitchSummaryActivity.class, bundle);
    }

    public void goToChangeSwitchOut() {
        openActivityAndClearHistory(DashboardActivity.class);
        openActivity(SwitchOutFundListActivity.class, true);
    }

    @Override
    public void onNavigationClick() {
        onBackPressed();
    }
}
