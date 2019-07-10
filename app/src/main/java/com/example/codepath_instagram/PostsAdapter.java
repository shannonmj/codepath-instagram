package com.example.codepath_instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.codepath_instagram.model.Post;
import com.parse.ParseFile;

import java.util.List;

//extracting items from item_posts here
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    // will create one individual row in view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);
        return new ViewHolder(view);
    }

    // heres data at certain position and bind it
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
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHandle;
        private ImageView ivImage;
        private TextView tvDescription;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHandle = itemView.findViewById(R.id.tvHandle);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }

        public void bind(Post post) {
            // bind the view elements to the post
            tvHandle.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            // ensures there is an image
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            tvDescription.setText(post.getDescription());

        }
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
