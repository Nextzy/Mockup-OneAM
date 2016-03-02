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

package com.nextzy.oneassetmanagement.view.report;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.network.model.FundReport;

import java.util.List;

/**
 * Created by Akexorcist on 1/20/2016 AD.
 */
public class FundListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CONTENT = 1;
    private List<FundReport> fundReportList;

    public FundListAdapter(List<FundReport> fundReportList) {
        this.fundReportList = fundReportList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_report_fund_header, parent, false);
            return new FundHeaderViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_report_fund_item, parent, false);
        return new FundItemViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            FundHeaderViewHolder headerViewHolder = (FundHeaderViewHolder) holder;
        } else {
            position--;
            FundItemViewHolder itemViewHolder = (FundItemViewHolder) holder;
            itemViewHolder.tvFundTitle.setText(fundReportList.get(position).getTitle());
            itemViewHolder.tvFundName.setText(fundReportList.get(position).getName());
            itemViewHolder.viewIndicator.setBackgroundColor(Color.parseColor(fundReportList.get(position).getColor()));
        }
    }

    @Override
    public int getItemCount() {
        return fundReportList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_HEADER)
            return TYPE_HEADER;
        return TYPE_CONTENT;
    }
}
