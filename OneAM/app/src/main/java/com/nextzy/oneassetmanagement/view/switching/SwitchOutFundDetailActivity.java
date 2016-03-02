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
import android.widget.EditText;
import android.widget.TextView;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.common.BaseToolbarActivity;
import com.nextzy.oneassetmanagement.network.model.PurchasedFund;
import com.nextzy.oneassetmanagement.view.dialog.ConfirmDialogFragment;

import org.parceler.Parcels;

public class SwitchOutFundDetailActivity extends BaseToolbarActivity implements View.OnClickListener {
    private static final String KEY_SWITCH_OUT_FUND = "SwitchOutFund";
    private static final String KEY_SWITCH_OUT_VOLUME = "SwitchOutVolume";

    private TextView tvSymbol;
    private TextView tvName;
    private TextView tvRiskRating;
    private TextView tvStarRating;
    private TextView tvNav;
    private TextView tvCutOffTime;
    private TextView tvInPortAmount;
    private EditText etVolume;
    private Button btnFactSheet;
    private Button btnSwitchOut;

    private PurchasedFund switchOutFund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_out_fund_detail);

        checkBundleFromIntent();
        initToolbar();
        prepareView();
        serveData();
    }

    private void checkBundleFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            switchOutFund = Parcels.unwrap(bundle.getParcelable(KEY_SWITCH_OUT_FUND));
        }
    }

    private void prepareView() {
        tvSymbol = (TextView) findViewById(R.id.switch_out_fund_detail_tv_symbol);
        tvName = (TextView) findViewById(R.id.switch_out_fund_detail_tv_name);
        tvRiskRating = (TextView) findViewById(R.id.switch_out_fund_detail_tv_risk_rating);
        tvStarRating = (TextView) findViewById(R.id.switch_out_fund_detail_tv_star_rating);
        tvNav = (TextView) findViewById(R.id.switch_out_fund_detail_tv_nav);
        tvCutOffTime = (TextView) findViewById(R.id.switch_out_fund_detail_tv_cut_off_time);
        tvInPortAmount = (TextView) findViewById(R.id.switch_out_fund_detail_tv_in_port_amount);
        etVolume = (EditText) findViewById(R.id.switch_out_fund_detail_et_volume);
        btnFactSheet = (Button) findViewById(R.id.switch_out_fund_detail_btn_download_fact_sheet);
        btnSwitchOut = (Button) findViewById(R.id.switch_out_fund_detail_btn_switch_out);
    }

    private void serveData() {
        setToolbarTitle(getString(R.string.title_switch));
        setBackNavigation();
        btnFactSheet.setOnClickListener(this);
        btnSwitchOut.setOnClickListener(this);

        if (switchOutFund != null) {
            tvSymbol.setText(switchOutFund.getSymbol());
            tvName.setText(switchOutFund.getName());
            tvRiskRating.setText(switchOutFund.getRiskRating());
            tvStarRating.setText(switchOutFund.getStarRating());
            tvNav.setText(switchOutFund.getNavLatest());
            tvCutOffTime.setText(switchOutFund.getCutOffTime());
            tvInPortAmount.setText(switchOutFund.getInPortAmount());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnFactSheet) {
            downloadFactSheet();
        } else if (v == btnSwitchOut) {
            switchOutThisFund();
        }
    }

    public void downloadFactSheet() {
        Uri uri = Uri.parse(switchOutFund.getFactSheetLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void switchOutThisFund() {
        if(!etVolume.getText().toString().isEmpty()) {
            final float volumeValue = Float.parseFloat(etVolume.getText().toString());
            float inPortAmount = Float.parseFloat(switchOutFund.getInPortAmount());
            if (volumeValue <= inPortAmount) {
                showConfirmDialog(R.string.transaction_confirm_title, R.string.transaction_confirm_message, new ConfirmDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onDialogClick(ConfirmDialogFragment dialog, int which) {
                        if (which == ConfirmDialogFragment.TYPE_CONFIRM) {
                            goToSwitchInFundList(volumeValue);
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

    public void goToSwitchInFundList(float switchOutVolume) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SWITCH_OUT_FUND, Parcels.wrap(switchOutFund));
        bundle.putFloat(KEY_SWITCH_OUT_VOLUME, switchOutVolume);
        openActivity(SwitchInFundListActivity.class, bundle);
    }

    @Override
    public void onNavigationClick() {
        onBackPressed();
    }
}
