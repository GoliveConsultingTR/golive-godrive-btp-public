package com.golive.godrive.btppublic.service;


import com.kaopiz.kprogresshud.KProgressHUD;

public interface SessionPostListenerInterface {
    void onBaseServiceCompleted(String methodName, String token, String cookie, KProgressHUD hud);
}
