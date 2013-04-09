package quintec.tenis.math;

import quintec.tenis.Score;
import quintec.tenis.Score.SCORE_FLAGS;
import quintec.tenis.math.ScoreMath.SCORE;

public class CalculateThreeSetWithTie {

	public static Score calculate(Score score, int whichplayer) {
		score = ScoreMath.calculateScore(score, whichplayer);
		int set1 = 0;
		int set2 = 0;
		if (score.getFlag() == SCORE_FLAGS.FIRST_SET) {
			set1 = score.getPl1_set1();
			set2 = score.getPl2_set1();
		}
		else if (score.getFlag() == SCORE_FLAGS.SECOND_SET) {
			set1 = score.getPl1_set2();
			set2 = score.getPl2_set2();
		}
        else if (score.getFlag() == SCORE_FLAGS.THIRD_SET) {
        	set1 = score.getPl1_set3();
			set2 = score.getPl2_set3();
		}
        else if (score.getFlag() == SCORE_FLAGS.FOURTH_SET) {
        	set1 = score.getPl1_set4();
			set2 = score.getPl2_set4();
		}
        else if (score.getFlag() == SCORE_FLAGS.FIFTH_SET) {
        	set1 = score.getPl1_set5();
			set2 = score.getPl2_set5();
		}
		
		if (set1 == 6 && set2 == 6) {
			score.setGameMode(Score.GAME_MODE.TIEBREAK);
			score = TieMath.calculate(score, whichplayer);
		}
		
//		if (score.getPl1_set_score() == 2 && score.getPl2_set_score() == 2) {
//			score.setGameMode(Score.GAME_MODE.TIEBREAK);
//			score = TieMath.calculate(score, whichplayer);
//		}
		else if (score.getPl1_score() == SCORE.ZERO && score.getPl2_score() == SCORE.ZERO) {
			score.setGameMode(Score.GAME_MODE.CLASSIC);
			if (whichplayer == 1) {
				score = SetMath.calculatePl1(score);
			}
			else if (whichplayer == 2) {
				score = SetMath.calculatePl2(score);
			}
//			if (score.getPl1_set_score() == 3 || score.getPl2_set_score() == 3) {
//				score.setGameIsEnded(true);
//			}
		}
		if (score.getPl1_set_score() == 3 || score.getPl2_set_score() == 3) {
			score.setGameIsEnded(true);
		}
		
		return score;
	}
}
