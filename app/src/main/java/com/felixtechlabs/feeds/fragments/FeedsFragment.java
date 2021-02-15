package com.felixtechlabs.feeds.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.felixtechlabs.feeds.R;
import com.felixtechlabs.feeds.activities.MainActivity;
import com.felixtechlabs.feeds.adapters.FeedsAdapter;
import com.felixtechlabs.feeds.api.APIClient;
import com.felixtechlabs.feeds.api.APIInterface;
import com.felixtechlabs.feeds.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View v = inflater.inflate(R.layout.fragment_feeds, null);
        fetchPosts(v);
        return v;
    }


    void fetchPosts(final View view) {

        APIInterface  apiInterface = APIClient.getRClient().create(APIInterface.class);
        Call<List<Post>> call = apiInterface.getAllPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                List<Post> posts = response.body();

                FeedsAdapter feedsAdapter = new FeedsAdapter(posts);

                RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                recyclerView.setAdapter(feedsAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }}
