package com.example.r3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.r3.view.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            loadSettingsFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadSettingsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the SettingsFragment
        transaction.replace(R.id.fragmentContainer, new SettingsFragment());

        // Add to back stack (optional)
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}