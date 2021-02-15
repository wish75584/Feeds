package com.felixtechlabs.feeds.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.felixtechlabs.feeds.R;
import com.felixtechlabs.feeds.adapters.FeedsAdapter;
import com.felixtechlabs.feeds.api.APIClient;
import com.felixtechlabs.feeds.api.APIInterface;
import com.felixtechlabs.feeds.fragments.FeedsFragment;
import com.felixtechlabs.feeds.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        apiInterface = APIClient.getRClient().create(APIInterface.class);


        //loading the default fragment
        loadFragment(new FeedsFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

       // fetchPosts();
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menus, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add) {

        }

        Toast.makeText(this, "Add tapped", Toast.LENGTH_LONG).show();

        Post post = new Post();
        post.setBody("Some Body");
        post.setTitle("Some Title");
        post.setUserId(1);

        Call<Post> postCall = apiInterface.addNewPost(post);

        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();

                Toast.makeText(MainActivity.this, post.getBody() + post.getTitle() + String.valueOf(post.getId()), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

        return super.onOptionsItemSelected(item);
    }

//    void fetchPosts() {
//
//        Call<List<Post>> call = apiInterface.getAllPosts();
//
//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//
//                List<Post> posts = response.body();
//
//                FeedsAdapter feedsAdapter = new FeedsAdapter(posts);
//
//                RecyclerView recyclerView = findViewById(R.id.rv_feeds);
//
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//
//                recyclerView.setAdapter(feedsAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//
//            }
//        });
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new FeedsFragment();
                break;

            case R.id.navigation_dashboard:
                fragment = new FeedsFragment();
                break;

            case R.id.navigation_notifications:
                fragment = new FeedsFragment();
                break;

            case R.id.navigation_profile:
                fragment = new FeedsFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
