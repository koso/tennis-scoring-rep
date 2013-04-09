package quintec.tenis.math;

import quintec.tenis.Score;
import quintec.tenis.math.ScoreMath.SCORE;

public class CalculateThreeSetWithoutTie {
	
	public static Score calculate(Score score, int whichplayer) {
		score = ScoreMath.calculateScore(score, whichplayer);
		
		if (score.getPl1_score() == SCORE.ZERO && score.getPl2_score() == SCORE.ZERO) {
			boolean gameContinue = true;
			
			if (whichplayer == 1) {
				score = SetMath.calculatePl1(score);
				gameContinue = checkIfEndMatchWithouTie(score.getPl1_set_score(), 
						                                score.getPl2_set_score(), 
						                                score.getGameProfile());
			}
			else if (whichplayer == 2) {
				score = SetMath.calculatePl2(score);
				gameContinue = checkIfEndMatchWithouTie(score.getPl2_set_score(), 
						                                score.getPl1_set_score(), 
						                                score.getGameProfile());
			}
			if (!gameContinue) {
				score.setGameIsEnded(true);
			}
		}
		
		return score;
	}

    private static boolean checkIfEndMatchWithouTie(int pl1_setScore, int pl2_setScore, int gameProfile) {
		boolean gameContinue = true;
		
		if (pl1_setScore == 3) {
				gameContinue = false;
			}
			
		return gameContinue;
	}

}
