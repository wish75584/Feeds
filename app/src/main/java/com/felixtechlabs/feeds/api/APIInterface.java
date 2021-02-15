package com.felixtechlabs.feeds.api;

import com.felixtechlabs.feeds.models.Post;
import com.felixtechlabs.feeds.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("posts/")
    Call<List<Post>> getAllPosts();

    @GET("users/{id}")
    Call<User> getUser(@Path("id") int id);

    @POST("posts/")
    Call<Post> addNewPost(@Body Post post);
}
