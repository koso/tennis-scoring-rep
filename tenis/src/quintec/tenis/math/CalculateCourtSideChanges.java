package quintec.tenis.math;

import quintec.tenis.Score;

public class CalculateCourtSideChanges {
	
	public static boolean changeSides(Score score) {
		boolean result = false;
		int sum = score.getSumOfSets();
		int gameMode = score.getGameMode();
		
		
		boolean setJustEnded = score.getPl1_score() == 0 && score.getPl2_score() == 0;
		if (setJustEnded && gameMode != Score.GAME_MODE.TIEBREAK) {
			if (sum == 1) {
				result = true;
			}
			else if ((sum - 1) > 0 && (sum - 1) % 2 == 0) {
				result = true;
			}
		}
		else if (gameMode == Score.GAME_MODE.TIEBREAK) {
			int sumTie = 0;
			sumTie += score.getPl1TieScore();
			sumTie += score.getPl2TieScore();
			
			if ((sumTie) % 6 == 0) {
				result = true;
			}
		}
		
		return result;
	}

}
