package com.example.codepath_instagram.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codepath_instagram.EndlessRecyclerViewScrollListener;
import com.example.codepath_instagram.PostsAdapter;
import com.example.codepath_instagram.R;
import com.example.codepath_instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    Context context;
    public RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> mPosts;
    public SwipeRefreshLayout swipeContainer;
    public int whichFragment;

    // Store a member variable for the listener
    public EndlessRecyclerViewScrollListener scrollListener;
    long maxId = 0;


// onCreateView to inflate the view

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_posts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = view.findViewById(R.id.rvPostsView);

        // create the data source
        mPosts = new ArrayList<>();

        // create the adapter
        //adapter = new PostsAdapter(getContext(), mPosts);
        // set the adapter on the recycler view
        //rvPosts.setAdapter(adapter);
        // set the layout manager on the recycler view
        //rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        setRecyclerView(view);


        // TODO add progress bar after posting tweet
        // on some click or some loading we need to wait for...
        /* ProgressBar pb = view.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);
        // run a background job and once complete
        pb.setVisibility(ProgressBar.INVISIBLE); */
    }

    protected void setRecyclerView(View view) {
        whichFragment = 0;

        adapter = new PostsAdapter(getContext(), mPosts, whichFragment);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        //create endless recycling view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);


        queryPosts();

        //refresh extended activity
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };

        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);
        // RecyclerView setup (layout manager, use adapter)
        rvPosts.setLayoutManager(linearLayoutManager);
        // set the adapter
        rvPosts.setAdapter(adapter);
        queryPosts();
    }

    // TODO for endless scroll, check
    public void loadNextDataFromApi(int offset) {
        queryPosts();
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        // Remember to CLEAR OUT old items before appending in the new ones
        adapter.clear();
        // ...the data has come back, add new items to your adapter...
        //adapter.addAll(...);
        queryPosts();
        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);
    }

    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        //when we get post back we'll also get the full details of the user
        postQuery.include(Post.KEY_USER);
        //I want a maximum of 20 posts back
        postQuery.setLimit(20);
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


                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d("PostsFragment", "POST: " + posts.get(i).getDescription() + ", username: " + post.getUser().getUsername());
                }
            }
        });
    }

}
