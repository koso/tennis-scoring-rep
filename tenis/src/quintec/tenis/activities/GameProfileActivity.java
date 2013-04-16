package quintec.tenis.activities;

import quintec.tenis.LocaleActivity;
import quintec.tenis.R;
import quintec.tenis.Score.GAME_PROFILE;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class GameProfileActivity extends LocaleActivity {

	public static final String GAME_PROFILE_EXTRASS_KEY = "GAME_PROFILE_EXTRASS_KEY";
	public static final String INTENT_EXTRAS_KEY = "INTENT_EXTRAS_KEY";
	public static final int ACTIVITY_FOR_RESULT = 1;
	private String pl1id, pl2id, pl12id, pl22id;
	private int matchID;
	private boolean matchIsSingle;
	
	private RadioGroup profilesRadioGroup;
	private Button startMatchButon;
	private TextView pl1NameTV, pl2NameTV, team1TV, team2TV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		pl1id = intent.getStringExtra(TenisActivity.PLAYER_ONE_TEAM_1_NAME_BUNDLE);
		pl2id = intent.getStringExtra(TenisActivity.PLAYER_ONE_TEAM_2_NAME_BUNDLE);
		matchID = intent.getIntExtra(TenisActivity.MATCH_ID_BUNDLE, 0);
		matchIsSingle = intent.getBooleanExtra(TenisActivity.MATCH_IS_SINGLE_BUNDLE,true);
		if (!matchIsSingle) {
			pl12id = intent.getStringExtra(TenisActivity.PLAYER_TWO_TEAM_1_NAME_BUNDLE);
			pl22id = intent.getStringExtra(TenisActivity.PLAYER_TWO_TEAM_2_NAME_BUNDLE);
		}
		setContentView(R.layout.game_profile_activity);
		referenceViews();
		setTextviews();
		setOnStartMatchListener();
	}
	
	private void referenceViews(){
		profilesRadioGroup = (RadioGroup) findViewById(R.id.profileRadioGroup);
	    startMatchButon = (Button) findViewById(R.id.gameProfileStartMatchButton);
	    pl1NameTV = (TextView) findViewById(R.id.profile_pl1Name);
	    pl2NameTV = (TextView) findViewById(R.id.profile_pl2Name);
	    team1TV = (TextView) findViewById(R.id.profile_Team1);
	    team2TV = (TextView) findViewById(R.id.profile_Team2);
	}
	
	private void setTextviews(){
		String pl1Name = pl1id;
		String pl2Name = pl2id;
		if (!matchIsSingle) {
			pl1Name = pl1id + "  " + playerSeparator + "  " + 
		              pl12id;
			pl2Name = pl2id+ "  " + playerSeparator + "  " + 
		              pl22id;
			team2TV.setText(R.string.team_two);
			team1TV.setText(R.string.team_one);
		}
		else {
			team1TV.setText(R.string.player_one);
			team2TV.setText(R.string.player_two);
		}
		pl1NameTV.setText(pl1Name);
		pl2NameTV.setText(pl2Name);
	}
	
	private void setOnStartMatchListener(){
		startMatchButon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int gameProfile;
				
				switch (profilesRadioGroup.getCheckedRadioButtonId()) {
				
				case R.id.profile_2sets_without_tie:
					gameProfile = GAME_PROFILE.TWO_SETS_WITHOUT_TIE;
					break;
               case R.id.profile_3sets_without_tie:
					gameProfile = GAME_PROFILE.THREE_SETS_WITHOUT_TIE;
					break;
				case R.id.profile_2sets_with_tie:
					gameProfile = GAME_PROFILE.TWO_SETS_WITH_TIE;
					break;
				case R.id.profile_3sets_with_tie:
					gameProfile = GAME_PROFILE.THREE_SETS_WITH_TIE;
					break;
				case R.id.profile_2sets_with_super_tie:
					gameProfile = GAME_PROFILE.TWO_SETS_WITH_SUPER_TIE;
					break;
                case R.id.profile_3sets_with_super_tie:
					gameProfile = GAME_PROFILE.THREE_SETS_WITH_SUPER_TIE;
					break;

				default:
					gameProfile = GAME_PROFILE.TWO_SETS_WITHOUT_TIE;
					break;
				}
				
				Intent intent = new Intent(getApplicationContext(), CounterActivity.class);
				intent.putExtra(GAME_PROFILE_EXTRASS_KEY, gameProfile);
				intent.putExtra(TenisActivity.PLAYER_ONE_TEAM_1_NAME_BUNDLE, pl1id);
				intent.putExtra(TenisActivity.PLAYER_ONE_TEAM_2_NAME_BUNDLE, pl2id);
				if (!matchIsSingle) {
					intent.putExtra(TenisActivity.PLAYER_TWO_TEAM_1_NAME_BUNDLE, pl12id);
					intent.putExtra(TenisActivity.PLAYER_TWO_TEAM_2_NAME_BUNDLE, pl22id);
					intent.putExtra(TenisActivity.MATCH_IS_SINGLE_BUNDLE, false);
				}
				else {
					intent.putExtra(TenisActivity.MATCH_IS_SINGLE_BUNDLE, true);
				}
				intent.putExtra(TenisActivity.MATCH_ID_BUNDLE, matchID);
				startActivityForResult(intent, ACTIVITY_FOR_RESULT);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ACTIVITY_FOR_RESULT && resultCode == RESULT_OK) {
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.default_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.InfoMenuItem:
			Intent infoIntent = new Intent(getApplicationContext(), InfoActivity.class);
			startActivity(infoIntent);
			return true;

		case R.id.languageMenuItem:
			Intent settingsIntent = new Intent(getApplicationContext(), LanguageActivity.class);
			startActivity(settingsIntent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
