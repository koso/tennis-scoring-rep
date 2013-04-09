package quintec.tenis.math;

import quintec.tenis.Score;

public class BrainMath {

	public static Score calculate(Score score, int whichPlayerScored) {
		switch (score.getGameProfile()) {
		case Score.GAME_PROFILE.TWO_SETS_WITHOUT_TIE:
			CalculateTwoSetWithoutTie.calculate(score, whichPlayerScored);
			break;
        
		case Score.GAME_PROFILE.THREE_SETS_WITHOUT_TIE:
        	CalculateThreeSetWithoutTie.calculate(score, whichPlayerScored);
			break;
       
        case Score.GAME_PROFILE.TWO_SETS_WITH_SUPER_TIE:
        	CalculateTwoSetWithSuperTie.calculate(score, whichPlayerScored);
			break;
			
        case Score.GAME_PROFILE.THREE_SETS_WITH_SUPER_TIE:
        	CalculateTwoSetWithSuperTie.calculate(score, whichPlayerScored);
			break;
		
        case Score.GAME_PROFILE.TWO_SETS_WITH_TIE:
        	CalculateTwoSetWithTie.calculate(score, whichPlayerScored);
			break;
			
        case Score.GAME_PROFILE.THREE_SETS_WITH_TIE:
        	CalculateThreeSetWithTie.calculate(score, whichPlayerScored);
			break;

		default:
			break;
		}
		
		if (CalculateCourtSideChanges.changeSides(score)) 
			score.setChangeSides(true);
		else 
			score.setChangeSides(false);
		
		return score;
	}
}
