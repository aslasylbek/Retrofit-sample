package com.example.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aslan on 09.11.17.
 */

public class RestClient {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static final String POST_URL = "http://d19b3f67.ngrok.io/";

    public static Retrofit retrofit  = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Retrofit retrofit1 = new Retrofit.Builder().baseUrl(POST_URL).addConverterFactory(GsonConverterFactory.create()).build();
    public static ApiClass request(){
        return retrofit.create(ApiClass.class);
    }

    public static ApiClass postRequest(){
        return  retrofit1.create(ApiClass.class);
    }


}
