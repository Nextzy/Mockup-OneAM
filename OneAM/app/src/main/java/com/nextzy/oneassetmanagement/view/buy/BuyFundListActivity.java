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
import com.nextzy.oneassetmanagement.network.model.CreditsFund;
import com.nextzy.oneassetmanagement.network.model.FundProduct;
import com.nextzy.oneassetmanagement.network.model.UserProfile;
import com.nextzy.oneassetmanagement.util.NextzyUtil;

import org.parceler.Parcels;

import java.util.List;

public class BuyFundListActivity extends BaseToolbarActivity implements NextzyService.FundListResultCallback, NextzyService.CreditsFundCallback, NextzyService.UserProfileCallback, View.OnClickListener {
    private static final String KEY_FUND_PRODUCT = "FundProduct";
    private static final String KEY_CREDITS_FUND = "CreditsFund";
    private static final String KEY_USER_PROFILE = "UserProfile";

    private TextView tvCreditsFund;
    private TextView tvUsername;
    private TextView tvClientNumber;
    private LinearLayout layoutFundListContainer;
    private LinearLayout layoutFundListContent;
    private Button btnMoreFilter;
    private CardView cvHeader;

    private List<FundProduct> fundProductList;
    private CreditsFund creditsFund;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_fund_list);

        initToolbar();
        prepareView();
        serveData();
        callNextzyService();
    }

    public void prepareView() {
        tvCreditsFund = (TextView) findViewById(R.id.buy_fund_list_tv_credits_fund);
        tvUsername = (TextView) findViewById(R.id.buy_fund_list_tv_user_name);
        tvClientNumber = (TextView) findViewById(R.id.buy_fund_list_tv_user_client_number);
        layoutFundListContainer = (LinearLayout) findViewById(R.id.buy_fund_list_layout_fund_list);
        layoutFundListContent = (LinearLayout) findViewById(R.id.buy_fund_list_layout_content);
        btnMoreFilter = (Button) findViewById(R.id.buy_fund_list_btn_more_filter);
        cvHeader = (CardView) findViewById(R.id.buy_fund_list_cv_header);
    }

    public void serveData() {
        setToolbarTitle(getString(R.string.title_buy));
        setBackNavigation();
        layoutFundListContent.setVisibility(View.INVISIBLE);
        cvHeader.setVisibility(View.INVISIBLE);
        btnMoreFilter.setOnClickListener(this);
    }

    public void callNextzyService() {
        showLoadingDialog();
        NextzyService.getFundList(this);
        NextzyService.getUserProfile(this);
        NextzyService.getCreditsFund(this);
    }

    @Override
    public void onFundListSuccess(List<FundProduct> fundProductList) {
        this.fundProductList = fundProductList;
        updateFundList(fundProductList);
        checkAllServiceRequest();
    }

    @Override
    public void onCreditsFundSuccess(CreditsFund creditsFund) {
        this.creditsFund = creditsFund;
        updateCreditsFund(creditsFund);
        checkAllServiceRequest();

    }

    @Override
    public void onUserProfileSuccess(UserProfile userProfile) {
        this.userProfile = userProfile;
        updateUserProfile(userProfile);
        checkAllServiceRequest();
    }

    private void updateFundList(List<FundProduct> fundProductList) {
        for (final FundProduct fundProduct : fundProductList) {
            View fundItemView = LayoutInflater.from(this).inflate(R.layout.view_buy_fund_list_item, layoutFundListContainer, false);
            CardView cvFundItem = (CardView) fundItemView.findViewById(R.id.buy_fund_list_cv_fund_item);
            TextView tvSymbol = (TextView) fundItemView.findViewById(R.id.buy_fund_list_tv_symbol);
            TextView tvName = (TextView) fundItemView.findViewById(R.id.buy_fund_list_tv_name);
            TextView tvAssetType = (TextView) fundItemView.findViewById(R.id.buy_fund_list_tv_asset_type);
            TextView tvRating = (TextView) fundItemView.findViewById(R.id.buy_fund_list_tv_rating);

            tvSymbol.setText(fundProduct.getSymbol());
            tvName.setText(fundProduct.getName());
            tvAssetType.setText(fundProduct.getAssetType());
            tvRating.setText(fundProduct.getRiskRating());
            cvFundItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(KEY_FUND_PRODUCT, Parcels.wrap(fundProduct));
                    bundle.putParcelable(KEY_CREDITS_FUND, Parcels.wrap(creditsFund));
                    bundle.putParcelable(KEY_USER_PROFILE, Parcels.wrap(userProfile));
                    openActivity(BuyFundDetailActivity.class, bundle);
                }
            });
            layoutFundListContainer.addView(fundItemView);
        }
    }

    private void updateCreditsFund(CreditsFund creditsFund) {
        tvCreditsFund.setText(creditsFund.getValue());
    }

    private void updateUserProfile(UserProfile userProfile) {
        tvUsername.setText(userProfile.getName());
        tvClientNumber.setText(userProfile.getClientNumber());
    }

    public void checkAllServiceRequest() {
        if (fundProductList != null && creditsFund != null && userProfile != null) {
            showUp();
        }
    }

    public void showUp() {
        dismissDialog();
        NextzyUtil.startAnimator(this, cvHeader, R.animator.animator_header_show_by_slide_down, new NextzyUtil.AnimateFinishCallback() {
            @Override
            public void onAnimateFinished() {
                NextzyUtil.startAnimatorSet(BuyFundListActivity.this, layoutFundListContent, R.animator.animator_content_show_by_slide_up, null);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnMoreFilter) {
            openFilterPanel();
        }
    }

    public void openFilterPanel() {
        showMessage(R.string.more_filter_unavailable);
    }

    @Override
    public void onNavigationClick() {
        onBackPressed();
    }
}
