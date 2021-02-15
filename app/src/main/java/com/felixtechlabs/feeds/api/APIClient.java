package com.felixtechlabs.feeds.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static String BASE_URL = "https://jsonplaceholder.typicode.com";

    private static Retrofit retrofit = null;

    public static Retrofit getRClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
