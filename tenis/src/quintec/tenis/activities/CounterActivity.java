package quintec.tenis.activities;

import java.util.ArrayList;

import quintec.tenis.LocaleActivity;
import quintec.tenis.Log;
import quintec.tenis.Match;
import quintec.tenis.Match.STATE;
import quintec.tenis.R;
import quintec.tenis.Score;
import quintec.tenis.Score.GAME_PROFILE;
import quintec.tenis.database.LocalDbInsert;
import quintec.tenis.database.LocalDbQuery;
import quintec.tenis.database.Qscore;
import quintec.tenis.dialogs.BallsChangeDialog;
import quintec.tenis.dialogs.EndMatchDialog;
import quintec.tenis.dialogs.EndMatchOnBackPressedDIalog;
import quintec.tenis.dialogs.ServiceDialog;
import quintec.tenis.dialogs.TeamSidePickerDialog;
import quintec.tenis.math.BrainMath;
import quintec.tenis.math.CalculateBallsChange;
import quintec.tenis.math.CalculateBallsChange.BallsChange;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CounterActivity extends LocaleActivity {
	
	public static final String SCORE_BUNDLE = "SCORE_BUNDLE";
	public static final String PLAYER_ONE_TEAM_1_NAME_BUNDLE = "PLAYER_ONE_TEAM_1_NAME_BUNDLE";
	public static final String PLAYER_TWO_TEAM_1_NAME_BUNDLE = "PLAYER_TWO_TEAM_1_NAME_BUNDLE";
	public static final String PLAYER_ONE_TEAM_2_NAME_BUNDLE = "PLAYER_ONE_TEAM_2_NAME_BUNDLE";
	public static final String PLAYER_TWO_TEAM_2_NAME_BUNDLE = "PLAYER_TWO_TEAM_2_NAME_BUNDLE";
	public static final String MATCH_IS_SINGLE_BUNDLE = "MATCH_IS_SINGLE_BUNDLE";
	
	private TextView pl1NameTV, pl12NameTV, pl1ScoreTV, pl1Set1TV, pl1Set2TV, pl1Set3TV, pl1Set4TV, pl1Set5TV, 
	         pl2NameTV,pl22NameTV, pl2ScoreTV, pl2Set1TV, pl2Set2TV, pl2Set3TV, pl2Set4TV, pl2Set5TV, gameType;
	private Button buttonScore1, buttonScore2;
	private LinearLayout buttonHolder;
	private Score score;
	private String pl1id, pl2id, pl12, pl22; 
	private int matchId;
	private boolean matchIsSingle;
	private ImageView servicePl1Score, servicePl1, servicePl2, servicePl2Score;
	private ArrayList<Integer> undoRedo;
	private int currentValueForRedo;
	private int numberOfUndo = 0;
	
	private boolean player1IsOnHisSide = true;
	
	/*------------------------------------*/
	/*         OVERRIDE METHODES          */
	/*------------------------------------*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		undoRedo = new ArrayList<Integer>();
		Intent intent = getIntent();
		int gameProfile = intent.getIntExtra(GameProfileActivity.GAME_PROFILE_EXTRASS_KEY, 1);
		pl1id = intent.getStringExtra(TenisActivity.PLAYER_ONE_TEAM_1_NAME_BUNDLE);
		pl2id = intent.getStringExtra(TenisActivity.PLAYER_ONE_TEAM_2_NAME_BUNDLE);
		matchId = intent.getIntExtra(TenisActivity.MATCH_ID_BUNDLE, 0);
		matchIsSingle = intent.getBooleanExtra(TenisActivity.MATCH_IS_SINGLE_BUNDLE,true);
		if (!matchIsSingle) {
			pl12 = intent.getStringExtra(TenisActivity.PLAYER_TWO_TEAM_1_NAME_BUNDLE);
			pl22 = intent.getStringExtra(TenisActivity.PLAYER_TWO_TEAM_2_NAME_BUNDLE);
		}
		score = new Score();
		score.setGameProfile(gameProfile);
		score.setMatchId(matchId);
		
		setContentView(R.layout.counter_activity);
		referenceViews();
		setPlayerNames();
		setOnClickListeners();
		Score pausedScore = LocalDbQuery.getPausedScore(getApplicationContext(), score.getMatchId());
//		Score pausedScore = null;
		if (pausedScore != null) {
			score = pausedScore;
			showScore(score);
			setVisiblityForSets(score);
			setServiceIndentificator(score);
		}
		else {
			setVisiblityForSets(score);
			if (!matchIsSingle) {
				showServiceDialog(pl1id, pl2id, pl12, pl22);
			}
			else {
				showServiceDialog(pl1id, pl2id);
			}
			setNoServiceIdentificator();
		}
		
		int id;
		id = LocalDbQuery.getSecondBiggestID(getApplicationContext(), score.getMatchId());
		if (id != 0) {
			
		}
		
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.counter_activity_menu, menu);
			MenuItem undoMenuItem = menu.findItem(R.id.menuItemCounterUndo);
			MenuItem redoMenuItem = menu.findItem(R.id.menuItemCounterRedo);
			undoMenuItem.setEnabled(false);
			redoMenuItem.setEnabled(false);
			return true;
	    }
	 
	 @Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		MenuItem undoMenuItem = menu.findItem(R.id.menuItemCounterUndo);
		MenuItem redoMenuItem = menu.findItem(R.id.menuItemCounterRedo);
		if (undoRedo.size() == 0 || currentValueForRedo == 0) {
			undoMenuItem.setEnabled(false);
		}
		else {
			undoMenuItem.setEnabled(true);
		}
		if (currentValueForRedo == undoRedo.size() || numberOfUndo == 0) {
			redoMenuItem.setEnabled(false);
		} else {
			redoMenuItem.setEnabled(true);
		}
		
		return super.onMenuOpened(featureId, menu);
	}
	    
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
			case R.id.menuItemCounterUndo:
				undoMenu();
				return true;
				
	        case R.id.menuItemCounterRedo:
	        	redoMenu();
				return true;
				
	        case R.id.menuItemCounterStop:
	        	showPauseDialog();
				return true;
				
	        case R.id.menuItemCounterEditMatch:
	        	Intent i = new Intent(this, EditMatchActivity.class);
	        	i.putExtra("score", score);
	        	i.putExtra(PLAYER_ONE_TEAM_1_NAME_BUNDLE, pl1id);
				i.putExtra(PLAYER_ONE_TEAM_2_NAME_BUNDLE, pl2id);
				if (!matchIsSingle) {
					i.putExtra(PLAYER_TWO_TEAM_1_NAME_BUNDLE, pl12);
					i.putExtra(PLAYER_TWO_TEAM_2_NAME_BUNDLE, pl22);
					i.putExtra(MATCH_IS_SINGLE_BUNDLE, false);
				}
				else {
					i.putExtra(MATCH_IS_SINGLE_BUNDLE, true);
				}
				
				startActivity(i);
//				i.putExtra(MATCH_ID_BUNDLE, score.getMatchId());
//				i.putExtra(PLAYER_ONE_SCORE, score.getPl1_score());
//				i.putExtra(PLAYER_TWO_SCORE, score.getPl2_score());
//				i.putExtra(PLAYER_ONE_SET1, score.getPl1_set1());
//				i.putExtra(PLAYER_TWO_SET1, score.getPl2_set1());
//				i.putExtra(PLAYER_ONE_SET2, score.getPl1_set2());
//				i.putExtra(PLAYER_TWO_SET2, score.getPl2_set2());
//				i.putExtra(PLAYER_ONE_SET3, score.getPl1_set3());
//				i.putExtra(PLAYER_TWO_SET3, score.getPl2_set3());
//				i.putExtra(PLAYER_ONE_SET4, score.getPl1_set4());
//				i.putExtra(PLAYER_TWO_SET4, score.getPl2_set4());
//				i.putExtra(PLAYER_ONE_SET5, score.getPl1_set5());
//				i.putExtra(PLAYER_TWO_SET5, score.getPl2_set5());
//				i.putExtra(SERVICE, score.getWhichPlayerServing());
				return true;
				
	        case R.id.menuItemCounterSettings:
	        	Intent languageIntent = new Intent(this, LanguageActivity.class);
	        	startActivity(languageIntent);
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
			}
	    }
	 
	 @Override
	public void onBackPressed() {
		showPauseDialog();
	}
	 
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == Activity.RESULT_OK) {
			
		}
	}
	
		/*------------------------------------*/
		/*         PRIVATE METHODES          */
		/*------------------------------------*/
	 
	private void referenceViews() {
		pl1NameTV = (TextView) findViewById(R.id.counter_playerName1_TV);
		pl2NameTV = (TextView) findViewById(R.id.counter_playerName2_TV);
		pl22NameTV = (TextView) findViewById(R.id.counter_playerName22_TV);
		pl12NameTV = (TextView) findViewById(R.id.counter_playerName12_TV);
		
		pl1Set1TV = (TextView) findViewById(R.id.counter_pl_set1_TV);
		pl1Set2TV = (TextView) findViewById(R.id.counter_pl_set2_TV);
		pl1Set3TV = (TextView) findViewById(R.id.counter_pl1_set3_TV);
		pl1Set4TV = (TextView) findViewById(R.id.counter_pl1_set4_TV);
		pl1Set5TV = (TextView) findViewById(R.id.counter_pl1_set5_TV);
		pl2Set1TV = (TextView) findViewById(R.id.counter_pl2_set1_TV);
	    pl2Set2TV = (TextView) findViewById(R.id.counter_pl2_set2_TV);
	    pl2Set3TV = (TextView) findViewById(R.id.counter_pl2_set3_TV);
	    pl2Set4TV = (TextView) findViewById(R.id.counter_pl2_set4_TV);
	    pl2Set5TV = (TextView) findViewById(R.id.counter_pl2_set5_TV);
		
	    pl1ScoreTV = (TextView) findViewById(R.id.counter_pl1_score_TV);
		pl2ScoreTV = (TextView) findViewById(R.id.counter_pl_2_score_TV);
		
		buttonScore1 = (Button) findViewById(R.id.counter_button1);
		buttonScore2 = (Button) findViewById(R.id.counter_button2);
		
		buttonHolder = (LinearLayout) findViewById(R.id.counterActButtonHolderLL);
		
		servicePl1 = (ImageView) findViewById(R.id.serviceIndikatorPl1);
		servicePl1Score = (ImageView) findViewById(R.id.serviceIndikatorScorePl1);
		servicePl2 = (ImageView) findViewById(R.id.serviceIndikatorPl2);
		servicePl2Score = (ImageView) findViewById(R.id.serviceIndikatorScorePl2);
		
		gameType = (TextView) findViewById(R.id.counter_gameTypeTV);
	}
	
	private void setPlayerNames(){
		switch (score.getGameProfile()) {
		
		case GAME_PROFILE.TWO_SETS_WITHOUT_TIE:
			gameType.setText(R.string.two_sets_without_tie_break);
			break;
       case GAME_PROFILE.THREE_SETS_WITHOUT_TIE:
    	   gameType.setText(R.string.three_sets_without_tie_break);
    	   break;
		case GAME_PROFILE.TWO_SETS_WITH_TIE:
			gameType.setText(R.string.two_sets_with_tie_break);
			break;
		case GAME_PROFILE.THREE_SETS_WITH_TIE:
			gameType.setText(R.string.three_sets_with_tie_break);
			break;
		case  GAME_PROFILE.TWO_SETS_WITH_SUPER_TIE:
			gameType.setText(R.string.two_sets_with_super_tie_break);
			break;
        case GAME_PROFILE.THREE_SETS_WITH_SUPER_TIE:
        	gameType.setText(R.string.three_sets_with_super_tie_break);
        	break;

		default:
			gameType.setText("");
			break;
		}
		pl1NameTV.setText(pl1id);
		pl2NameTV.setText(pl2id);
		if (matchIsSingle) {
			pl12NameTV.setVisibility(View.GONE);
			pl22NameTV.setVisibility(View.GONE);
//			buttonScore1.setText(pl1id);
//			buttonScore2.setText(pl2id);
		}
		else {
			pl12NameTV.setVisibility(View.VISIBLE);
			pl22NameTV.setVisibility(View.VISIBLE);
			pl12NameTV.setText(pl12);
			pl22NameTV.setText(pl22);
//			String pl1Name = pl1id + "  " + playerSeparator + "  " + 
//					pl12;
//			String pl2Name = pl2id+ "  " + playerSeparator + "  " + 
//					pl22;
//			buttonScore1.setText(pl1Name);
//			buttonScore2.setText(pl2Name);
		}
		
		showPlayerNames();
	}
	
	private void showPlayerNames() {
		if (matchIsSingle) {
			if (player1IsOnHisSide) {
				buttonScore1.setText(pl1id);
				buttonScore2.setText(pl2id);
			}
			else {
				buttonScore2.setText(pl1id);
				buttonScore1.setText(pl2id);
			}
		}
		else {
			String pl1Name = pl1id + "  " + playerSeparator + "  " + 
					pl12;
			String pl2Name = pl2id+ "  " + playerSeparator + "  " + 
					pl22;
			
			if (player1IsOnHisSide) {
				buttonScore1.setText(pl1Name);
				buttonScore2.setText(pl2Name);
			}
			else {
				buttonScore2.setText(pl1Name);
				buttonScore1.setText(pl2Name);
			}
		}
	}
	
	private void setOnClickListeners() {
        buttonScore1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (player1IsOnHisSide) 
					player1Scored();
				else {
					player2Scored();
				}
			}
		});
		
		buttonScore2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (player1IsOnHisSide) 
					player2Scored();
				else {
					player1Scored();
				}
			}
	    });
	}
	
	private void player1Scored() {
		score = BrainMath.calculate( getScoreForCalculation(), 1);
		score.setId(0);
		if (score.isGameIsEnded() == true) {
			showScore(score);
			endMatch();
			Log.LogToFile("player one wins the match");
		}
		else {
			Animation a = AnimationUtils.makeOutAnimation(getApplicationContext(), false);
			buttonHolder.setAnimation(a);
			buttonHolder.startLayoutAnimation();
			showScore(score);
			setVisiblityForSets(score);
			setServiceIndentificator(score);
			
			
		}
		int id = LocalDbInsert.getBiggestID(getApplicationContext())+1;
		android.util.Log.d("pred", id+"");
		if (undoRedo.size() != 0) {
			for (int i = 0; i <1999; i++) {
				if (undoRedo.get(undoRedo.size()-1) >= id) {
					id = id +1;
				}
				if (undoRedo.get(undoRedo.size()-1) < id) {
					break;
				}
			}
		}
		android.util.Log.d("po", id+"");
		undoRedo.add(id);
		new InsertScore(score, id).execute();
		
		calculateBallsChange();
		calculateIfChangeSides();
		
		/*Runnable a = new Runnable() {
			
			@Override
			public void run() {
//				int isOnWeb = 0;
//				if (isOnline()) {
//					isOnWeb = Qscore.insertScoreChange(score, getApplicationContext());
//					int id = LocalDbInsert.insertScore(score, getApplicationContext(), isOnWeb, 0);
//					undoRedo.add(id);
//					currentValueForRedo = undoRedo.size()-1;
				Qscore.insertScoreChange(score, getApplicationContext());
			
				
			}
		};
//		a.run();
		int isOnWeb = 0;
		if ( isOnline()) {
			a.run();
//			isOnWeb = Qscore.insertScoreChange(score, getApplicationContext());
			
		}
		
		int id = LocalDbInsert.insertScore(score, getApplicationContext(), isOnWeb, 0);
		undoRedo.add(id);
		currentValueForRedo = undoRedo.size();
		numberOfUndo = 0;*/
	}
	
	private void player2Scored() {
		score = BrainMath.calculate( getScoreForCalculation(), 2);
		score.setId(0);
		if (score.isGameIsEnded() == true) {
			showScore(score);
			endMatch();
			Log.LogToFile("player two wins the match");
		}
		else {
			Animation a = AnimationUtils.makeOutAnimation(getApplicationContext(), true);
			buttonHolder.setAnimation(a);
			buttonHolder.startLayoutAnimation();
			showScore(score);
			setVisiblityForSets(score);
			setServiceIndentificator(score);
			
		}
		int id = LocalDbInsert.getBiggestID(getApplicationContext()) + 1;

		if (undoRedo.size() != 0) {
			for (int i = 0; i < 1999; i++) {
				if (undoRedo.get(undoRedo.size() - 1) >= id) {
					id = id + 1;
				}
				if (undoRedo.get(undoRedo.size() - 1) < id) {
					break;
				}
			}
		}
		undoRedo.add(id);
		new InsertScore(score, id).execute();
		
		calculateBallsChange();
		calculateIfChangeSides();
		
		/*Runnable a = new Runnable() {
			
			@Override
			public void run() {
				 Qscore.insertScoreChange(score, getApplicationContext());
//				int isOnWeb = 0;
//				if (isOnline()) {
//					isOnWeb = Qscore.insertScoreChange(score, getApplicationContext());
//				}
//				int id = LocalDbInsert.insertScore(score, getApplicationContext(), isOnWeb, 0);
//				undoRedo.add(id);
//				currentValueForRedo = undoRedo.size()-1;
			}
		};
		a.run();
		
		int isOnWeb = 0;
		if (isOnline()) {
			a.run();
//			isOnWeb = Qscore.insertScoreChange(score, getApplicationContext());
		}
		int id = LocalDbInsert.insertScore(score, getApplicationContext(), isOnWeb, 0);
		undoRedo.add(id);
		currentValueForRedo = undoRedo.size();
		numberOfUndo = 0;*/
	}
	
	private void calculateBallsChange() {
		BallsChange checkBalls = CalculateBallsChange.checkBalls(score, getBallsChangeMode());
		if (checkBalls != BallsChange.NONE) {
			FragmentManager fm = getSupportFragmentManager();
			DialogFragment createDIalog = BallsChangeDialog.createDIalog(checkBalls);
			createDIalog.setCancelable(false);
			createDIalog.show(fm, "dialog");
		}
	}
	
	private void calculateIfChangeSides() {
        boolean changeSides = score.getChangeSides();
		if (changeSides) {
			this.player1IsOnHisSide = !player1IsOnHisSide;
			setPlayerNames();
			Toast.makeText(this, R.string.teamSidesChenged, Toast.LENGTH_LONG).show();
		}
	}
	
	private Score getScoreForCalculation() {
		if (score.getGameMode() == Score.GAME_MODE.CLASSIC) {
			score.setPl1_score(pl1ScoreTV.getText().toString());
			score.setPl2_score(pl2ScoreTV.getText().toString());
		}
		else if (score.getGameMode() == Score.GAME_MODE.SUPER_TIEBREAK || 
				 score.getGameMode() == Score.GAME_MODE.TIEBREAK) {
			score.setPl1TieScore(Integer.parseInt(pl1ScoreTV.getText().toString()));
			score.setPl2TieScore(Integer.parseInt(pl2ScoreTV.getText().toString()));
		}
		
		score.setPl1_set1(Integer.parseInt(pl1Set1TV.getText().toString()));
		score.setPl1_set2(Integer.parseInt(pl1Set2TV.getText().toString()));
		score.setPl1_set3(Integer.parseInt(pl1Set3TV.getText().toString()));
		score.setPl1_set4(Integer.parseInt(pl1Set4TV.getText().toString()));
		score.setPl1_set5(Integer.parseInt(pl1Set5TV.getText().toString()));
		
		score.setPl2_set1(Integer.parseInt(pl2Set1TV.getText().toString()));
		score.setPl2_set2(Integer.parseInt(pl2Set2TV.getText().toString()));
		score.setPl2_set3(Integer.parseInt(pl2Set3TV.getText().toString()));
		score.setPl2_set4(Integer.parseInt(pl2Set4TV.getText().toString()));
		score.setPl2_set5(Integer.parseInt(pl2Set5TV.getText().toString()));
		
		return score;
	}
	
	private void showScore(Score score) {
		if (score.getGameMode() == Score.GAME_MODE.CLASSIC) {
			pl1ScoreTV.setText(score.getPl1_score_String());
			pl2ScoreTV.setText(score.getPl2_score_String());
		}
		else if (score.getGameMode() == Score.GAME_MODE.SUPER_TIEBREAK || 
				 score.getGameMode() == Score.GAME_MODE.TIEBREAK) {
			pl1ScoreTV.setText(Integer.toString(score.getPl1TieScore()));
			pl2ScoreTV.setText(Integer.toString(score.getPl2TieScore()));
		}
		
		pl1Set1TV.setText(Integer.toString(score.getPl1_set1()));
		pl1Set2TV.setText(Integer.toString(score.getPl1_set2()));
		pl1Set3TV.setText(Integer.toString(score.getPl1_set3()));
		pl1Set4TV.setText(Integer.toString(score.getPl1_set4()));
		pl1Set5TV.setText(Integer.toString(score.getPl1_set5()));
		
		pl2Set1TV.setText(Integer.toString(score.getPl2_set1()));
		pl2Set2TV.setText(Integer.toString(score.getPl2_set2()));
		pl2Set3TV.setText(Integer.toString(score.getPl2_set3()));
		pl2Set4TV.setText(Integer.toString(score.getPl2_set4()));
		pl2Set5TV.setText(Integer.toString(score.getPl2_set5()));
	}
	
	private void setVisiblityForSets(Score score){
		int setFlag = score.getFlag();
		int set1Visiblity;
		int set2Visiblity;
		int set3Visiblity;
		int set4Visiblity;
		int set5Visiblity;
		
		switch (setFlag) {
		
		case Score.SCORE_FLAGS.FIRST_SET:
			set1Visiblity = View.VISIBLE;
			set2Visiblity = View.GONE;
			set3Visiblity = View.GONE;
			set4Visiblity = View.GONE;
			set5Visiblity = View.GONE;
			break;
			
        case Score.SCORE_FLAGS.SECOND_SET:
        	set1Visiblity = View.VISIBLE;
			set2Visiblity = View.VISIBLE;
			set3Visiblity = View.GONE;
			set4Visiblity = View.GONE;
			set5Visiblity = View.GONE;
			break;
			
        case Score.SCORE_FLAGS.THIRD_SET:
        	set1Visiblity = View.VISIBLE;
			set2Visiblity = View.VISIBLE;
			set3Visiblity = View.VISIBLE;
			set4Visiblity = View.GONE;
			set5Visiblity = View.GONE;
	        break;
	        
        case Score.SCORE_FLAGS.FOURTH_SET:
        	set1Visiblity = View.VISIBLE;
			set2Visiblity = View.VISIBLE;
			set3Visiblity = View.VISIBLE;
			set4Visiblity = View.VISIBLE;
			set5Visiblity = View.GONE;
	        break;
	        
        case Score.SCORE_FLAGS.FIFTH_SET:
        	set1Visiblity = View.VISIBLE;
			set2Visiblity = View.VISIBLE;
			set3Visiblity = View.VISIBLE;
			set4Visiblity = View.VISIBLE;
			set5Visiblity = View.VISIBLE;
	        break;

		default:
			set1Visiblity = View.VISIBLE;
			set2Visiblity = View.GONE;
			set3Visiblity = View.GONE;
			set4Visiblity = View.GONE;
			set5Visiblity = View.GONE;
			break;
		}
		
		pl1Set1TV.setVisibility(set1Visiblity);
		pl2Set1TV.setVisibility(set1Visiblity);
		pl1Set2TV.setVisibility(set2Visiblity);
		pl2Set2TV.setVisibility(set2Visiblity);
		pl1Set3TV.setVisibility(set3Visiblity);
		pl2Set3TV.setVisibility(set3Visiblity);
		pl1Set4TV.setVisibility(set4Visiblity);
		pl2Set4TV.setVisibility(set4Visiblity);
		pl1Set5TV.setVisibility(set5Visiblity);
		pl2Set5TV.setVisibility(set5Visiblity);
	}
	
	private void setServiceIndentificator(Score score) {
		int whichPlayerServing = score.getWhichPlayerServing();
		if (whichPlayerServing == 1) {
			servicePl2.setVisibility(View.INVISIBLE);
			servicePl2Score.setVisibility(View.INVISIBLE);
			servicePl1.setVisibility(View.VISIBLE);
			servicePl1Score.setVisibility(View.VISIBLE);
		}
		else if (whichPlayerServing == 2) {
			servicePl2.setVisibility(View.VISIBLE);
			servicePl2Score.setVisibility(View.VISIBLE);
			servicePl1.setVisibility(View.INVISIBLE);
			servicePl1Score.setVisibility(View.INVISIBLE);
		}
	}
	
	private void setNoServiceIdentificator(){
		servicePl2.setVisibility(View.INVISIBLE);
		servicePl2Score.setVisibility(View.INVISIBLE);
		servicePl1.setVisibility(View.INVISIBLE);
		servicePl1Score.setVisibility(View.INVISIBLE);
	}
	
	private void showServiceDialog(String pl1Name, String pl2Name) {
		FragmentManager fm = getSupportFragmentManager();
		DialogFragment dialog = ServiceDialog.createDialog(pl1Name, pl2Name);
		dialog.setCancelable(false);
		dialog.show(fm, "serviceDIalog");
	}
	
	private void showServiceDialog(String pl1Name, String pl2Name, String pl12Name, String pl22Name) {
		FragmentManager fm = getSupportFragmentManager();
		DialogFragment dialog = ServiceDialog.createDialog(pl1Name, pl2Name,pl12Name,pl22Name);
		dialog.setCancelable(false);
		dialog.show(fm, "serviceDIalog");
	}
	
	private void showSidesPickerDialog() {
		FragmentManager fm = getSupportFragmentManager();
		DialogFragment dialog;
		if (matchIsSingle) 
			dialog = TeamSidePickerDialog.createDialog(pl1id, pl2id);
		else 
			dialog = TeamSidePickerDialog.createDialog(pl1id, pl2id, pl12, pl22);
		dialog.setCancelable(false);
		dialog.show(fm, "sidePickerDialog");
	}
	
	
	private void endMatch() {
		buttonScore1.setEnabled(false);
		buttonScore2.setEnabled(false);
		FragmentManager fm = getSupportFragmentManager();
		DialogFragment dialog = EndMatchDialog.createDialog();
		dialog.setCancelable(false);
		dialog.show(fm, "endMatchDialog");
	}
	
	private void showPauseDialog(){
		FragmentManager fm = getSupportFragmentManager();
		DialogFragment endMatch = EndMatchOnBackPressedDIalog.createDialog();
		endMatch.setCancelable(false);
		endMatch.show(fm, "endMatchDIalog");
	}
	
	
	public void setServiceAtStart(int whichPlayerServing) {
		showSidesPickerDialog();
		
		score.setWhichPlayerServing(whichPlayerServing);
		setServiceIndentificator(score);
		if (isOnline()) {
			Match.setMatchState(STATE.STARTED, matchId, getApplicationContext());
			Qscore.insertScoreChange(score, getApplicationContext());
		}
	}
	
	public void setSidesAtStart(TeamSides teamSide) {
		if (teamSide == TeamSides.NORMAL) 
			player1IsOnHisSide = true;
		else 
			player1IsOnHisSide = false;
		
		setPlayerNames();
	}
	
	/******** METHODS FOR DIALOG AFTER MATCH FINISH ********/
	public void undo() {
		// TODO  check if it's working
		undoMenu();
	}
	
	public void finishMatch() {
		Match.setMatchState(Match.STATE.ENDED, matchId, getApplicationContext());
		setResult(Activity.RESULT_OK);
		LocalDbQuery.deleteRowsOnMatchFinish(this, matchId);
		finish();
	}
	
	/******** METHODS FOR MENU AND PAUSE DIALOG ********/
	public void pauseMatchMenu() {
		Match.setMatchState(Match.STATE.INTERUPTED, matchId, getApplicationContext());
		LocalDbQuery.deleteRowsOnMatchPause(getApplicationContext());
		LocalDbInsert.insertScore(score, getApplicationContext(), 1, 1, 0);
		setResult(Activity.RESULT_OK);
		finish();
	}
	
	private void undoMenu() {
		numberOfUndo = numberOfUndo + 1;
		Score scoreForUndo = LocalDbQuery.getPreviouseScore(
				getApplicationContext(), score.getMatchId(),
				undoRedo.get(currentValueForRedo - 1));
		currentValueForRedo = currentValueForRedo - 1;
		showScore(scoreForUndo);
		setVisiblityForSets(scoreForUndo);
		setServiceIndentificator(scoreForUndo);
		score = scoreForUndo;
		new InsertScoreChange(score).execute();
	}
	
	private void redoMenu() {
		numberOfUndo = numberOfUndo - 1;
		Score scoreForRedo = LocalDbQuery.getPreviouseScore(getApplicationContext(), 
				score.getMatchId(), undoRedo.get(currentValueForRedo+1));
		currentValueForRedo = currentValueForRedo + 1;
		showScore(scoreForRedo);
		setVisiblityForSets(scoreForRedo);
		setServiceIndentificator(scoreForRedo);
		score = scoreForRedo;
		new InsertScoreChange(score).execute();
	}
	
	 private class InsertScore extends AsyncTask<Score , Void, Integer>{
         Score score;
         int scoreId;
		 public InsertScore(Score score, int scoreId) {
			this.score = score;
			this.scoreId = scoreId;
		}
	    	
			@Override
			protected Integer doInBackground(Score... params) {
				int isOnWeb = 0;
				if (isOnline()) {
					try {
						isOnWeb = Qscore.insertScoreChange(this.score, getApplicationContext());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				LocalDbInsert.insertScore(this.score, getApplicationContext(), isOnWeb, 0, scoreId);
				
				return scoreId;
			}
			
			@Override
			protected void onPostExecute(Integer id) {
				super.onPostExecute(id);
				
				currentValueForRedo = undoRedo.size();
				numberOfUndo = 0;
			}
	    }
	 
	 private class InsertScoreChange extends AsyncTask<Score , Void, Integer>{
         Score score;
         
		 public InsertScoreChange(Score score) {
			this.score = score;
		}
	    	
			@Override
			protected Integer doInBackground(Score... params) {
				int isOnWeb = 0;
				if (isOnline()) {
					isOnWeb = Qscore.insertScoreChange(score,getApplicationContext());
				}
				int id = LocalDbInsert.insertScore(score, getApplicationContext(), isOnWeb, 0, 0);
				
				return id;
			}
			
			@Override
			protected void onPostExecute(Integer id) {
				super.onPostExecute(id);
				
				undoRedo.add(id);
			}
	    }
	 
	public static enum TeamSides {
		NORMAL(1), REVERSE(2);

		private int value;

		private TeamSides(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
