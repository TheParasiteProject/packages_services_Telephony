/*
 * Copyright (C) 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.phone.testapps.satellitetestapp;

import android.net.Network;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


class PingTask{
    private static final String PING_TARGET_HOST = "www.google.com";
    private static final int PING_TIMEOUT_SECONDS = 20;

    public String ping(Network network) {
        URL url = null;
        try {
            url = new URL("http://www.google.com");
        } catch (Exception e) {
            Log.d("PingTask", "exception: " + e);
        }
        if (url != null) {
            try {
                Log.d("PingTask", "ping " + url);
                String result = httpGet(network, url);
                Log.d("PingTask", "Ping Success");
                return result;
            } catch (Exception e) {
                Log.d("PingTask", "exception: " + e);
            }
        }
        return null;

    }

    /**
     * Performs a HTTP GET to the specified URL on the specified Network, and returns the response
     * body decoded as UTF-8.
     */
    private static String httpGet(Network network, URL httpUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) network.openConnection(httpUrl);
        try {
            InputStream inputStream = connection.getInputStream();
            Log.d("httpGet", "httpUrl + " + httpUrl);
            return "Ping Success " + httpUrl;
        } finally {
            connection.disconnect();
        }
    }

    public Integer pingIcmp() {
        String command = "/system/bin/ping -c 1 -W " + PING_TIMEOUT_SECONDS + " "
                + PING_TARGET_HOST;
        try {
            Log.d("PingTask", "ping " + command);
            int result = Runtime.getRuntime().exec(command).waitFor();
            Log.d("PingTask", "Ping Success");
            return result;
        } catch (Exception e) {
            Log.d("PingTask", "exception: " + e);
        }

        return null;
    }

}
