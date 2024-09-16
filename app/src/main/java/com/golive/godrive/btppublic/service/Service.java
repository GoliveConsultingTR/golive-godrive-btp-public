package com.golive.godrive.btppublic.service;

import com.golive.godrive.btppublic.helper.BasicAuthInterceptor;
import com.golive.godrive.btppublic.helper.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {   public static Service mInstance ;
    public Retrofit retrofit ;
    private SessionPostListenerInterface listener;

    public static Service Instance(){

        if (mInstance == null) {

            mInstance = new Service();

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(Constant.httpUsername, Constant.httpPassword))
                    .connectTimeout(360, TimeUnit.SECONDS)
                    .writeTimeout(360, TimeUnit.SECONDS)
                    .readTimeout(360, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            mInstance.retrofit = retrofit;
            return mInstance;
        }

        return mInstance;

    }
}
