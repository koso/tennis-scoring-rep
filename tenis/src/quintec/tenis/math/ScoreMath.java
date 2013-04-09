package quintec.tenis.math;

import quintec.tenis.Score;

public class ScoreMath {

	public static Score calculateScore(Score score, int whichPlayer) {
		
		int pl1Score = score.getPl1_score();
		int pl2Score = score.getPl2_score();
		
		if (whichPlayer == 1) {

			if (pl1Score == SCORE.FOURTY) {

				if (pl2Score < SCORE.FOURTY) {
					score.setPl1_score(SCORE.ZERO);
					score.setPl2_score(SCORE.ZERO);
				}

				else if (pl2Score == SCORE.FOURTY) {
					score.setPl1_score(SCORE.ADVANTAGE);
				}

				else if (pl2Score == SCORE.ADVANTAGE) {
					score.setPl2_score(SCORE.FOURTY);
				}
			}

			else if (pl1Score == SCORE.ZERO || pl1Score == SCORE.FIFTEEN || pl1Score == SCORE.THIRTY) {
				score.setPl1_score(pl1Score + 1);
			}

			else if (pl1Score == SCORE.ADVANTAGE) {
				score.setPl1_score(SCORE.ZERO);
				score.setPl2_score(SCORE.ZERO);
			}
		}
		else if (whichPlayer==2) {

			if (pl2Score == SCORE.FOURTY) {
				
				if (pl1Score < SCORE.FOURTY) {
					score.setPl2_score(SCORE.ZERO);
					score.setPl1_score(SCORE.ZERO);
				}
				
				else if (pl1Score == SCORE.FOURTY) {
					score.setPl2_score(SCORE.ADVANTAGE);
				}
				
				else if (pl1Score == SCORE.ADVANTAGE) {
					score.setPl1_score(SCORE.FOURTY);
				}
			}
			
			else if (pl2Score == SCORE.ZERO || pl2Score == SCORE.FIFTEEN || pl2Score == SCORE.THIRTY) {
				score.setPl2_score(pl2Score+1);
			}
			
			else if (pl2Score == SCORE.ADVANTAGE) {
				score.setPl2_score(SCORE.ZERO);
				score.setPl1_score(SCORE.ZERO);
			}
		}
		
		return score;
	}
	
	public static class SCORE{
		public static final int ZERO= 0;
		public static final int FIFTEEN = 1;
		public static final int THIRTY = 2;
		public static final int FOURTY = 3;
		public static final int ADVANTAGE= 4;
	}
}
