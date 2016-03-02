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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.common.BaseToolbarActivity;
import com.nextzy.oneassetmanagement.network.NextzyService;
import com.nextzy.oneassetmanagement.network.model.PurchasedFund;
import com.nextzy.oneassetmanagement.network.model.TotalProfit;
import com.nextzy.oneassetmanagement.network.model.UserProfile;
import com.nextzy.oneassetmanagement.util.NextzyUtil;
import com.nextzy.oneassetmanagement.view.buy.BuyFundListActivity;
import com.nextzy.oneassetmanagement.view.menu.MenuActivity;
import com.nextzy.oneassetmanagement.view.sell.SellFundListActivity;
import com.nextzy.oneassetmanagement.view.switching.SwitchOutFundListActivity;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends BaseToolbarActivity implements NextzyService.UserProfileCallback, View.OnClickListener, NextzyService.PurchasedFundListResultCallback, NextzyService.TotalProfitCallback, ViewPager.OnPageChangeListener, OnChartValueSelectedListener {
    private static final String[] colors = new String[]{"#74dae8", "#f37092", "#f39f70",
            "#d88cee", "#8ceeb5", "#fcf47d"};

    private Button btnBuy;
    private Button btnSell;
    private Button btnSwitch;
    private TextView tvUsername;
    private TextView tvClientNumber;
    private TextView tvTotalProfit;
    private CardView cvHeader;
    private LinearLayout layoutContent;
    private ViewPager vpPurchasedFund;
    private ImageView ivArrowNext;
    private ImageView ivArrowPrev;
    private PieChart pcInvestmentRatio;

    private UserProfile userProfile;
    private List<PurchasedFund> purchasedFundList;
    private TotalProfit totalProfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initToolbar();
        prepareView();
        serveData();
        callNextzyService();
    }

    public void prepareView() {
        tvUsername = (TextView) findViewById(R.id.dashboard_tv_user_name);
        tvClientNumber = (TextView) findViewById(R.id.dashboard_tv_user_client_number);
        tvTotalProfit = (TextView) findViewById(R.id.dashboard_tv_total_profit);
        btnBuy = (Button) findViewById(R.id.dashboard_btn_buy);
        btnSell = (Button) findViewById(R.id.dashboard_btn_sell);
        btnSwitch = (Button) findViewById(R.id.dashboard_btn_switch);
        cvHeader = (CardView) findViewById(R.id.dashboard_cv_header);
        layoutContent = (LinearLayout) findViewById(R.id.dashboard_layout_content);
        vpPurchasedFund = (ViewPager) findViewById(R.id.dashboard_vp_purchased_fund);
        ivArrowNext = (ImageView) findViewById(R.id.dashboard_iv_arrow_next);
        ivArrowPrev = (ImageView) findViewById(R.id.dashboard_iv_arrow_prev);
        pcInvestmentRatio = (PieChart) findViewById(R.id.dashboard_pc_investment_ratio);
    }

    public void serveData() {
        setToolbarTitle(getString(R.string.title_dashboard));

        cvHeader.setVisibility(View.INVISIBLE);
        layoutContent.setVisibility(View.INVISIBLE);
        ivArrowNext.setVisibility(View.INVISIBLE);
        ivArrowPrev.setVisibility(View.INVISIBLE);
        pcInvestmentRatio.setVisibility(View.INVISIBLE);
        ivArrowNext.setOnClickListener(this);
        ivArrowPrev.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        btnSell.setOnClickListener(this);
        btnSwitch.setOnClickListener(this);
        vpPurchasedFund.setOffscreenPageLimit(5);
        vpPurchasedFund.addOnPageChangeListener(this);
    }

    public void callNextzyService() {
        showLoadingDialog();
        NextzyService.getUserProfile(this);
        NextzyService.getPurchasedFundList(this);
        NextzyService.getTotalProfit(this);
    }

    @Override
    public void onUserProfileSuccess(UserProfile userProfile) {
        this.userProfile = userProfile;
        updateUserProfile(userProfile);
        checkAllServiceRequest();
    }

    @Override
    public void onPurchasedFundListSuccess(List<PurchasedFund> purchasedFundList) {
        this.purchasedFundList = purchasedFundList;
        updatePurchasedFundList(purchasedFundList);
        updateInvestmentRatioChart(purchasedFundList);
        checkAllServiceRequest();
    }

    @Override
    public void onCreditsFundSuccess(TotalProfit totalProfit) {
        this.totalProfit = totalProfit;
        updateTotalProfit(totalProfit);
        checkAllServiceRequest();
    }

    public void updateUserProfile(UserProfile userProfile) {
        tvUsername.setText(userProfile.getName());
        tvClientNumber.setText(userProfile.getClientNumber());
    }

    public void updatePurchasedFundList(List<PurchasedFund> purchasedFundList) {
        if (purchasedFundList != null && purchasedFundList.size() > 1) {
            ivArrowNext.setVisibility(View.VISIBLE);
            PurchasedFundPagerAdapter adapter = new PurchasedFundPagerAdapter(getSupportFragmentManager(), purchasedFundList);
            vpPurchasedFund.setAdapter(adapter);
            vpPurchasedFund.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dashboard_view_pager_margin));
        }
    }

    public void updateInvestmentRatioChart(List<PurchasedFund> purchasedFundList) {
        if (purchasedFundList != null && purchasedFundList.size() > 1) {
            pcInvestmentRatio.setVisibility(View.VISIBLE);
            pcInvestmentRatio.setUsePercentValues(true);
            pcInvestmentRatio.setDescription("");
            pcInvestmentRatio.setDragDecelerationFrictionCoef(0.95f);
            pcInvestmentRatio.setRotationEnabled(false);
            pcInvestmentRatio.setHighlightPerTapEnabled(true);
            pcInvestmentRatio.getLegend().setEnabled(false);
            pcInvestmentRatio.setOnChartValueSelectedListener(this);

            float totalAmount = getTotalAmount(purchasedFundList);
            List<Entry> entryList = new ArrayList<>();

            ArrayList<Integer> colorList = new ArrayList<>();
            ArrayList<String> xVals = new ArrayList<>();
            for (int i = 0; i < purchasedFundList.size(); i++) {
                entryList.add(new Entry(getInvestmentRatio(purchasedFundList.get(i), totalAmount), i));
                xVals.add("");
                colorList.add(Color.parseColor(colors[i % purchasedFundList.size()]));
            }

            PieDataSet dataSet = new PieDataSet(entryList, "");
            dataSet.setSliceSpace(4f);

            dataSet.setSelectionShift(5f);
            dataSet.setColors(colorList);

            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(14);
            pcInvestmentRatio.setData(data);
        }
    }

    public float getInvestmentRatio(PurchasedFund purchasedFund, float total) {
        float amount = Float.parseFloat(purchasedFund.getInPortAmount());
        return amount * 100 / total;
    }

    public float getTotalAmount(List<PurchasedFund> purchasedFundList) {
        float total = 0;
        for (PurchasedFund purchasedFund : purchasedFundList) {
            total += Float.parseFloat(purchasedFund.getInPortAmount());
        }
        return total;
    }

    public void updateTotalProfit(TotalProfit totalProfit) {
        tvTotalProfit.setText(totalProfit.getValue());
    }

    public void showUp() {
        dismissDialog();
        NextzyUtil.startAnimator(this, cvHeader, R.animator.animator_header_show_by_slide_down, new NextzyUtil.AnimateFinishCallback() {
            @Override
            public void onAnimateFinished() {
                NextzyUtil.startAnimatorSet(DashboardActivity.this, layoutContent, R.animator.animator_content_show_by_slide_up, null);
            }
        });
    }

    public void checkAllServiceRequest() {
        if (userProfile != null && purchasedFundList != null && totalProfit != null) {
            showUp();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnBuy) {
            goToBuyFundActivity();
        } else if (v == btnSell) {
            goToSellFundActivity();
        } else if (v == btnSwitch) {
            goToSwitchFundActivity();
        } else if (v == ivArrowNext) {
            nextPurchasedFundItem();
        } else if (v == ivArrowPrev) {
            prevPurchasedFundItem();
        }
    }

    public void goToBuyFundActivity() {
        openActivity(BuyFundListActivity.class);
    }

    public void goToSellFundActivity() {
        openActivity(SellFundListActivity.class);
    }

    public void goToSwitchFundActivity() {
        openActivity(SwitchOutFundListActivity.class);
    }

    public void nextPurchasedFundItem() {
        vpPurchasedFund.setCurrentItem(vpPurchasedFund.getCurrentItem() + 1);
    }

    public void prevPurchasedFundItem() {
        vpPurchasedFund.setCurrentItem(vpPurchasedFund.getCurrentItem() - 1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            NextzyUtil.startAnimator(this, ivArrowPrev, R.animator.animator_fade_out, null);
        } else {
            NextzyUtil.startAnimator(this, ivArrowPrev, R.animator.animator_fade_in, null);
        }
        if (purchasedFundList != null && position < purchasedFundList.size() - 1) {
            NextzyUtil.startAnimator(this, ivArrowNext, R.animator.animator_fade_in, null);
        } else {
            NextzyUtil.startAnimator(this, ivArrowNext, R.animator.animator_fade_out, null);
        }

        pcInvestmentRatio.highlightValue(position, 0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onValueSelected(Entry entry, int dataSetIndex, Highlight highlight) {
        vpPurchasedFund.setCurrentItem(entry.getXIndex());
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onNavigationClick() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
