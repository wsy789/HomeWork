package com.wsy.android_js;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtl {
        //判断是否在有网环境下
    public static boolean isConnected(Context applicationContext) {
        boolean isConnected = false;//是否有网
        //要加权限
        ConnectivityManager manager =(ConnectivityManager)
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        //判断网络状态
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        //有网环境                          连接网络的/网络存活的
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            isConnected = true;
        }
        return isConnected;
    }
}