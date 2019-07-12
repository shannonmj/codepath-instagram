package com.example.codepath_instagram;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.codepath_instagram.model.Post;
import com.parse.ParseFile;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

//extracting items from item_posts here
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    public int whichFragment;


    public PostsAdapter(Context context, List<Post> posts, int whichFragment) {
        this.context = context;
        this.posts = posts;
        this.whichFragment = whichFragment;
    }

    @NonNull
    @Override
    // will create one individual row in view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);
        return new ViewHolder(view);
    }

    // heres data at certain position and bind it
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        viewHolder.bind(post);
    }

    // returns how many items are in data center
    @Override
    public int getItemCount() {
        return posts.size();
    }


    // first because want post adapter to extend recycler view
    // parametrized by view holder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvHandle;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvTime;
        public ImageView ivProfileImage;
        public ImageView ivHeart;
        public ImageView ivChat;
        public ImageView ivPlane;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHandle = itemView.findViewById(R.id.tvHandle);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ivHeart = itemView.findViewById(R.id.ivHeart);
            ivChat = itemView.findViewById(R.id.ivChat);
            ivPlane = itemView.findViewById(R.id.ivPlane);
            tvTime = itemView.findViewById(R.id.tvTime);


            itemView.setOnClickListener(this);

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(Post post) {
            // bind the view elements to the post
            tvHandle.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (whichFragment == 0) {
                // ensures there is an image
                if (image != null) {
                    Glide.with(context).load(image.getUrl()).into(ivImage);
                }
                tvDescription.setText(post.getDescription());
                tvTime.setText((getRelativeTimeAgo(post.getCreatedAt().toString())));
            } else if (whichFragment == 1) {

                tvHandle.setVisibility(View.GONE);
                tvDescription.setVisibility(View.GONE);
                ivProfileImage.setVisibility(View.GONE);
                ivHeart.setVisibility(View.GONE);
                ivChat.setVisibility(View.GONE);
                ivPlane.setVisibility(View.GONE);
                tvTime.setVisibility(View.GONE);

                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                int pxWidth = displayMetrics.widthPixels;

                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(pxWidth / 3, pxWidth / 3);
                ivImage.setLayoutParams(layoutParams);
                if (image != null) {
                    Glide.with(context).load(image.getUrl()).into(ivImage);
                }
            }
        }

        // to click tweet and see detailview
        @Override
        public void onClick(View v) {
            //gets item position
            int position = getAdapterPosition();
            //make sure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                //get the tweet at the position
                Post post = posts.get(position);
                //create intent for the new activity
                Intent intent = new Intent(context, DetailsActivity.class);
                //serialize the tweet using the parceler,
                intent.putExtra(Post.class.getSimpleName(), post);
                //show activity
                context.startActivity(intent);
            }
        }

    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // for swipe refresh layout
    //updates RecyclerView.Adapter
    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

}
