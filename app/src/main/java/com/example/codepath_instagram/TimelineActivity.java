package com.example.codepath_instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
    }

    /*BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_favorites:
                    // do something here
                    return true;
                case R.id.action_schedules:
                    // do something here
                    return true;
                case R.id.action_music:
                    // do something here
                    return true;
                default: return true;
            }
        }
    });*/
}
