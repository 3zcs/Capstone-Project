package com.me.azcs.reviewbooks;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by azcs on 31/01/17.
 */

public class BookReviewApp extends Application {
    public static final String ENDPOINT = "https://www.googleapis.com/" ;
    public static final String KEY = "AIzaSyCRccgaXFNhX8upKKW5tTLneeiWLUbl2Lk";
    public static Retrofit retrofit = null ;
    public static OkHttpClient okHttpClient = null ;

    public static boolean NetworkState(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return info != null
                && info.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static Retrofit getClient(){
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create().createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        return retrofit ;
    }

    public static OkHttpClient getOkHttpClient(){
        if (okHttpClient == null)
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

        return okHttpClient ;
    }


    public static void msg (Context context , String s){
        Toast.makeText(context , s , Toast.LENGTH_SHORT).show();
    }
}
