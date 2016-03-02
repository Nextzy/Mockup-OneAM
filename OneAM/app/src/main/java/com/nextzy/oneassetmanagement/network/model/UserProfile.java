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
public class UserProfile {
    String name;
    String clientNumber;

    public UserProfile() {
    }

    public UserProfile(String name, String clientNumber) {
        this.name = name;
        this.clientNumber = clientNumber;
    }

    public String getName() {
        return name;
    }

    public String getClientNumber() {
        return clientNumber;
    }
}
