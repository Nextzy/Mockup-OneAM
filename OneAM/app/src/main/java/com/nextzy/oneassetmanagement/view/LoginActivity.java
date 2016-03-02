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

package com.nextzy.oneassetmanagement.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nextzy.oneassetmanagement.R;
import com.nextzy.oneassetmanagement.common.BaseActivity;
import com.nextzy.oneassetmanagement.util.NextzyUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivLogo;
    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnForgetPassword;
    private LinearLayout layoutLoginContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prepareView();
        serveData();
        startIntroAnimation();
    }

    public void prepareView() {
        ivLogo = (ImageView) findViewById(R.id.login_iv_logo);
        btnSignIn = (Button) findViewById(R.id.login_btn_sign_in);
        btnSignUp = (Button) findViewById(R.id.login_btn_sign_up);
        btnForgetPassword = (Button) findViewById(R.id.login_btn_forget_password);
        layoutLoginContainer = (LinearLayout) findViewById(R.id.login_layout_login_container);
    }

    public void serveData() {
        layoutLoginContainer.setVisibility(View.GONE);
        ivLogo.setVisibility(View.GONE);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);
    }

    public void startIntroAnimation() {
        NextzyUtil.startAnimator(this, ivLogo, R.animator.animator_login_logo_initial_position, null);
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                NextzyUtil.startAnimator(LoginActivity.this, ivLogo, R.animator.animator_login_logo_slide_up, new NextzyUtil.AnimateFinishCallback() {
                    @Override
                    public void onAnimateFinished() {
                        NextzyUtil.startAnimatorSet(LoginActivity.this, layoutLoginContainer, R.animator.animator_content_show_by_slide_up, null);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnSignIn) {
            login();
        } else if (v == btnSignUp) {
            showMessage(getString(R.string.login_sign_up_is_unavailable_now));
        } else if (v == btnForgetPassword) {
            showMessage(getString(R.string.login_forgot_password_is_unavailable_now));
        }
    }

    public void login() {
        showLoadingDialog();
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                dismissDialog();
                openActivity(TermAgreementActivity.class, true);
            }
        });
    }
}
