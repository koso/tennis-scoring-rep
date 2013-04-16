package quintec.tenis.activities;

import quintec.tenis.LocaleActivity;
import quintec.tenis.R;
import quintec.tenis.Score;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class EditMatchActivity extends LocaleActivity {
	
	private TextView pl1NameTV, pl12NameTV, pl1ScoreTV, pl1Set1TV, pl1Set2TV, pl1Set3TV, pl1Set4TV, pl1Set5TV, 
    pl2NameTV,pl22NameTV, pl2ScoreTV, pl2Set1TV, pl2Set2TV, pl2Set3TV, pl2Set4TV, pl2Set5TV, gameType;

	private ImageView score1PlusIMG, score1MinusIMG, score2PlusIMG, score2MinusIMG, pl1SetPlusIMG, 
	        pl1SetMinusIMG, pl2SetPlusIMG, pl2SetMinusIMG;
	private ImageView servicePl1Score, servicePl2Score;
	private TextView score1EditTV, score2EditTV, set1EdittTV, set2EditTV;
	private RadioButton tiebrakRadio, classicRadio, service1Radio, service2Radio;
	
	private String pl1Name, pl2Name, pl12Name, pl22Name;
	private boolean matchIsSingle;
	private Score score;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		score = (Score) intent.getSerializableExtra("score");
		pl1Name = intent.getStringExtra(CounterActivity.PLAYER_ONE_TEAM_1_NAME_BUNDLE);
		pl2Name = intent.getStringExtra(CounterActivity.PLAYER_ONE_TEAM_2_NAME_BUNDLE);
		matchIsSingle = intent.getBooleanExtra(CounterActivity.MATCH_IS_SINGLE_BUNDLE,true);
		if (!matchIsSingle) {
			pl12Name = intent.getStringExtra(CounterActivity.PLAYER_TWO_TEAM_1_NAME_BUNDLE);
			pl22Name = intent.getStringExtra(CounterActivity.PLAYER_TWO_TEAM_2_NAME_BUNDLE);
		}
//		score.setPl1_score(intent.getIntExtra(CounterActivity.PLAYER_ONE_SCORE, 0));
//		score.setPl2_score(intent.getIntExtra(CounterActivity.PLAYER_TWO_SCORE, 0));
//		score.setPl1_set1(intent.getIntExtra(CounterActivity.PLAYER_ONE_SET1, 0));
//		score.setPl1_set2(intent.getIntExtra(CounterActivity.PLAYER_ONE_SET2, 0));
//		score.setPl1_set3(intent.getIntExtra(CounterActivity.PLAYER_ONE_SET3, 0));
//		score.setPl1_set4(intent.getIntExtra(CounterActivity.PLAYER_ONE_SET4, 0));
//		score.setPl1_set5(intent.getIntExtra(CounterActivity.PLAYER_ONE_SET5, 0));
//		score.setPl2_set1(intent.getIntExtra(CounterActivity.PLAYER_TWO_SET1, 0));
//		score.setPl2_set2(intent.getIntExtra(CounterActivity.PLAYER_TWO_SET2, 0));
//		score.setPl2_set3(intent.getIntExtra(CounterActivity.PLAYER_TWO_SET3, 0));
//		score.setPl2_set4(intent.getIntExtra(CounterActivity.PLAYER_TWO_SET4, 0));
//		score.setPl2_set5(intent.getIntExtra(CounterActivity.PLAYER_TWO_SET5, 0));
//		score.setWhichPlayerServing(intent.getIntExtra(CounterActivity.SERVICE, 0));
		setContentView(R.layout.edit_score_activity);
		referenceViews();
		setPlayerNames();
		setScore();
		setVisiblityForSets(score);
		setServiceIndentificator(score);
		setSetviceListener();
		setOnScoreListeners();
	}
	
	private void referenceViews(){
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
	
		servicePl1Score = (ImageView) findViewById(R.id.serviceIndikatorScorePl1);
		
		servicePl2Score = (ImageView) findViewById(R.id.serviceIndikatorScorePl2);
		service1Radio = (RadioButton) findViewById(R.id.edit_service1);
		service2Radio = (RadioButton) findViewById(R.id.edit_service2);
		score1EditTV = (TextView) findViewById(R.id.edit_Score1);
		score2EditTV = (TextView) findViewById(R.id.edit_Score2);
		score1PlusIMG = (ImageView) findViewById(R.id.edit_plusScore1);
		score1MinusIMG = (ImageView) findViewById(R.id.edit_minusScore1);
		score2PlusIMG = (ImageView) findViewById(R.id.edit_plusScore2);
		score2MinusIMG = (ImageView) findViewById(R.id.edit_minusScore2);
		classicRadio = (RadioButton) findViewById(R.id.edit__classicScore);
		tiebrakRadio = (RadioButton) findViewById(R.id.edit__tieScore);
	}
	
	private void setSetviceListener(){
		service1Radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					score.setWhichPlayerServing(1);
					setServiceIndentificator(score);
				}
				
			}
		});
       service2Radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					score.setWhichPlayerServing(2);
					setServiceIndentificator(score);
				}
				
			}
		});
	}
	
	private void setOnScoreListeners(){
		
		score1EditTV.setText(score.getPl1_score_String());
		score2EditTV.setText(score.getPl2_score_String());
		score1PlusIMG.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (classicRadio.isChecked()) {
					if (score.getPl1_score() == 0) {
						
						score.setPl1_score(1);
						score1EditTV.setText(score.getPl1_score_String());
					}
					else if (score.getPl1_score() == 1) {
						score.setPl1_score(2);
						score1EditTV.setText(score.getPl1_score_String());
					}
					else if (score.getPl1_score() == 2) {
						score.setPl1_score(3);
						score1EditTV.setText(score.getPl1_score_String());
					}
					else if (score.getPl1_score() == 3) {
						score.setPl1_score(4);
						score1EditTV.setText(score.getPl1_score_String());
					}
				}
				
				if (tiebrakRadio.isChecked()) {
					if (score.getPl1_score() == 0) {
						
						score.setPl1_score(1);
						score1EditTV.setText(score.getPl1_score_String());
					}
					
				}
				
				
				setScore();
				
			}
		});
		score1MinusIMG.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (classicRadio.isChecked()) {
					if (score.getPl1_score() == 0) {
						
					}
					else if (score.getPl1_score() == 1) {
						score.setPl1_score(0);
						score1EditTV.setText(score.getPl1_score_String());
					}
					else if (score.getPl1_score() == 2) {
						score.setPl1_score(1);
						score1EditTV.setText(score.getPl1_score_String());
					}
					else if (score.getPl1_score() == 3) {
						score.setPl1_score(2);
						score1EditTV.setText(score.getPl1_score_String());
					}
					else if (score.getPl1_score() == 4) {
						score.setPl1_score(3);
						score1EditTV.setText(score.getPl1_score_String());
					}
				}
				
				setScore();
				
			}
		});
		
		score2MinusIMG.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (classicRadio.isChecked()) {
					if (score.getPl2_score() == 0) {
						
					}
					else if (score.getPl2_score() == 1) {
						score.setPl2_score(0);
						score2EditTV.setText(score.getPl2_score_String());
					}
					else if (score.getPl2_score() == 2) {
						score.setPl2_score(1);
						score2EditTV.setText(score.getPl2_score_String());
					}
					else if (score.getPl2_score() == 3) {
						score.setPl2_score(2);
						score2EditTV.setText(score.getPl2_score_String());
					}
					else if (score.getPl2_score() == 4) {
						score.setPl2_score(3);
						score2EditTV.setText(score.getPl2_score_String());
					}
				}
				
				setScore();
				
			}
		});
		
		score2PlusIMG.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (classicRadio.isChecked()) {
					if (score.getPl2_score() == 0) {
						
						score.setPl2_score(1);
						score2EditTV.setText(score.getPl2_score_String());
					}
					else if (score.getPl2_score() == 1) {
						score.setPl2_score(2);
						score2EditTV.setText(score.getPl2_score_String());
					}
					else if (score.getPl2_score() == 2) {
						score.setPl2_score(3);
						score2EditTV.setText(score.getPl2_score_String());
					}
					else if (score.getPl2_score() == 3) {
						score.setPl2_score(4);
						score2EditTV.setText(score.getPl2_score_String());
					}
				}
				
				
				setScore();
				
			}
		});
	}
	
	private void setPlayerNames(){
		pl1NameTV.setText(pl1Name);
		pl2NameTV.setText(pl2Name);
		if (matchIsSingle) {
			pl12NameTV.setVisibility(View.GONE);
			pl22NameTV.setVisibility(View.GONE);
		}
		else {
			pl12NameTV.setVisibility(View.VISIBLE);
			pl22NameTV.setVisibility(View.VISIBLE);
			pl12NameTV.setText(pl12Name);
			pl22NameTV.setText(pl22Name);
		}
	}
	
	private void setScore(){
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
			servicePl2Score.setVisibility(View.INVISIBLE);
			servicePl1Score.setVisibility(View.VISIBLE);
		}
		else if (whichPlayerServing == 2) {
			servicePl2Score.setVisibility(View.VISIBLE);
			servicePl1Score.setVisibility(View.INVISIBLE);
		}
	}
}
