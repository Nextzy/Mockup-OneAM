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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.network.model.PurchasedFund;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasedFundFragment extends Fragment implements View.OnClickListener {
    private static final String KEY_PURCHASED_FUND = "PurchasedFund";
    private static final String KEY_POSITION = "Position";

    private static final String[] colors = new String[]{"#74dae8", "#f37092", "#f39f70",
            "#d88cee", "#8ceeb5", "#fcf47d"};

    private View viewIndicator;
    private TextView tvSymbol;
    private TextView tvName;
    private TextView tvRiskRating;
    private TextView tvStarRating;
    private TextView tvFundNav;
    private TextView tvCutOffTime;
    private TextView tvInPortAmount;
    private LinearLayout layoutContent;

    private PurchasedFund purchasedFund;
    private int position;

    public static PurchasedFundFragment newInstance(PurchasedFund purchasedFund, int position) {
        PurchasedFundFragment fragment = new PurchasedFundFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PURCHASED_FUND, Parcels.wrap(purchasedFund));
        bundle.putInt(KEY_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchased_fund, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkArgumentFromFragment();
        prepareView(view);
        serveData();
        updatePurchasedFund(purchasedFund);
        updateIndicatorColor(position);
    }

    public void checkArgumentFromFragment() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            purchasedFund = Parcels.unwrap(bundle.getParcelable(KEY_PURCHASED_FUND));
            position = bundle.getInt(KEY_POSITION);
        }
    }

    public void prepareView(View view) {
        viewIndicator = view.findViewById(R.id.purchased_fund_view_indicator);
        tvSymbol = (TextView) view.findViewById(R.id.purchased_fund_tv_symbol);
        tvName = (TextView) view.findViewById(R.id.purchased_fund_tv_name);
        tvRiskRating = (TextView) view.findViewById(R.id.purchased_fund_tv_risk_rating);
        tvStarRating = (TextView) view.findViewById(R.id.purchased_fund_tv_star_rating);
        tvFundNav = (TextView) view.findViewById(R.id.purchased_fund_nav);
        tvCutOffTime = (TextView) view.findViewById(R.id.purchased_fund_tv_cut_off_time);
        tvInPortAmount = (TextView) view.findViewById(R.id.purchased_fund_tv_in_port_amount);
        layoutContent = (LinearLayout) view.findViewById(R.id.purchased_fund_layout_content);
    }

    public void serveData() {
        layoutContent.setOnClickListener(this);
    }

    public void updatePurchasedFund(PurchasedFund purchasedFund) {
        if (purchasedFund != null) {
            tvSymbol.setText(purchasedFund.getSymbol());
            tvName.setText(purchasedFund.getName());
            tvRiskRating.setText(purchasedFund.getRiskRating());
            tvStarRating.setText(purchasedFund.getStarRating());
            tvFundNav.setText(purchasedFund.getNavLatest());
            tvCutOffTime.setText(purchasedFund.getCutOffTime());
            tvInPortAmount.setText(purchasedFund.getInPortAmount());
        }
    }

    public void updateIndicatorColor(int position) {
        viewIndicator.setBackgroundColor(Color.parseColor(colors[position % colors.length]));
    }

    @Override
    public void onClick(View v) {
        if (v == layoutContent) {
            goToPurchasedFundDetail();
        }
    }

    public void goToPurchasedFundDetail() {
        Intent intent = new Intent(getActivity(), PurchasedFundDetailActivity.class);
        intent.putExtra(KEY_PURCHASED_FUND, Parcels.wrap(purchasedFund));
        startActivity(intent);
    }
}
