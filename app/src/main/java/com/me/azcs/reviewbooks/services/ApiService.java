package com.me.azcs.reviewbooks.services;

import com.me.azcs.reviewbooks.models.Item;
import com.me.azcs.reviewbooks.models.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by azcs on 31/01/17.
 */

public interface ApiService {

    @GET("books/v1/volumes")
    Observable<Response> getBookDetaiels(@Query("q") String searchWords);

    @GET("books/v1/volumes")
    Call<Response> getBookDetaielsr(@Query("q") String searchWords);


    @GET("books/v1/volumes/{id}")
    Observable<Item> getItemDetails(@Path("id") String id);

    @GET("books/v1/volumes/{id}")
    Call<Item>getItemDetailsr(@Path("id") String id);
}
