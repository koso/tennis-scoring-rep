package quintec.tenis.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.content.Context;

import quintec.tenis.Log;
import quintec.tenis.Score;

public class Qscore {
	
	public static int insertScoreChange(Score score, Context context ) {
		int isOnWeb;
		int id = getBiggestID(context)+1;
		int setPl1Score;
		int setPl2Score;
		String scorePl1;
		String scorePl2;
		int gameMode;
		int setOrder = score.getFlag();
		
		switch (setOrder) {
		case Score.SCORE_FLAGS.FIRST_SET:
			setPl1Score = score.getPl1_set1();
			setPl2Score = score.getPl2_set1();
			break;
        case Score.SCORE_FLAGS.SECOND_SET:
        	setPl1Score = score.getPl1_set2();
			setPl2Score = score.getPl2_set2();
			break;
        case Score.SCORE_FLAGS.THIRD_SET:
        	setPl1Score = score.getPl1_set3();
			setPl2Score = score.getPl2_set3();
			break;
        case Score.SCORE_FLAGS.FOURTH_SET:
        	setPl1Score = score.getPl1_set4();
			setPl2Score = score.getPl2_set4();
			break;
        case Score.SCORE_FLAGS.FIFTH_SET:
        	setPl1Score = score.getPl1_set5();
			setPl2Score = score.getPl2_set5();
			break;

		default:
			setPl1Score = 0;
			setPl2Score = 0;;
			break;
		}
		
		if (score.getGameMode() == Score.GAME_MODE.CLASSIC) {
			scorePl1 = score.getPl1_score_String();
			scorePl2 = score.getPl2_score_String();
			gameMode = 0;
		}
		else if (score.getGameMode() == Score.GAME_MODE.SUPER_TIEBREAK || score.getGameMode() == Score.GAME_MODE.TIEBREAK) {
			scorePl1 = String.valueOf(score.getPl1TieScore());
			scorePl2 = String.valueOf(score.getPl2TieScore());
			gameMode = 1;
		}
		else {
			scorePl1 = "0";
			scorePl2 = "0";
			gameMode = 0;
		}
		
        String insert = "INSERT into scores VALUES (" + id + ", '" + Log.GetUTCdatetimeAsString() + "', '" 
                         + scorePl1 +  "','" + scorePl2 + "'," + setPl1Score + "," + setPl2Score + 
                         "," + score.getPl1_set_score() + "," +  score.getPl2_set_score() + "," + 
                         score.getFlag() + "," + gameMode + "," + score.getWhichPlayerServing() + "," + 
                         score.getMatchId() + " );";
		
        Connection connection = PostgreSqlConnection.connect(context);
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute(insert);
			isOnWeb = 1;
		} catch (SQLException e) {
			e.printStackTrace();
			isOnWeb = 0;
		}
		finally{
			try {
			if (connection != null) {
				connection.close();
				
			}
			if (statement != null) {
				statement.close();
			}
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		return isOnWeb;
	}

	private static int getBiggestID(Context context) {
		int id = 0;
        String sql = "SELECT id FROM scores order by id desc limit 1;";
		
        Connection connection = PostgreSqlConnection.connect(context);
		Statement statement = null;
		ResultSet result = null;
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(sql);
			
			while (result.next()) {
				id = result.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (result != null) {
				result.close();
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
}
