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

package com.nextzy.oneassetmanagement.network.model;

import org.parceler.Parcel;

/**
 * Created by Akexorcist on 1/7/2016 AD.
 */

@Parcel
public class PurchasedFund {
    String symbol;
    String name;
    String assetType;
    String assetFirm;
    String riskRating;
    String factSheetLink;
    String navLatest;
    String cutOffTime;
    String starRating;
    String inPortAmount;

    public PurchasedFund() {
    }

    public PurchasedFund(String symbol, String name, String assetType, String assetFirm, String riskRating, String factSheetLink, String navLatest, String cutOffTime, String starRating, String inPortAmount) {
        this.symbol = symbol;
        this.name = name;
        this.assetType = assetType;
        this.assetFirm = assetFirm;
        this.riskRating = riskRating;
        this.factSheetLink = factSheetLink;
        this.navLatest = navLatest;
        this.cutOffTime = cutOffTime;
        this.starRating = starRating;
        this.inPortAmount = inPortAmount;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getAssetFirm() {
        return assetFirm;
    }

    public String getRiskRating() {
        return riskRating;
    }

    public String getFactSheetLink() {
        return factSheetLink;
    }

    public String getNavLatest() {
        return navLatest;
    }

    public String getCutOffTime() {
        return cutOffTime;
    }

    public String getStarRating() {
        return starRating;
    }

    public String getInPortAmount() {
        return inPortAmount;
    }
}
