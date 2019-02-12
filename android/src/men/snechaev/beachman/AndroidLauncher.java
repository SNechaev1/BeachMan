package men.snechaev.beachman;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

public class AndroidLauncher extends FragmentActivity implements AndroidFragmentApplication.Callbacks  {
	//	String mLogTag = "logTag";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LauncherFragment())
                .commit();
    }


    public void applyFragment(Fragment fragment, int containerViewId, String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }

    public void setSettings(View view) {
        applyFragment(new SettingsFragment(), R.id.fragment_container, SettingsFragment.FRAGMENT_TAG);
    }

	public void play(View view) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		if (pref.getBoolean("screenOrientationLandscape", true)) {
			setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
        applyFragment(new GameFragment(), R.id.fragment_container, GameFragment.FRAGMENT_TAG);
	}

	@Override
	public void exit() {
	}

}

