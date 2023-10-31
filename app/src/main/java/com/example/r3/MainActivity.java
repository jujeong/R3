package com.example.r3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.activity.OnBackPressedCallback;  // Import the OnBackPressedCallback
import com.example.r3.view.alarm.AlarmFragment;
import com.example.r3.view.map.MapFragment;
import com.example.r3.view.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private boolean isSettingsFragmentVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set a default fragment when the activity is created
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new AlarmFragment()) // Set the default fragment
                    .commit();
        }

        // Set up BottomNavigationView item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.action_alarm_fragment:
                    selectedFragment = new AlarmFragment();
                    break;
                case R.id.action_map_fragment:
                    selectedFragment = new MapFragment();
                    break;
            }
            // Load the selected fragment
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            } else {
                return false;
            }
        });

        // Handle back button press
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isSettingsFragmentVisible) {
                    // If SettingsFragment is visible, show the BottomNavigationView and go back to the previous fragment
                    loadFragment(isSettingsFragmentVisible ? new MapFragment() : new AlarmFragment());
                    isSettingsFragmentVisible = false;
                } else {
                    // If a different fragment is visible, handle back navigation as usual
                    onBackPressed();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            // Load the SettingsFragment when the "setting" item is selected.
            loadFragment(new SettingsFragment());
            isSettingsFragmentVisible = true;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the specified fragment
        transaction.replace(R.id.fragmentContainer, fragment);

        // Add to the back stack (optional)
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        // Show or hide the BottomNavigationView based on the fragment
        if (fragment instanceof SettingsFragment) {
            bottomNavigationView.setVisibility(View.GONE);
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }
}
