package com.example.codepath_instagram.fragments;

import android.util.Log;

import com.example.codepath_instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

// inheriting all of the behaviors of PostsFragment
public class ProfileFragment extends PostsFragment {

    @Override
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
    }
}
