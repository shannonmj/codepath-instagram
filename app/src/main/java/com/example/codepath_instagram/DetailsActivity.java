package com.example.codepath_instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.codepath_instagram.model.Post;

public class DetailsActivity extends AppCompatActivity {

    Post post;
    ImageView ivImage;
    TextView tvHandle;
    TextView tvDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


            // perform findViewById lookups
            ivImage = (ImageView) findViewById(R.id.ivImage);
            tvHandle = (TextView) findViewById(R.id.tvHandle);
            tvDescription = (TextView) findViewById(R.id.tvDescription);

            post = (Post) getIntent().getParcelableExtra(Post.class.getSimpleName());

            // set the title and overview
            tvHandle.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());

            //load image using glide
            Glide.with(this)
                    .load(post.getImage().getUrl())
                    .into(ivImage);

    }
}
