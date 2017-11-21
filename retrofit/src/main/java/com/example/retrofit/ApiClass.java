package com.example.retrofit;

import java.util.List;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
/**
 * Created by aslan on 09.11.17.
 */

public interface ApiClass {

    @GET("posts")
    Call<List<Post>> getPostList();

    @GET ("photos/1")
    Call<Photo> getPhotoItem();

    @FormUrlEncoded
    @POST("posts")
    Call<User> updateUser(@Field("title") String first, @Field("author") String last);
}
