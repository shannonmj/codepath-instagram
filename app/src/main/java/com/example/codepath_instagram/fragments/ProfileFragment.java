package com.example.codepath_instagram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.codepath_instagram.MainActivity;
import com.example.codepath_instagram.R;
import com.parse.ParseUser;

// inheriting all of the behaviors of PostsFragment
public class ProfileFragment extends PostsFragment {


    private Button btnLogOut;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        btnLogOut = view.findViewById(R.id.btnLogOut);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            btnLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseUser.logOut();
                    ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                    final Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

        }
    }














    /*@Override
    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        //when we get post back we'll also get the full details of the user
        postQuery.include(Post.KEY_USER);
        //I want a maximum of 20 posts back
        postQuery.setLimit(20);
        // only see posts by that user
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e("PostsFragment", "error with query");
                    e.printStackTrace();
                    return;
                }
                mPosts.addAll(posts);
                adapter.notifyDataSetChanged();


                for(int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d("PostsFragment", "POST: " + posts.get(i).getDescription() + ", username: " + post.getUser().getUsername());
                }
            }
        });
    }*/
}
