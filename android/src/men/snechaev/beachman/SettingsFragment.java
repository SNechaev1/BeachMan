package men.snechaev.beachman;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String FRAGMENT_TAG = "SettingsTag";

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

}


