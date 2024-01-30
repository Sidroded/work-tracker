package com.sidroded.worktracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sidroded.worktracker.auth.LoginActivity;
import com.sidroded.worktracker.fragments.AnalyticsFragment;
import com.sidroded.worktracker.fragments.TasksFragment;
import com.sidroded.worktracker.fragments.TimerFragment;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
    BottomNavigationView bottomNavigationView;
    TasksFragment tasksFragment = new TasksFragment();
    AnalyticsFragment analyticsFragment = new AnalyticsFragment();
    TimerFragment timerFragment = new TimerFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        checkAuth();
        setBottomNavigationListener();
        setDefaultFragmentTimer();
}

    private void setBottomNavigationListener() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_timer);
        bottomNavigationView.setOnItemSelectedListener(navListener);
    }

    private void checkAuth() {
        if (currentUser != null) {
            currentUser.reload();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setDefaultFragmentTimer() {
        fTrans.add(R.id.flFragment, timerFragment);
        fTrans.commit();
    }

    private final BottomNavigationView.OnItemSelectedListener navListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.nav_task:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flFragment, tasksFragment)
                                .commit();
                        break;
                    case R.id.nav_analytics:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flFragment, analyticsFragment)
                                .commit();
                        break;
                    case R.id.nav_timer:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flFragment, timerFragment)
                                .commit();
                        break;
                }
                return true;
            };
}