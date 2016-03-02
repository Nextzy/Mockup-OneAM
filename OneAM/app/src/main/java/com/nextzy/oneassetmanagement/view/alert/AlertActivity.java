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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.common.BaseToolbarActivity;
import com.nextzy.oneassetmanagement.network.NextzyService;
import com.nextzy.oneassetmanagement.network.model.Feed;
import com.nextzy.oneassetmanagement.util.NextzyUtil;
import com.nextzy.oneassetmanagement.view.menu.MenuActivity;

import java.util.List;

public class AlertActivity extends BaseToolbarActivity implements NextzyService.FeedCallback {
    private RecyclerView rvFeed;
    private List<Feed> feedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        initToolbar();
        prepareView();
        serveData();
        callNextzyService();
    }

    public void prepareView() {
        rvFeed = (RecyclerView) findViewById(R.id.alert_rv_feed);
    }

    public void serveData() {
        setToolbarTitle(getString(R.string.title_alert));

        rvFeed.setLayoutManager(new LinearLayoutManager(this));
    }

    public void callNextzyService() {
        showLoadingDialog();
        NextzyService.getFeedList(this);
    }

    @Override
    public void onFeedSuccess(List<Feed> feedList) {
        this.feedList = feedList;
        updateFeedList(feedList);
        dismissDialog();
    }

    public void updateFeedList(List<Feed> feedList) {
        FeedAdapter adapter = new FeedAdapter(feedList);
        rvFeed.setAdapter(adapter);
        showUp();
    }

    public void showUp() {
        dismissDialog();
        NextzyUtil.startAnimatorSet(this, rvFeed, R.animator.animator_alert_show_by_slide_up, null);
    }

    @Override
    public void onNavigationClick() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
