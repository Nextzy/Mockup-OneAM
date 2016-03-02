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

package com.nextzy.oneassetmanagement.view.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.common.BaseToolbarActivity;
import com.nextzy.oneassetmanagement.network.model.PurchasedFund;
import com.nextzy.oneassetmanagement.view.sell.SellFundDetailActivity;
import com.nextzy.oneassetmanagement.view.switching.SwitchOutFundDetailActivity;

import org.parceler.Parcels;

public class PurchasedFundDetailActivity extends BaseToolbarActivity implements View.OnClickListener {
    private static final String KEY_PURCHASED_FUND = "PurchasedFund";
    private static final String KEY_FUND_PRODUCT = "FundProduct";
    private static final String KEY_SWITCH_OUT_FUND = "SwitchOutFund";

    private TextView tvSymbol;
    private TextView tvName;
    private TextView tvRiskRating;
    private TextView tvStarRating;
    private TextView tvCutOffTime;
    private TextView tvLatestNav;
    private TextView tvInPortAmount;
    private Button btnDownloadFactSheet;
    private Button btnSwitch;
    private Button btnSell;

    private PurchasedFund purchasedFund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_fund_detail);

        checkBundleFromIntent();
        initToolbar();
        prepareView();
        serveData();
        updatePurchasedFund(purchasedFund);
    }

    public void checkBundleFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            purchasedFund = Parcels.unwrap(bundle.getParcelable(KEY_PURCHASED_FUND));
        }
    }

    public void updatePurchasedFund(PurchasedFund purchasedFund) {
        if (purchasedFund != null) {
            tvSymbol.setText(purchasedFund.getSymbol());
            tvName.setText(purchasedFund.getName());
            tvRiskRating.setText(purchasedFund.getRiskRating());
            tvStarRating.setText(purchasedFund.getStarRating());
            tvCutOffTime.setText(purchasedFund.getCutOffTime());
            tvLatestNav.setText(purchasedFund.getNavLatest());
            tvInPortAmount.setText(purchasedFund.getInPortAmount());
        }
    }

    public void prepareView() {
        tvSymbol = (TextView) findViewById(R.id.purchased_fund_detail_tv_symbol);
        tvName = (TextView) findViewById(R.id.purchased_fund_detail_tv_name);
        tvRiskRating = (TextView) findViewById(R.id.purchased_fund_detail_tv_risk_rating);
        tvStarRating = (TextView) findViewById(R.id.purchased_fund_detail_tv_star_rating);
        tvCutOffTime = (TextView) findViewById(R.id.purchased_fund_detail_tv_cut_off_time);
        tvLatestNav = (TextView) findViewById(R.id.purchased_fund_detail_tv_latest_nav);
        tvInPortAmount = (TextView) findViewById(R.id.purchased_fund_detail_tv_in_port_amount);
        btnDownloadFactSheet = (Button) findViewById(R.id.purchased_fund_detail_btn_download_fact_sheet);
        btnSell = (Button) findViewById(R.id.purchased_fund_detail_btn_sell);
        btnSwitch = (Button) findViewById(R.id.purchased_fund_detail_btn_switch);
    }

    public void serveData() {
        setToolbarTitle(getString(R.string.title_purchased_fund_detail));
        setBackNavigation();
        btnDownloadFactSheet.setOnClickListener(this);
        btnSwitch.setOnClickListener(this);
        btnSell.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDownloadFactSheet) {
            downloadFactSheet();
        } else if (v == btnSell) {
            goToSellFund();
        } else if (v == btnSwitch) {
            goToSwitchFund();
        }
    }

    public void downloadFactSheet() {
        Uri uri = Uri.parse(purchasedFund.getFactSheetLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void goToSellFund() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_FUND_PRODUCT, Parcels.wrap(purchasedFund));
        openActivity(SellFundDetailActivity.class, bundle, true);
    }

    public void goToSwitchFund() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SWITCH_OUT_FUND, Parcels.wrap(purchasedFund));
        openActivity(SwitchOutFundDetailActivity.class, bundle, true);
    }

    @Override
    public void onNavigationClick() {
        onBackPressed();
    }
}
