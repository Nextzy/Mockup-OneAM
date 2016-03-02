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

package com.nextzy.oneassetmanagement.network;

import com.nextzy.oneassetmanagement.network.model.CreditsFund;
import com.nextzy.oneassetmanagement.network.model.Feed;
import com.nextzy.oneassetmanagement.network.model.FundReport;
import com.nextzy.oneassetmanagement.network.model.PurchasedFund;
import com.nextzy.oneassetmanagement.network.model.TotalProfit;
import com.nextzy.oneassetmanagement.network.model.UserProfile;
import com.nextzy.oneassetmanagement.util.NextzyUtil;
import com.nextzy.oneassetmanagement.network.model.FundProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akexorcist on 1/7/2016 AD.
 */
public class NextzyService {

    public static void getFundList(final FundListResultCallback callback) {
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                if (callback != null) {
                    List<FundProduct> fundProductList = new ArrayList<>();
                    fundProductList.add(new FundProduct("ONE-CHINA", "One China Auto Redemption Fund", "FIF", "1 A.M.FUND", "[7]", "https://www.one-asset.com/wp-content/uploads/2012/08/ONE-CHINA_FS6.pdf", "9.3472", "15:00", "5"));
                    fundProductList.add(new FundProduct("ABAG", "Aberdeen American Growth Fund", "FIF EQ", "ABERDEEN", "[7]", "http://www.aberdeen-asset.co.th/doc.nsf/Lit/FactsheetThailandOpenABAG", "18.0787", "15:00", "5"));
                    fundProductList.add(new FundProduct("ASP", "Asset Plus Fixed Income Fund", "MMF", "ASP", "[1]", "http://www.assetfund.co.th/th/fundfact/TH-ASP.pdf", "15.8911", "15:00", "4.7"));
                    fundProductList.add(new FundProduct("B-INFRA", "Bualuang Infrastructure", "EQ", "BBLAM", "[8]", "http://www.bangkokbank.com/download/factsheet/B_INFRA_fs.pdf", "23.6384", "15:00", "4.9"));
                    fundProductList.add(new FundProduct("ABDWOOF", "Aberdeen World Opportunities Fund", "FIF EQ", "ABERDEEN", "[6]", "http://www.bangkokbank.com/download/factsheet/B_INFRA_fs.pdf", "11.739", "15:00", "3.2"));
                    callback.onFundListSuccess(fundProductList);
                }
            }
        });
    }

    public interface FundListResultCallback {
        void onFundListSuccess(List<FundProduct> fundProductList);
    }

    public static void getPurchasedFundList(final PurchasedFundListResultCallback callback) {
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                if (callback != null) {
                    List<PurchasedFund> purchasedFundList = new ArrayList<>();
                    purchasedFundList.add(new PurchasedFund("B-INFRA", "Bualuang Infrastructure", "EQ", "BBLAM", "[8]", "http://www.bangkokbank.com/download/factsheet/B_INFRA_fs.pdf", "23.6384", "15:00", "4.9", "15830"));
                    purchasedFundList.add(new PurchasedFund("ONE-CHINA", "ONE CHINA AUTO REDEMPTION FUND", "FIF", "1 A.M.FUND", "[7]", "https://www.one-asset.com/wp-content/uploads/2012/08/ONE-CHINA_FS6.pdf", "9.3472", "15:00", "5", "16500"));
                    purchasedFundList.add(new PurchasedFund("ABAG", "Aberdeen American Growth Fund", "FIF EQ", "ABERDEEN", "[7]", "http://www.aberdeen-asset.co.th/doc.nsf/Lit/FactsheetThailandOpenABAG", "18.0787", "15:00", "5", "2000"));
                    callback.onPurchasedFundListSuccess(purchasedFundList);
                }
            }
        });
    }

    public interface PurchasedFundListResultCallback {
        void onPurchasedFundListSuccess(List<PurchasedFund> purchasedFundList);
    }

    public static void getUserProfile(final UserProfileCallback callback) {
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                if (callback != null) {
                    UserProfile userProfile = new UserProfile("Andy Robin", "123-4-56789012-3");
                    callback.onUserProfileSuccess(userProfile);
                }
            }
        });
    }

    public interface UserProfileCallback {
        void onUserProfileSuccess(UserProfile userProfile);
    }

    public static void getCreditsFund(final CreditsFundCallback callback) {
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                if (callback != null) {
                    CreditsFund creditsFund = new CreditsFund("253040");
                    callback.onCreditsFundSuccess(creditsFund);
                }
            }
        });
    }

    public interface CreditsFundCallback {
        void onCreditsFundSuccess(CreditsFund creditsFund);
    }

    public static void getTotalProfit(final TotalProfitCallback callback) {
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                if (callback != null) {
                    TotalProfit totalProfit = new TotalProfit("15.90%");
                    callback.onCreditsFundSuccess(totalProfit);
                }
            }
        });
    }

    public interface TotalProfitCallback {
        void onCreditsFundSuccess(TotalProfit totalProfit);
    }

    public static void sell(final SellCallback callback) {
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                if (callback != null) {
                    callback.onSellSuccess();
                }
            }
        });
    }

    public interface SellCallback {
        void onSellSuccess();
    }

    public static void buy(final BuyCallback callback) {
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                if (callback != null) {
                    callback.onBuySuccess();
                }
            }
        });
    }

    public interface BuyCallback {
        void onBuySuccess();
    }

    public static void switching(final SwitchCallback callback) {
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                if (callback != null) {
                    callback.onSwitchSuccess();
                }
            }
        });
    }

    public interface SwitchCallback {
        void onSwitchSuccess();
    }

    public static void getFeedList(final FeedCallback callback) {
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                if (callback != null) {
                    List<Feed> feedList = new ArrayList<>();
                    feedList.add(new Feed("ข่าวดี! AIE เตรียมพร้อมกลับมาเทรด-หลังบอร์ดไฟเขียวแต่งตั้งผู้สอบบัญชีใหม่", "2016-01-20 14:21:00"));
                    feedList.add(new Feed("ตลาดฯ คาดการณ์ จีดีพี ไตรมาส 4 ของจีน เติบโตได้ 6.9% จะเป็น Sentiment เชิงบวกต่อหุ้นได้หรือไม่", "2016-01-18 20:55:13"));
                    feedList.add(new Feed("อีกแล้ว .. บลจ.เมย์แบงก์ ระบุลดน้ำหนักลงทุนหุ้น PTT", "2016-01-13 18:12:31"));
                    callback.onFeedSuccess(feedList);
                }
            }
        });
    }

    public interface FeedCallback {
        void onFeedSuccess(List<Feed> feedList);
    }

    public static void getFundReportList(final FundReportCallback callback) {
        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                if (callback != null) {
                    List<FundReport> fundReportList = new ArrayList<>();
                    fundReportList.add(new FundReport("AMATA", "AMATA Corporation Public Company Limited", 5, "#74dae8"));
                    fundReportList.add(new FundReport("MAJOR", "Major Cineplex Group Public Company Limited", 6, "#f37092"));
                    fundReportList.add(new FundReport("SANKO", "Sanko Diecasting (Thailand) Public Company Limited", 3, "#f39f70"));
                    fundReportList.add(new FundReport("TCAP", "Thanachart Capital Public Company Limited", 2, "#d88cee"));
                    fundReportList.add(new FundReport("UMS", "Unique Mining Services Public Company Limited", 2, "#fcf47d"));
                    fundReportList.add(new FundReport("KTB", "Krung Thai Bank Public Company Limited", 2, "#8ceeb5"));
                    callback.onFundReportSuccess(fundReportList);
                }
            }
        });
    }

    public interface FundReportCallback {
        void onFundReportSuccess(List<FundReport> feedList);
    }
}
