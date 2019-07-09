package com.example.codepath_instagram;

import android.app.Application;

import com.example.codepath_instagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

// sets up Parse server
public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("shannonjordan")
                .clientKey("31312310")
                .server("http://shannonmj-fbu-instagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);

    }
}
