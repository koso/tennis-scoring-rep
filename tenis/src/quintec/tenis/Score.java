package quintec.tenis;

import java.io.Serializable;

import quintec.tenis.math.ScoreMath.SCORE;

public class Score implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pl1_set1 = 0;
	private int pl1_set2 = 0;
	private int pl1_set3 = 0;
	private int pl1_set4 = 0;
	private int pl1_set5 = 0;
	private int pl1_score = 0;
	private int pl1_set_score = 0;
	
	private int pl2_set1 = 0;
	private int pl2_set2 = 0;
	private int pl2_set3 = 0;
	private int pl2_set4 = 0;
	private int pl2_set5 = 0;
	private int pl2_score = 0;
	private int pl2_set_score = 0;
	
	private String pl1_score_String = "0" ;
	private String pl2_score_String = "0";
	private int pl1TieScore = 0;
	private int pl2TieScore = 0;
	private int gameProfile = 1;
	private int gameMode = 1;
	private int whichPlayerServing = 1;
	private int tieBreakServingNumber = 0;
	private boolean gameIsEnded = false;
	private boolean changeSides = false;
	
	private int matchId = 0;
	private int id = 0;
	private int isOnWeb;
	private String timeStamp;
	
	private int flag = 1;
	
	public int getPl1_set1() {
		return pl1_set1;
	}
	public void setPl1_set1(int pl1_set1) {
		this.pl1_set1 = pl1_set1;
	}
	public int getPl1_set2() {
		return pl1_set2;
	}
	public void setPl1_set2(int pl1_set2) {
		this.pl1_set2 = pl1_set2;
	}
	public int getPl1_set3() {
		return pl1_set3;
	}
	public void setPl1_set3(int pl1_set3) {
		this.pl1_set3 = pl1_set3;
	}
	public int getPl1_score() {
		return pl1_score;
	}
	public void setPl1_score(int pl1_score) {
		setPl1_score_String(pl1_score);
		this.pl1_score = pl1_score;
	}
	public int getPl2_set1() {
		return pl2_set1;
	}
	public void setPl2_set1(int pl2_set1) {
		this.pl2_set1 = pl2_set1;
	}
	public int getPl2_set2() {
		return pl2_set2;
	}
	public void setPl2_set2(int pl2_set2) {
		this.pl2_set2 = pl2_set2;
	}
	public int getPl2_set3() {
		return pl2_set3;
	}
	public void setPl2_set3(int pl2_set3) {
		this.pl2_set3 = pl2_set3;
	}
	public int getPl2_score() {
		return pl2_score;
	}
	public void setPl2_score(int pl2_score) {
		this.pl2_score = pl2_score;
		setPl2_score_String(pl2_score);
	}
	public int getPl2_set5() {
		return pl2_set5;
	}
	public void setPl2_set5(int pl2_set5) {
		this.pl2_set5 = pl2_set5;
	}
	public int getPl2_set4() {
		return pl2_set4;
	}
	public void setPl2_set4(int pl2_set4) {
		this.pl2_set4 = pl2_set4;
	}
	public int getPl1_set5() {
		return pl1_set5;
	}
	public void setPl1_set5(int pl1_set5) {
		this.pl1_set5 = pl1_set5;
	}
	public int getPl1_set4() {
		return pl1_set4;
	}
	public void setPl1_set4(int pl1_set4) {
		this.pl1_set4 = pl1_set4;
	}
	
	public int getSumOfSets() {
		int sum = 0;
		sum += this.getPl1_set1();
		sum += this.getPl2_set1();
		sum += this.getPl1_set2();
		sum += this.getPl2_set2();
		sum += this.getPl2_set3();
		sum += this.getPl1_set3();
		sum += this.getPl1_set4();
		sum += this.getPl2_set4();
		sum += this.getPl1_set5();
		sum += this.getPl2_set5();
		
		return sum;
	}
	
	public String getPl1_score_String() {
		return pl1_score_String;
	}
	public void setPl1_score_String(int score) {
		if (score == SCORE.ZERO) {
			this.pl1_score_String = "0";
		}
		else if (score == SCORE.FIFTEEN) {
			this.pl1_score_String = "15";
		}
		else if (score == SCORE.THIRTY) {
			this.pl1_score_String = "30";
		}
		else if (score == SCORE.FOURTY) {
			this.pl1_score_String = "40";
		}
		else if (score == SCORE.ADVANTAGE) {
			this.pl1_score_String = "A";
		}
		else {
			this.pl2_score_String = "0";
		}
	}
	public String getPl2_score_String() {
	    return pl2_score_String;
	}
	public void setPl2_score_String(int score) {
		if (score == SCORE.ZERO) {
			this.pl2_score_String = "0";
		}
		else if (score == SCORE.FIFTEEN) {
			this.pl2_score_String = "15";
		}
		else if (score == SCORE.THIRTY) {
			this.pl2_score_String = "30";
		}
		else if (score == SCORE.FOURTY) {
			this.pl2_score_String = "40";
		}
		else if (score == SCORE.ADVANTAGE) {
			this.pl2_score_String = "A";
		}
		else {
			this.pl2_score_String = "0";
		}
	}
	
	public void setPl1_score(String score){
		if (score.equalsIgnoreCase("0")) {
			this.pl1_score = SCORE.ZERO;
			this.pl1_score_String = "0";
		}
		if (score.equalsIgnoreCase("15")) {
			this.pl1_score = SCORE.FIFTEEN;
			this.pl1_score_String = "15";
		}
		if (score.equalsIgnoreCase("30")) {
			this.pl1_score = SCORE.THIRTY;
			this.pl1_score_String = "30";
		}
		if (score.equalsIgnoreCase("40")) {
			this.pl1_score = SCORE.FOURTY;
			this.pl1_score_String = "40";
		}
		if (score.equalsIgnoreCase("A")) {
			this.pl1_score = SCORE.ADVANTAGE;
			this.pl1_score_String = "A";
		}
	}
	public void setPl2_score(String score){
		if (score.equalsIgnoreCase("0")) {
			this.pl2_score = SCORE.ZERO;
			this.pl2_score_String = "0";
		}
		if (score.equalsIgnoreCase("15")) {
			this.pl2_score = SCORE.FIFTEEN;
			this.pl2_score_String = "15";
		}
		if (score.equalsIgnoreCase("30")) {
			this.pl2_score = SCORE.THIRTY;
			this.pl2_score_String = "30";
		}
		if (score.equalsIgnoreCase("40")) {
			this.pl2_score = SCORE.FOURTY;
			this.pl2_score_String = "40";
		}
		if (score.equalsIgnoreCase("A")) {
			this.pl2_score = SCORE.ADVANTAGE;
			this.pl2_score_String = "A";
		}
	}
	public int getFlag() {
		
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public int getPl1_set_score() {
		return pl1_set_score;
	}
	public void setPl1_set_score(int pl1_set_score) {
		this.pl1_set_score = pl1_set_score;
	}

	public int getPl2_set_score() {
		return pl2_set_score;
	}
	public void setPl2_set_score(int pl2_set_score) {
		this.pl2_set_score = pl2_set_score;
	}

	public int getGameProfile() {
		return gameProfile;
	}
	public void setGameProfile(int gameProfile) {
		this.gameProfile = gameProfile;
	}

	public boolean isGameIsEnded() {
		return gameIsEnded;
	}
	public void setGameIsEnded(boolean gameIsEnded) {
		this.gameIsEnded = gameIsEnded;
	}

	public int getGameMode() {
		return gameMode;
	}
	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	public int getPl2TieScore() {
		return pl2TieScore;
	}
	public void setPl2TieScore(int pl2TieScore) {
		this.pl2TieScore = pl2TieScore;
	}

	public int getPl1TieScore() {
		return pl1TieScore;
	}
	public void setPl1TieScore(int pl1TieScore) {
		this.pl1TieScore = pl1TieScore;
	}

	public int getWhichPlayerServing() {
		return whichPlayerServing;
	}
	public void setWhichPlayerServing(int whichPlayerServing) {
		this.whichPlayerServing = whichPlayerServing;
	}

	public int getTieBreakServingNumber() {
		return tieBreakServingNumber;
	}
	public void setTieBreakServingNumber(int tieBreakServingNumber) {
		this.tieBreakServingNumber = tieBreakServingNumber;
	}

	public int getMatchId() {
		return matchId;
	}
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int isOnWeb() {
		return isOnWeb;
	}
	public void setOnWeb(int isOnWeb) {
		this.isOnWeb = isOnWeb;
	}

	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public void setChangeSides(boolean changeSides) {
		this.changeSides = changeSides;
	}
	
	public boolean getChangeSides() {
		return this.changeSides;
	}

	public static class SCORE_FLAGS {
		public static final int FIRST_SET = 1;
		public static final int SECOND_SET = 2;
		public static final int THIRD_SET = 3;
		public static final int FOURTH_SET = 4;
		public static final int FIFTH_SET = 5;
	}
	
	public static class SERVICE_FLAGS {
		public static final int PLAYER_ONE_SERVE = 1;
		public static final int PLAYER_TWO_SERVE = 2;
	}
	
	public static class GAME_PROFILE {
		public static final int TWO_SETS_WITHOUT_TIE = 1;
		public static final int THREE_SETS_WITHOUT_TIE = 2;
		public static final int TWO_SETS_WITH_TIE = 3;
		public static final int THREE_SETS_WITH_TIE = 4;
		public static final int TWO_SETS_WITH_SUPER_TIE = 5;
		public static final int THREE_SETS_WITH_SUPER_TIE = 6;
	}
	
	public static class GAME_MODE {
		public static final int CLASSIC = 1;
		public static final int TIEBREAK = 2;
		public static final int SUPER_TIEBREAK = 3;
	}
}
