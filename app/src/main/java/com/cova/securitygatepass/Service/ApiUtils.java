package com.cova.securitygatepass.Service;

import static com.cova.securitygatepass.Config.Constant.BASE_URL;

import android.content.Context;

public class ApiUtils {
    private ApiUtils() {
    }

    public static ApiService getApiService(Context context) {
        return RetrofitClient.getClient(BASE_URL, context).create(ApiService.class);
    }
}
