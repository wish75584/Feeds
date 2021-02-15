package com.felixtechlabs.feeds.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.felixtechlabs.feeds.R;
import com.felixtechlabs.feeds.activities.MainActivity;
import com.felixtechlabs.feeds.api.APIClient;
import com.felixtechlabs.feeds.api.APIInterface;
import com.felixtechlabs.feeds.models.Post;
import com.felixtechlabs.feeds.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedsAdapter extends  RecyclerView.Adapter<FeedsAdapter.FeedsHolder>{

    List<Post> posts;

    Context context;

    public FeedsAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public FeedsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_feeds, viewGroup, false);

        context = viewGroup.getContext();

        return new FeedsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedsHolder feedsHolder, int i) {

        Post post = posts.get(i);

        getUserOfId(post.getUserId(), feedsHolder);

        feedsHolder.tvBody.setText(post.getBody());

        String url = "https://source.unsplash.com/random/" + i;

        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(feedsHolder.ivMediaImage);


        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(feedsHolder.ivUserImage);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class FeedsHolder extends RecyclerView.ViewHolder {

        TextView tvUserName;

        TextView tvEmail;

        TextView tvBody;

        ImageView ivMediaImage;

        ImageView ivUserImage;

        public FeedsHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvBody = itemView.findViewById(R.id.body);
            ivMediaImage = itemView.findViewById(R.id.mediaImage);
            ivUserImage = itemView.findViewById(R.id.userImage);
        }
    }


    void getUserOfId(int id, final FeedsHolder feedsHolder) {

        APIInterface apiInterface = APIClient.getRClient().create(APIInterface.class);

        Call<User> call = apiInterface.getUser(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                feedsHolder.tvUserName.setText(user.getName());

                feedsHolder.tvEmail.setText(user.getUsername());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
