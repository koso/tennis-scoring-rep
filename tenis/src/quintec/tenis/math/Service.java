package quintec.tenis.math;

import quintec.tenis.Score;

public class Service {

	public static Score setService(Score score){
		int whichPlayerServing = score.getWhichPlayerServing();
		if (score.getGameMode() == Score.GAME_MODE.CLASSIC) {
			if (whichPlayerServing == 1) {
				score.setWhichPlayerServing(2);
			}
			else {
				score.setWhichPlayerServing(1);
			}
		}
		else if (score.getGameMode() == Score.GAME_MODE.SUPER_TIEBREAK || score.getGameMode() == Score.GAME_MODE.TIEBREAK) {
			if (score.getTieBreakServingNumber() == 0) {
				if (whichPlayerServing == 1) {
					score.setWhichPlayerServing(2);
				}
				else {
					score.setWhichPlayerServing(1);
				}
				score.setTieBreakServingNumber(1);
			}
			else if (score.getTieBreakServingNumber() == 1) {
				score.setTieBreakServingNumber(0);
			}
		}
		
		return score;
		
	}
}
