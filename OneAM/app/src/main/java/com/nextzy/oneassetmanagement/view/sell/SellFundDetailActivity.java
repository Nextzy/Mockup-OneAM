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

package com.nextzy.oneassetmanagement.view.sell;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.common.BaseToolbarActivity;
import com.nextzy.oneassetmanagement.network.NextzyService;
import com.nextzy.oneassetmanagement.network.model.PurchasedFund;
import com.nextzy.oneassetmanagement.view.TransactionSuccessfulActivity;
import com.nextzy.oneassetmanagement.view.dialog.ConfirmDialogFragment;

import org.parceler.Parcels;

public class SellFundDetailActivity extends BaseToolbarActivity implements View.OnClickListener, NextzyService.SellCallback {
    private static final String KEY_FUND_PRODUCT = "FundProduct";

    private TextView tvSymbol;
    private TextView tvName;
    private TextView tvRiskRating;
    private TextView tvStarRating;
    private TextView tvNav;
    private TextView tvCutOffTime;
    private TextView tvInPortAmount;
    private EditText etVolume;
    private Button btnFactSheet;
    private Button btnSell;

    private PurchasedFund purchasedFund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_fund_detail);

        checkBundleFromIntent();
        initToolbar();
        prepareView();
        serveData();
    }

    private void checkBundleFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            purchasedFund = Parcels.unwrap(bundle.getParcelable(KEY_FUND_PRODUCT));
        }
    }

    private void prepareView() {
        tvSymbol = (TextView) findViewById(R.id.sell_fund_detail_tv_symbol);
        tvName = (TextView) findViewById(R.id.sell_fund_detail_tv_name);
        tvRiskRating = (TextView) findViewById(R.id.sell_fund_detail_tv_risk_rating);
        tvStarRating = (TextView) findViewById(R.id.sell_fund_detail_tv_star_rating);
        tvNav = (TextView) findViewById(R.id.sell_fund_detail_nav);
        tvCutOffTime = (TextView) findViewById(R.id.sell_fund_detail_tv_cut_off_time);
        tvInPortAmount = (TextView) findViewById(R.id.sell_fund_detail_tv_in_port_amount);
        etVolume = (EditText) findViewById(R.id.sell_fund_detail_et_volume);
        btnFactSheet = (Button) findViewById(R.id.sell_fund_detail_btn_download_fact_sheet);
        btnSell = (Button) findViewById(R.id.sell_fund_detail_btn_sell);
    }

    private void serveData() {
        setToolbarTitle(getString(R.string.title_sell));
        setBackNavigation();
        btnFactSheet.setOnClickListener(this);
        btnSell.setOnClickListener(this);

        if (purchasedFund != null) {
            tvSymbol.setText(purchasedFund.getSymbol());
            tvName.setText(purchasedFund.getName());
            tvRiskRating.setText(purchasedFund.getRiskRating());
            tvStarRating.setText(purchasedFund.getStarRating());
            tvNav.setText(purchasedFund.getNavLatest());
            tvCutOffTime.setText(purchasedFund.getCutOffTime());
            tvInPortAmount.setText(purchasedFund.getInPortAmount());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnFactSheet) {
            downloadFactSheet();
        } else if (v == btnSell) {
            sellThisFund();
        }
    }

    public void downloadFactSheet() {
        Uri uri = Uri.parse(purchasedFund.getFactSheetLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void sellThisFund() {
        if(!etVolume.getText().toString().isEmpty()) {
            float volumeValue = Float.parseFloat(etVolume.getText().toString());
            float inPortAmount = Float.parseFloat(purchasedFund.getInPortAmount());
            if (volumeValue <= inPortAmount) {
                showConfirmDialog(R.string.transaction_confirm_title, R.string.transaction_confirm_message, new ConfirmDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onDialogClick(ConfirmDialogFragment dialog, int which) {
                        if (which == ConfirmDialogFragment.TYPE_CONFIRM) {
                            sellIt();
                        } else if (which == ConfirmDialogFragment.TYPE_CANCEL) {
                            dialog.dismiss();
                        }
                    }
                });
            } else {
                showMessage(R.string.sell_fund_detail_not_enough_in_port_amount);
            }
        } else {
            showMessage(R.string.sell_fund_detail_please_insert_volume);
        }
    }

    public void sellIt() {
        showLoadingDialog();
        NextzyService.sell(this);
    }

    @Override
    public void onSellSuccess() {
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
