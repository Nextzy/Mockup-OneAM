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

package com.nextzy.oneassetmanagement.common;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nextzy.oneassetmanagement.R;

/**
 * Created by Akexorcist on 1/7/2016 AD.
 */
public class BaseToolbarActivity extends BaseActivity {
    private Toolbar tbTitle;
    private TextView tvTitle;
    private ImageButton btnNavigation;

    public void initToolbar() {
        tbTitle = (Toolbar) findViewById(R.id.tb_title);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        btnNavigation = (ImageButton) findViewById(R.id.btn_navigation);
        if (btnNavigation != null) {
            btnNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
        }
    }

    public void setToolbarTitle(String title) {
        if (tbTitle == null) {
            throw new NullPointerException("Should have toolbar");
        } else {
            tvTitle.setText(title);
        }
    }

    public Toolbar getToolbar() {
        return tbTitle;
    }

    public void onNavigationClick() {
    }

    public void setBackNavigation() {
        if (btnNavigation != null) {
            btnNavigation.setImageResource(R.mipmap.ic_back);
        }
    }

    public void setMenuNavigation() {
        if (btnNavigation != null) {
            btnNavigation.setImageResource(R.mipmap.ic_menu);
        }
    }

    public void hideMenuNavigation() {
        if (btnNavigation != null) {
            btnNavigation.setVisibility(View.GONE);
        }
    }
}
