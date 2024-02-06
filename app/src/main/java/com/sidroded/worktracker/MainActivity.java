package com.sidroded.worktracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sidroded.worktracker.auth.LoginActivity;
import com.sidroded.worktracker.fragments.AnalyticsFragment;
import com.sidroded.worktracker.fragments.TasksFragment;
import com.sidroded.worktracker.fragments.TimerFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
    BottomNavigationView bottomNavigationView;
    TasksFragment tasksFragment = new TasksFragment();
    AnalyticsFragment analyticsFragment = new AnalyticsFragment();
    TimerFragment timerFragment = new TimerFragment();
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    TextView logOutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        logOutTextView = findViewById(R.id.logout_drawer_text_view);

        checkAuth();
        setBottomNavigationListener();
        setDefaultFragmentTimer();

        configureToolbar();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        setDrawerIconOnToolbar();
        setDrawerIconColor();

        setLogOutTextViewListener();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setLogOutTextViewListener() {
        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setElevation(0);
    }

    private void setDrawerIconColor() {
        if (isDarkTheme()) {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
        } else {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.black));
        }
    }

    private void setDrawerIconOnToolbar() {
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public Toolbar getToolbar() {
        return toolbar;
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

    private boolean isDarkTheme() {
        int nightModeFlags = getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
}