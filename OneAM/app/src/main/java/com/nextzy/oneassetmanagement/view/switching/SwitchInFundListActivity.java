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
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.common.BaseToolbarActivity;
import com.nextzy.oneassetmanagement.network.NextzyService;
import com.nextzy.oneassetmanagement.network.model.FundProduct;
import com.nextzy.oneassetmanagement.network.model.PurchasedFund;
import com.nextzy.oneassetmanagement.util.NextzyUtil;
import com.nextzy.oneassetmanagement.view.dashboard.DashboardActivity;

import org.parceler.Parcels;

import java.util.List;

public class SwitchInFundListActivity extends BaseToolbarActivity implements NextzyService.FundListResultCallback, View.OnClickListener {
    private static final String KEY_SWITCH_OUT_FUND = "SwitchOutFund";
    private static final String KEY_SWITCH_OUT_VOLUME = "SwitchOutVolume";
    private static final String KEY_SWITCH_IN_FUND = "SwitchInFund";

    private TextView tvSwitchOutSymbol;
    private TextView tvSwitchOutVolume;
    private TextView tvSwitchOutName;
    private Button btnChangeSwitchOut;
    private Button btnMoreFilter;
    private LinearLayout layoutContent;
    private LinearLayout layoutFundListContainer;
    private CardView cvHeader;

    private PurchasedFund switchOutFund;
    private float switchOutVolume;
    private List<FundProduct> switchInFundList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_in_fund_list);

        checkBundleFromIntent();
        initToolbar();
        prepareView();
        serveData();
        callNextzyService();
    }

    public void prepareView() {
        tvSwitchOutSymbol = (TextView) findViewById(R.id.switch_in_fund_list_tv_symbol);
        tvSwitchOutVolume = (TextView) findViewById(R.id.switch_in_fund_list_tv_switch_out_volume);
        tvSwitchOutName = (TextView) findViewById(R.id.switch_in_fund_list_tv_name);
        btnChangeSwitchOut = (Button) findViewById(R.id.switch_in_fund_list_btn_change_switch_out);
        btnMoreFilter = (Button) findViewById(R.id.switch_in_fund_list_btn_more_filter);
        layoutContent = (LinearLayout) findViewById(R.id.switch_in_fund_list_layout_content);
        layoutFundListContainer = (LinearLayout) findViewById(R.id.switch_in_fund_list_layout_fund_list);
        cvHeader = (CardView) findViewById(R.id.switch_in_fund_list_cv_header);
    }

    public void serveData() {
        setToolbarTitle(getString(R.string.title_switch));
        setBackNavigation();
        layoutContent.setVisibility(View.INVISIBLE);
        cvHeader.setVisibility(View.INVISIBLE);

        btnMoreFilter.setOnClickListener(this);
        btnChangeSwitchOut.setOnClickListener(this);

        updateSwitchOutFund(switchOutFund);
        updateSwitchOutVolume(switchOutVolume);
    }

    private void checkBundleFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            switchOutFund = Parcels.unwrap(bundle.getParcelable(KEY_SWITCH_OUT_FUND));
            switchOutVolume = bundle.getFloat(KEY_SWITCH_OUT_VOLUME);
        }
    }

    public void updateSwitchOutFund(PurchasedFund switchOutFund) {
        if (switchOutFund != null) {
            tvSwitchOutSymbol.setText(switchOutFund.getSymbol());
            tvSwitchOutName.setText(switchOutFund.getName());
        }
    }

    public void updateSwitchOutVolume(float switchOutVolume) {
        tvSwitchOutVolume.setText((int) switchOutVolume + "");
    }

    public void callNextzyService() {
        showLoadingDialog();
        NextzyService.getFundList(this);
    }

    @Override
    public void onFundListSuccess(List<FundProduct> switchInFundList) {
        this.switchInFundList = switchInFundList;
        updateSwitchInFundList(switchInFundList);
        checkAllServiceRequest();
    }

    @Override
    public void onClick(View v) {
        if (v == btnChangeSwitchOut) {
            goToChangeSwitchOut();
        } else if (v == btnMoreFilter) {
            openFilterPanel();
        }
    }

    public void goToChangeSwitchOut() {
        openActivityAndClearHistory(DashboardActivity.class);
        openActivity(SwitchOutFundListActivity.class, true);
    }

    private void updateSwitchInFundList(List<FundProduct> switchInFundList) {
        for (final FundProduct switchInFund : switchInFundList) {
            View fundItemView = LayoutInflater.from(this).inflate(R.layout.view_switch_in_fund_list_item, layoutFundListContainer, false);
            CardView cvFundItem = (CardView) fundItemView.findViewById(R.id.switch_in_fund_list_cv_fund_item);
            TextView tvSymbol = (TextView) fundItemView.findViewById(R.id.switch_in_fund_list_tv_symbol);
            TextView tvName = (TextView) fundItemView.findViewById(R.id.switch_in_fund_list_tv_name);
            TextView tvAssetType = (TextView) fundItemView.findViewById(R.id.switch_in_fund_list_tv_asset_type);
            TextView tvRating = (TextView) fundItemView.findViewById(R.id.switch_in_fund_list_tv_rating);

            tvSymbol.setText(switchInFund.getSymbol());
            tvName.setText(switchInFund.getName());
            tvAssetType.setText(switchInFund.getAssetType());
            tvRating.setText(switchInFund.getRiskRating());
            cvFundItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(KEY_SWITCH_OUT_FUND, Parcels.wrap(switchOutFund));
                    bundle.putFloat(KEY_SWITCH_OUT_VOLUME, switchOutVolume);
                    bundle.putParcelable(KEY_SWITCH_IN_FUND, Parcels.wrap(switchInFund));
                    openActivity(SwitchInFundDetailActivity.class, bundle);
                }
            });
            layoutFundListContainer.addView(fundItemView);
        }
    }

    public void checkAllServiceRequest() {
        if (switchOutFund != null && switchInFundList != null) {
            showUp();
        }
    }

    public void showUp() {
        dismissDialog();
        NextzyUtil.startAnimator(this, cvHeader, R.animator.animator_header_show_by_slide_down, new NextzyUtil.AnimateFinishCallback() {
            @Override
            public void onAnimateFinished() {
                NextzyUtil.startAnimatorSet(SwitchInFundListActivity.this, layoutContent, R.animator.animator_content_show_by_slide_up, null);
            }
        });
    }

    public void openFilterPanel() {
        showMessage(R.string.more_filter_unavailable);
    }

    @Override
    public void onNavigationClick() {
        onBackPressed();
    }
}
