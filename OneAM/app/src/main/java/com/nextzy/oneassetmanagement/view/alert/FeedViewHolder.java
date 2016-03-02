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

package com.nextzy.oneassetmanagement.view.alert;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextzy.oneassetmanagement.R;

/**
 * Created by Akexorcist on 1/20/2016 AD.
 */
public class FeedViewHolder extends RecyclerView.ViewHolder {
    TextView tvFeedTitle;
    TextView tvFeedTimestamp;
    LinearLayout layoutReadMore;

    public FeedViewHolder(View itemView) {
        super(itemView);

        tvFeedTitle = (TextView) itemView.findViewById(R.id.alert_tv_feed_title);
        tvFeedTimestamp = (TextView) itemView.findViewById(R.id.alert_tv_feed_timestamp);
        layoutReadMore = (LinearLayout) itemView.findViewById(R.id.alert_layout_feed_read_more);
    }
}
