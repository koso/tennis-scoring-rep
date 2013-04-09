package quintec.tenis.math;

import quintec.tenis.Score;
import quintec.tenis.Score.SCORE_FLAGS;
import quintec.tenis.math.ScoreMath.SCORE;

public class SuperTieMath {

	public static Score calculate(Score score, int whichPlayer){
		
		if (whichPlayer ==1) {
			int pl1score = score.getPl1TieScore();
			int pl2Score = score.getPl2TieScore();
			
			if (pl1score < 9 ) {
				pl1score++;
			}
			else if (pl1score == 9) {
				if (pl2Score < 9) {
//					pl1score++;
//					score.setGameIsEnded(true);
					calculatePl1TieScore(score);
					pl1score = 0;
				}
				else if(pl2Score == 10 || pl2Score == 9){
					pl1score++;
				}
			}
			else if (pl1score > 9) {
				if (pl1score == pl2Score) {
					pl1score++;
				}
				else if (pl1score - pl2Score == 1) {
//					pl1score++;
//					score.setGameIsEnded(true);
					calculatePl1TieScore(score);
					pl1score = 0;
				}
				else {
					pl1score++;
				}
			}
			score.setPl1TieScore(pl1score);
		}
		else if (whichPlayer == 2) {
			int pl1score = score.getPl1TieScore();
			int pl2Score = score.getPl2TieScore();
			
			if (pl2Score < 9 ) {
				pl2Score++;
			}
			else if (pl2Score == 9) {
				if (pl1score < 9) {
//					pl2Score++;
//					score.setGameIsEnded(true);
					calculatePl2TieScore(score);
					pl2Score = 0;
				}
				else if(pl1score == 10 || pl1score == 9){
					pl2Score++;
				}
			}
			else if (pl2Score > 9) {
				if (pl1score == pl2Score) {
					pl2Score++;
				}
				else if (pl2Score - pl1score == 1) {
//					pl2Score++;
//					score.setGameIsEnded(true);
					calculatePl2TieScore(score);
					pl2Score = 0;
				}
				else {
					pl2Score++;
				}
			}
			score.setPl2TieScore(pl2Score);
		}
		score = Service.setService(score);
		
		return score;
	}
	
	private static Score calculatePl1TieScore(Score score) {
		int setScore = score.getPl1_set_score();
		int scoreFlag = score.getFlag();
		score = Service.setService(score);
		score.setGameMode(Score.GAME_MODE.CLASSIC);
		score.setPl1_score(SCORE.ZERO);
		score.setPl2_score(SCORE.ZERO);
		score.setPl2TieScore(0);
		switch (scoreFlag) {
		case Score.SCORE_FLAGS.FIRST_SET:
			int set1Score = score.getPl1_set1();
			
			
				score.setFlag(SCORE_FLAGS.SECOND_SET);
				score.setPl1_set1(set1Score +1);
				setScore++;
				score.setPl1_set_score(setScore);
				
			return score;
		
		case Score.SCORE_FLAGS.SECOND_SET:
			int set2Score = score.getPl1_set2();
			
			
				score.setFlag(SCORE_FLAGS.THIRD_SET);
				score.setPl1_set2(set2Score +1);
				setScore++;
				score.setPl1_set_score(setScore);
			
			return score;
		
		case Score.SCORE_FLAGS.THIRD_SET:
			int set3Score = score.getPl1_set3();
			
			score.setFlag(SCORE_FLAGS.FOURTH_SET);
				score.setPl1_set3(set3Score +1);
				setScore++;
				score.setPl1_set_score(setScore);
			
			return score;
		
		case Score.SCORE_FLAGS.FOURTH_SET:
			int set4Score = score.getPl1_set4();
			
			
			score.setFlag(SCORE_FLAGS.FIFTH_SET);
				score.setPl1_set4(set4Score +1);
				score.setPl1_set_score(setScore + 1);
			
			return score;
			
		case Score.SCORE_FLAGS.FIFTH_SET:
			score.setPl1_set5(7);
			score.setPl1_set_score(setScore + 1);
			return score;
		
		default:
			return score;
		}
	}

	private static Score calculatePl2TieScore(Score score) {
		int setScore = score.getPl2_set_score();
		int scoreFlag = score.getFlag();
		score.setGameMode(Score.GAME_MODE.CLASSIC);
		score.setPl1_score(SCORE.ZERO);
		score.setPl2_score(SCORE.ZERO);
		score = Service.setService(score);
		score.setPl1TieScore(0);
		switch (scoreFlag) {
		case Score.SCORE_FLAGS.FIRST_SET:
			int set1Score = score.getPl2_set1();
			
			
				score.setFlag(SCORE_FLAGS.SECOND_SET);
				score.setPl2_set1(set1Score +1);
				setScore++;
				score.setPl2_set_score(setScore);
			return score;
		
		case Score.SCORE_FLAGS.SECOND_SET:
			int set2Score = score.getPl2_set2();
			
			
				score.setFlag(SCORE_FLAGS.THIRD_SET);
				score.setPl2_set2(set2Score +1);
				setScore++;
				score.setPl2_set_score(setScore);
			
			return score;
		
		case Score.SCORE_FLAGS.THIRD_SET:
			int set3Score = score.getPl2_set3();
			
			score.setFlag(SCORE_FLAGS.FOURTH_SET);
				score.setPl2_set3(set3Score +1);
				setScore++;
				score.setPl2_set_score(setScore);
			
			return score;
		
		case Score.SCORE_FLAGS.FOURTH_SET:
			int set4Score = score.getPl2_set4();
			
			
			score.setFlag(SCORE_FLAGS.FIFTH_SET);
				score.setPl2_set4(set4Score +1);
				score.setPl2_set_score(setScore + 1);
			
			return score;
			
		case Score.SCORE_FLAGS.FIFTH_SET:
			score.setPl2_set5(7);
			score.setPl2_set_score(setScore + 1);
			return score;
		
		default:
			return score;
		}
	}
}
