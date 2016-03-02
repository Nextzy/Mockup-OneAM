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
import com.nextzy.oneassetmanagement.network.model.PurchasedFund;
import com.nextzy.oneassetmanagement.util.NextzyUtil;

import org.parceler.Parcels;

import java.util.List;

public class SellFundListActivity extends BaseToolbarActivity implements NextzyService.PurchasedFundListResultCallback, View.OnClickListener {
    private static final String KEY_FUND_PRODUCT = "FundProduct";

    private LinearLayout layoutContent;
    private LinearLayout layoutFundListContainer;
    private Button btnMoreFilter;

    private List<PurchasedFund> purchasedFundList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_fund_list);

        initToolbar();
        prepareView();
        serveData();
        callNextzyService();
    }

    public void prepareView() {
        layoutContent = (LinearLayout) findViewById(R.id.sell_fund_list_layout_content);
        layoutFundListContainer = (LinearLayout) findViewById(R.id.sell_fund_list_layout_fund_list);
        btnMoreFilter = (Button) findViewById(R.id.sell_fund_list_btn_more_filter);
    }

    public void serveData() {
        setToolbarTitle(getString(R.string.title_sell));
        setBackNavigation();
        layoutContent.setVisibility(View.INVISIBLE);
        btnMoreFilter.setOnClickListener(this);
    }

    public void callNextzyService() {
        showLoadingDialog();
        NextzyService.getPurchasedFundList(this);
    }

    @Override
    public void onPurchasedFundListSuccess(List<PurchasedFund> purchasedFundList) {
        this.purchasedFundList = purchasedFundList;
        updatePurchasedFundList(purchasedFundList);
        checkAllServiceRequest();
    }

    public void updatePurchasedFundList(List<PurchasedFund> purchasedFundList) {
        for (final PurchasedFund purchasedFund : purchasedFundList) {
            View fundItemView = LayoutInflater.from(this).inflate(R.layout.view_sell_fund_list_item, layoutFundListContainer, false);
            CardView cvFundItem = (CardView) fundItemView.findViewById(R.id.buy_fund_list_cv_fund_item);
            TextView tvSymbol = (TextView) fundItemView.findViewById(R.id.buy_fund_list_tv_symbol);
            TextView tvName = (TextView) fundItemView.findViewById(R.id.buy_fund_list_tv_name);
            TextView tvAssetType = (TextView) fundItemView.findViewById(R.id.buy_fund_list_tv_asset_type);
            TextView tvRating = (TextView) fundItemView.findViewById(R.id.buy_fund_list_tv_rating);
            TextView tvInPortAmount = (TextView) fundItemView.findViewById(R.id.buy_fund_list_tv_in_port_amount);

            tvSymbol.setText(purchasedFund.getSymbol());
            tvName.setText(purchasedFund.getName());
            tvAssetType.setText(purchasedFund.getAssetType());
            tvRating.setText(purchasedFund.getRiskRating());
            tvInPortAmount.setText(purchasedFund.getInPortAmount());
            cvFundItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(KEY_FUND_PRODUCT, Parcels.wrap(purchasedFund));
                    openActivity(SellFundDetailActivity.class, bundle);
                }
            });
            layoutFundListContainer.addView(fundItemView);
        }
    }

    public void checkAllServiceRequest() {
        if (purchasedFundList != null) {
            showUp();
        }
    }

    public void showUp() {
        dismissDialog();
        NextzyUtil.startAnimatorSet(this, layoutContent, R.animator.animator_content_show_by_slide_up, null);
    }

    @Override
    public void onClick(View v) {
        openFilterPanel();
    }

    public void openFilterPanel() {
        showMessage(R.string.more_filter_unavailable);
    }

    @Override
    public void onNavigationClick() {
        onBackPressed();
    }
}
