package dfzy.cookie.bell;

import dfzy.cookie.bell.R;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class lingCHConfigPreference extends PreferenceActivity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
//		PreferenceManager manager=getPreferenceManager();
//		ListPreference listPreference=(ListPreference)manager.findPreference("interval_config");
	}
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,  
	        Preference preference) {  
	    return false;  
	} 
}
