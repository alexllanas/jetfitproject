package com.alexllanas.jefitproject.ui;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;

import com.alexllanas.jefitproject.R;
import com.google.android.material.appbar.MaterialToolbar;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    public NavController navController;
    private MaterialToolbar toolbar;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.populateDatabase();
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.app_tool_bar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.title_cities);
            setSupportActionBar(toolbar);
        }
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    /**
     * Setup navigation icon in app bar depending on current fragment.
     *
     * @param setNavigationIcon Whether to set or unset navigation icon in toolbar
     */
    public void setBackNavigationIcon(boolean setNavigationIcon) {
        if (setNavigationIcon) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(v -> {
                onBackPressed();
            });
        } else {
            toolbar.setNavigationIcon(null);
        }
    }
}