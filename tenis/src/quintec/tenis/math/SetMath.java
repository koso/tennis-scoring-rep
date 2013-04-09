package quintec.tenis.math;

import quintec.tenis.Score;
import quintec.tenis.Score.SCORE_FLAGS;

public class SetMath {
	
	public static Score calculatePl1(Score score) {
		int setScore = score.getPl1_set_score();
		int scoreFlag = score.getFlag();
		score = Service.setService(score);
		
		switch (scoreFlag) {
		case Score.SCORE_FLAGS.FIRST_SET:
			int set1Score = score.getPl1_set1();
			int set1_value = getSetValue(score.getPl1_set1(), score.getPl2_set1());
			
			if (set1_value != 0) {
				score.setPl1_set1(set1_value);
			}
			else {
				score.setFlag(SCORE_FLAGS.SECOND_SET);
				score.setPl1_set1(set1Score +1);
				setScore++;
				score.setPl1_set_score(setScore);
			}
			return score;
		
		case Score.SCORE_FLAGS.SECOND_SET:
			int set2_value = getSetValue(score.getPl1_set2(), score.getPl2_set2());
			int set2Score = score.getPl1_set2();
			
			if (set2_value != 0) {
				score.setPl1_set2(set2_value);
			}
			else {
				score.setFlag(SCORE_FLAGS.THIRD_SET);
				score.setPl1_set2(set2Score +1);
				setScore++;
				score.setPl1_set_score(setScore);
			}
			return score;
		
		case Score.SCORE_FLAGS.THIRD_SET:
			int set3_value = getSetValue(score.getPl1_set3(), score.getPl2_set3());
			int set3Score = score.getPl1_set3();
			
			if (set3_value != 0) {
				score.setPl1_set3(set3_value);
			}
			else {
				score.setFlag(SCORE_FLAGS.FOURTH_SET);
				score.setPl1_set3(set3Score +1);
				setScore++;
				score.setPl1_set_score(setScore);
			}
			return score;
		
		case Score.SCORE_FLAGS.FOURTH_SET:
			int set4_value = getSetValue(score.getPl1_set4(), score.getPl2_set4());
			int set4Score = score.getPl1_set4();
			
			if (set4_value != 0) {
				score.setPl1_set4(set4_value);
			}
			else {
				score.setFlag(SCORE_FLAGS.FIFTH_SET);
				score.setPl1_set4(set4Score +1);
				score.setPl2_set_score(setScore + 1);
			}
			return score;
			
		case Score.SCORE_FLAGS.FIFTH_SET:
			score.setPl1_set5(getSetValue(score.getPl1_set5(), score.getPl2_set5()));
			return score;
		
		default:
			return score;
		}
		
		
	
	}

    public static Score calculatePl2(Score score) {
    	int scoreFlag = score.getFlag();
		int setScore = score.getPl2_set_score();
		score = Service.setService(score);
		
		switch (scoreFlag) {
		
		case Score.SCORE_FLAGS.FIRST_SET:
			int set1_value = getSetValue(score.getPl2_set1(), score.getPl1_set1());
			int set1Score = score.getPl2_set1();
			
			if (set1_value != 0) {
				score.setPl2_set1(set1_value);
			}
			else {
				score.setFlag(SCORE_FLAGS.SECOND_SET);
				score.setPl2_set1(set1Score +1);
				setScore++;
				score.setPl2_set_score(setScore);
			}
			
			return score;
		
		case Score.SCORE_FLAGS.SECOND_SET:
			int set2_value = getSetValue(score.getPl2_set2(), score.getPl1_set2());
			int set2Score = score.getPl2_set2();
			
			if (set2_value != 0) {
				score.setPl2_set2(set2_value);
			}
			else {
				score.setFlag(SCORE_FLAGS.THIRD_SET);
				score.setPl2_set2(set2Score +1);
				setScore++;
				score.setPl2_set_score(setScore);
			}
			return score;
		
		case Score.SCORE_FLAGS.THIRD_SET:
			int set3_value = getSetValue(score.getPl2_set3(), score.getPl1_set3());
			int set3Score = score.getPl2_set3();
			
			if (set3_value != 0) {
				score.setPl2_set3(set3_value);
			}
			else {
				score.setFlag(SCORE_FLAGS.FOURTH_SET);
				score.setPl2_set3(set3Score +1);
				setScore++;
				score.setPl2_set_score(setScore);
			}
			return score;
		
		case Score.SCORE_FLAGS.FOURTH_SET:
			int set4_value = getSetValue(score.getPl2_set4(), score.getPl1_set4());
			int set4Score = score.getPl2_set4();
			
			if (set4_value != 0) {
				score.setPl2_set4(set4_value);
			}
			else {
				score.setFlag(SCORE_FLAGS.FIFTH_SET);
				score.setPl2_set4(set4Score +1);
				score.setPl2_set_score(setScore++);
			}
			return score;
		
		case Score.SCORE_FLAGS.FIFTH_SET:
			score.setPl2_set5(getSetValue(score.getPl2_set5(), score.getPl1_set5()));
			return score;
		
		default:
			return score;
		}
	}
    
    public static final int getSetValue(int pl1Set , int pl2Set) {
    	int setValue;
		if (pl1Set < 5 ) {
			setValue = pl1Set+1;
		}
		else if (pl1Set == 5) {
			if (pl2Set < 5) {
				setValue = 0;
			}
			else if(pl2Set == 6 || pl2Set == 5){
				setValue = pl1Set+1;
			}
			else {
				setValue = 0;
			}
		}
		else if (pl1Set > 5) {
			if (pl1Set == pl2Set) {
				setValue = pl1Set+1;
			}
			else if (pl1Set - pl2Set == 1) {
				setValue = 0;
			}
			else {
				setValue = pl1Set+1;
			}
		}
		else {
			setValue = 0;
		}
		return setValue;
	}
}
