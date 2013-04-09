package quintec.tenis;

import java.util.Locale;

import quintec.tenis.math.CalculateBallsChange.BallsChangeMode;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class LocaleActivity extends FragmentActivity {
	
	public static String playerSeparator = " & ";
	private static final String PREFERENCES = "pref";
	private static final int PREFERENCES_MODE = 0;
	private static final String LOCALE_SHARED_PREF_KEY = "LOCALE_SHARED_PREF_KEY";
	private static final String BALL_CHANGE = "BALL_CHANGE";
	public static final String SLOVAK_LOCALE = "sk";
	public static final String ENGLISH_LOCALE = "en";
	
	private SharedPreferences.Editor prefEditor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Locale locale = new Locale(getAplicationLocale());
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config, 
				getBaseContext().getResources().getDisplayMetrics());
	}
	
	public String getAplicationLocale() {
		SharedPreferences dataStore = getSharedPreferences(PREFERENCES, PREFERENCES_MODE);
		String locale = dataStore.getString(LOCALE_SHARED_PREF_KEY, ENGLISH_LOCALE);
		if (locale != null) {
			return locale;
		}
		else {
			return ENGLISH_LOCALE;
		}
	}
	
	public void setAplicationLocale(String locale){
		SharedPreferences dataStore = getSharedPreferences(PREFERENCES, PREFERENCES_MODE);
		prefEditor = dataStore.edit();
		prefEditor.putString(LOCALE_SHARED_PREF_KEY, locale);
	}
	
	public BallsChangeMode getBallsChangeMode() {
		SharedPreferences dataStore = getSharedPreferences(PREFERENCES, PREFERENCES_MODE);
		int ballChangeValue = dataStore.getInt(BALL_CHANGE, 1);
		BallsChangeMode ballChangeMod = BallsChangeMode.getBallChangeMod(ballChangeValue);
		return ballChangeMod;
	}
	
	public void setBallsChangeMode(BallsChangeMode mode) {
		SharedPreferences dataStore = getSharedPreferences(PREFERENCES, PREFERENCES_MODE);
		prefEditor = dataStore.edit();
		prefEditor.putInt(BALL_CHANGE, mode.getSaveValue());
		prefEditor.commit();
	}
	
	public void commitLocaleChanges() {
		prefEditor.commit();
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		        return true;
		    }
		    return false;
	}
}
