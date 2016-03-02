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

package com.nextzy.oneassetmanagement.view.buy;

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
import com.nextzy.oneassetmanagement.network.model.CreditsFund;
import com.nextzy.oneassetmanagement.network.model.FundProduct;
import com.nextzy.oneassetmanagement.network.model.UserProfile;
import com.nextzy.oneassetmanagement.view.TransactionSuccessfulActivity;
import com.nextzy.oneassetmanagement.view.dialog.ConfirmDialogFragment;

import org.parceler.Parcels;

public class BuyFundDetailActivity extends BaseToolbarActivity implements View.OnClickListener, NextzyService.BuyCallback {
    private static final String KEY_FUND_PRODUCT = "FundProduct";
    private static final String KEY_CREDITS_FUND = "CreditsFund";
    private static final String KEY_USER_PROFILE = "UserProfile";

    private TextView tvCreditsFund;
    private TextView tvUsername;
    private TextView tvClientNumber;
    private TextView tvSymbol;
    private TextView tvName;
    private TextView tvRiskRating;
    private TextView tvStarRating;
    private TextView tvNav;
    private TextView tvCutOffTime;
    private EditText etVolume;
    private Button btnFactSheet;
    private Button btnBuy;

    private FundProduct fundProduct;
    private CreditsFund creditsFund;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_fund_detail);

        checkBundleFromIntent();
        initToolbar();
        prepareView();
        serveData();
    }

    private void checkBundleFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fundProduct = Parcels.unwrap(bundle.getParcelable(KEY_FUND_PRODUCT));
            creditsFund = Parcels.unwrap(bundle.getParcelable(KEY_CREDITS_FUND));
            userProfile = Parcels.unwrap(bundle.getParcelable(KEY_USER_PROFILE));
        }
    }

    private void prepareView() {
        tvUsername = (TextView) findViewById(R.id.buy_fund_detail_tv_user_name);
        tvClientNumber = (TextView) findViewById(R.id.buy_fund_detail_tv_user_client_number);
        tvCreditsFund = (TextView) findViewById(R.id.buy_fund_detail_tv_credits_fund);
        tvSymbol = (TextView) findViewById(R.id.buy_fund_detail_tv_symbol);
        tvName = (TextView) findViewById(R.id.buy_fund_detail_tv_name);
        tvRiskRating = (TextView) findViewById(R.id.buy_fund_detail_tv_risk_rating);
        tvStarRating = (TextView) findViewById(R.id.buy_fund_detail_tv_star_rating);
        tvNav = (TextView) findViewById(R.id.buy_fund_detail_nav);
        tvCutOffTime = (TextView) findViewById(R.id.buy_fund_detail_tv_cut_off_time);
        etVolume = (EditText) findViewById(R.id.buy_fund_detail_et_volume);
        btnFactSheet = (Button) findViewById(R.id.buy_fund_detail_btn_download_fact_sheet);
        btnBuy = (Button) findViewById(R.id.buy_fund_detail_btn_buy);
    }

    private void serveData() {
        setToolbarTitle(getString(R.string.title_buy));
        setBackNavigation();
        btnFactSheet.setOnClickListener(this);
        btnBuy.setOnClickListener(this);

        if (fundProduct != null) {
            tvSymbol.setText(fundProduct.getSymbol());
            tvName.setText(fundProduct.getName());
            tvRiskRating.setText(fundProduct.getRiskRating());
            tvStarRating.setText(fundProduct.getStarRating());
            tvNav.setText(fundProduct.getNavLatest());
            tvCutOffTime.setText(fundProduct.getCutOffTime());
        }
        if(creditsFund != null) {
            updateCreditsFund(creditsFund);
        }
        if(userProfile != null) {
            updateUserProfile(userProfile);
        }
    }

    private void updateCreditsFund(CreditsFund creditsFund) {
        tvCreditsFund.setText(creditsFund.getValue());
    }

    private void updateUserProfile(UserProfile userProfile) {
        tvUsername.setText(userProfile.getName());
        tvClientNumber.setText(userProfile.getClientNumber());
    }

    @Override
    public void onClick(View v) {
        if (v == btnFactSheet) {
            downloadFactSheet();
        } else if (v == btnBuy) {
            buyThisFund();
        }
    }

    public void downloadFactSheet() {
        Uri uri = Uri.parse(fundProduct.getFactSheetLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void buyThisFund() {
        if(!etVolume.getText().toString().isEmpty()) {
            float creditsFundValue = Float.parseFloat(creditsFund.getValue());
            float volumeValue = Float.parseFloat(etVolume.getText().toString());
            if (volumeValue <= creditsFundValue) {
                showConfirmDialog(R.string.transaction_confirm_title, R.string.transaction_confirm_message, new ConfirmDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onDialogClick(ConfirmDialogFragment dialog, int which) {
                        if (which == ConfirmDialogFragment.TYPE_CONFIRM) {
                            buyIt();
                        } else if (which == ConfirmDialogFragment.TYPE_CANCEL) {
                            dialog.dismiss();
                        }
                    }
                });
            } else {
                showMessage(R.string.buy_fund_detail_not_enough_credits_fund);
            }
        } else {
            showMessage(R.string.buy_fund_detail_please_insert_volume);
        }
    }

    public void buyIt() {
        showLoadingDialog();
        NextzyService.buy(this);
    }

    @Override
    public void onBuySuccess() {
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
