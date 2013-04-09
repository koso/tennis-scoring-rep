package quintec.tenis.math;

import quintec.tenis.Score;

public class CalculateBallsChange {
	
	private static final int NUMBER_OF_SETS_FOR_PREPARING = 2;
	
	public enum BallsChange {
		PREPAR_BALLS,
		CHANGE_BALLS,
		NONE;
	}
	
	public enum BallsChangeMode {
		SEVEN_NINE(7, 9, 1),
		NINE_ELEVEN(9, 11, 2);
		
		private int firstNum;
		private int secondNum;
		private int saveValue;
		
		private BallsChangeMode(int firstNum, int secondNum, int saveValue) {
			this.firstNum = firstNum;
			this.secondNum = secondNum;
			this.saveValue = saveValue;
		}
		
		public int getFirstNum() {
			return firstNum;
		}
		
		public int getSecondNum() {
			return secondNum;
		}
		
		public int getSaveValue() {
			return saveValue;
		}
		
		public static BallsChangeMode getBallChangeMod(int value) {
			BallsChangeMode result = null;
			BallsChangeMode[] values = values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].getSaveValue() == value) {
					result = values[i];
					break;
				}
			}
			
			return result;
		}
	}
	
	public static BallsChange checkBalls(Score score, BallsChangeMode mode) {
		BallsChange result = BallsChange.NONE;
		int sum = score.getSumOfSets();
		
		boolean setJustEnded = score.getPl1_score() == 0 && score.getPl2_score() == 0;
		if (setJustEnded) {
			if (sum == mode.getFirstNum() - NUMBER_OF_SETS_FOR_PREPARING) {
				result = BallsChange.PREPAR_BALLS;
			}
			else if (sum == mode.getFirstNum()) {
				result = BallsChange.CHANGE_BALLS;
			}
			else if ((sum - mode.getFirstNum()) > 0 && (sum - mode.getFirstNum()) % mode.getSecondNum() == 0) {
				result = BallsChange.CHANGE_BALLS;
			}
			else if ((sum - mode.getFirstNum() + NUMBER_OF_SETS_FOR_PREPARING) > 0 && (sum - mode.getFirstNum() + NUMBER_OF_SETS_FOR_PREPARING) % mode.getSecondNum() == 0) {
				result = BallsChange.PREPAR_BALLS;
			}
		}
		
		return result;
	}

}
