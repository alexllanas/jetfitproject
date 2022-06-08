package com.alexllanas.jefitproject.ui;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alexllanas.jefitproject.R;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.app_tool_bar);
        if (toolbar != null) {
            setToolbarTitle(R.string.title_cities);
            setSupportActionBar(toolbar);
        }
    }

    public void setToolbarTitle(@StringRes int title) {
        toolbar.setTitle(title);
    }

    /**
     * Set the navigation icon in app bar depending on current fragment.
     *
     * @param setNavigationIcon Whether to set or unset navigation icon in toolbar
     */
    public void setBackNavigationIcon(boolean setNavigationIcon) {
        if (setNavigationIcon) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        } else {
            toolbar.setNavigationIcon(null);
        }
    }
}