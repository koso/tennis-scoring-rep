package quintec.tenis.activities;

import quintec.tenis.LocaleActivity;
import quintec.tenis.R;
import quintec.tenis.database.PostgreSqlConnection;
import quintec.tenis.math.CalculateBallsChange.BallsChangeMode;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class LanguageActivity extends LocaleActivity{

	private Spinner spinner;
	private Button saveButton;
	private EditText hostET, portET, dbET, userET, passET; 
	private RadioGroup ballsChangeGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.language_activity);
		referenceViews();
		setEdittextValues();
		setSpinnerAdapter();
		setSpinnerListener();
		setSaveClickListener();
	}
	
	private void referenceViews(){
		spinner = (Spinner) findViewById(R.id.language_spinner);
		saveButton = (Button) findViewById(R.id.languageSaveButton);
		hostET = (EditText) findViewById(R.id.settings_hostET);
		portET = (EditText) findViewById(R.id.settings_portET);
		dbET = (EditText) findViewById(R.id.settings_dbET);
		userET = (EditText) findViewById(R.id.settings_userET);
		passET = (EditText) findViewById(R.id.settings_passET);
		ballsChangeGroup = (RadioGroup) findViewById(R.id.settings__ballsChange_radioGroup);
		
		BallsChangeMode ballsChangeMode = getBallsChangeMode();
		if (ballsChangeMode == BallsChangeMode.SEVEN_NINE) 
			ballsChangeGroup.check(R.id.settings__radio_small);
		else 
			ballsChangeGroup.check(R.id.settings__radio_big);
	}
	
	private void setEdittextValues(){
		SharedPreferences dataStore = getSharedPreferences("pref", 0);
		String host = dataStore.getString(PostgreSqlConnection.HOST_KEY, "webdev.qintec.sk");
		String port = dataStore.getString(PostgreSqlConnection.PORT_KEY, "5432");
		String db = dataStore.getString(PostgreSqlConnection.DB_NAME_KEY, "tenis");
		String user = dataStore.getString(PostgreSqlConnection.USER_KEY, "tenista");
		String pass = dataStore.getString(PostgreSqlConnection.PASS_KEY, "t3n1st4");
		hostET.setText(host);
		portET.setText(port);
		dbET.setText(db);
		userET.setText(user);
		passET.setText(pass);
		hostET.setSelection(hostET.getText().length());
	}
	
	private void saveDBValues(){
		SharedPreferences dataStore = getSharedPreferences("pref", 0);
		SharedPreferences.Editor prefEditor = dataStore.edit();
		prefEditor.putString(PostgreSqlConnection.HOST_KEY, hostET.getText().toString());
		prefEditor.putString(PostgreSqlConnection.PORT_KEY, portET.getText().toString());
		prefEditor.putString(PostgreSqlConnection.DB_NAME_KEY, dbET.getText().toString());
		prefEditor.putString(PostgreSqlConnection.USER_KEY, userET.getText().toString());
		prefEditor.putString(PostgreSqlConnection.PASS_KEY, passET.getText().toString());
		
		prefEditor.commit();
	}
	
	private void setSpinnerAdapter(){
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.languages,R.layout.spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	
	private void setSpinnerListener(){
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					                   int pos, long arg3) {
				
				switch (pos) {
				case 0:
					setAplicationLocale(LocaleActivity.SLOVAK_LOCALE);
					break;

                case 1:
                	setAplicationLocale(LocaleActivity.ENGLISH_LOCALE);
					break;
				
                default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { }
		});
	}
	
	private void setSaveClickListener() {
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveBallsChanges();
				saveDBValues();
				commitLocaleChanges();
				Toast.makeText(getApplicationContext(), R.string.restartApp, Toast.LENGTH_LONG).show();
				finish();
			}
		});
	}
	
	private void saveBallsChanges() {
		BallsChangeMode mode;
		int checkedRadioButtonId = ballsChangeGroup.getCheckedRadioButtonId();
		if (checkedRadioButtonId == R.id.settings__radio_small) {
			mode = BallsChangeMode.SEVEN_NINE;
		}
		else {
			mode = BallsChangeMode.NINE_ELEVEN;
		}
		
		setBallsChangeMode(mode);
	}
}
