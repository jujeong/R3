package com.example.r3.view.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import com.example.r3.R;

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the listener for preference changes
        PreferenceManager.getDefaultSharedPreferences(requireContext())
                .registerOnSharedPreferenceChangeListener(this);

        // Load preferences from SharedPreferences and set them in the preferences UI components
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        preferenceScreen.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (preference instanceof CheckBoxPreference) {
                ((CheckBoxPreference) preference).setChecked(sharedPreferences.getBoolean(preference.getKey(), true));
            } else if (preference instanceof EditTextPreference) {
                ((EditTextPreference) preference).setText(sharedPreferences.getString(preference.getKey(), ""));
            }
            // Handle other types of preferences as needed
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(requireContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Handle preference changes here
        if (key.equals("enable_feature")) {
            // React to the change in the "enable_feature" preference
            boolean enabled = sharedPreferences.getBoolean(key, true); // Get the value
            // Save the value to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, enabled);
            editor.apply();
        } else if (key.equals("username")) {
            // React to the change in the "username" preference
            String username = sharedPreferences.getString(key, ""); // Get the value
            // Save the value to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, username);
            editor.apply();
        }
    }
}