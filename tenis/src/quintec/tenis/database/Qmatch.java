package quintec.tenis.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import quintec.tenis.Match;
import quintec.tenis.Player;

public class Qmatch {

	public static List<Match> getList(Context context) {
		List<Match> notStartedMatchsList = getNotStartedMatches(context);
		List<Match> pausedMatchList = getPausedMatches(context);
		List<Match> allMatchsList = new ArrayList<Match>(pausedMatchList);
		if (pausedMatchList != null) {
			allMatchsList.addAll(notStartedMatchsList);
		}
		return allMatchsList;
		
	}
	
	public static List<Match> getNotStartedMatches(Context context){
        String sqlMatchesQuery = "SELECT * FROM match WHERE state = '" + Match.STATE.NOT_STARTED + "' ORDER BY start_date_time ASC;";
		
		List<Match> matchList = new ArrayList<Match>();
		Connection connection = PostgreSqlConnection.connect(context);
		ResultSet result = null;
		Statement statement = null;
		if (connection != null) {
			
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(sqlMatchesQuery);
			
			while (result.next()) {

				Match match = new Match();
				int matchId = result.getInt("id");
				match.setId(matchId);
				
				int pl1Id = result.getInt("id_player11");
				match.setPl11id(pl1Id);
				
				int pl2Id = result.getInt("id_player21");
				match.setPl21id(pl2Id);
				
				int pl12Id = result.getInt("id_player12");
				match.setPl12id(pl12Id);
				
				int pl22Id = result.getInt("id_player22");
				match.setPl22id(pl22Id);
				
				String isSingle = result.getString("isSingle");
				if (isSingle.equals("0")) {
					match.setSingle(false);
				}
				else {
					match.setSingle(true);
				}
				int state = result.getInt("state");
				match.setState(state);
				
				Player pl11 = Qplayer.getPlayer(pl1Id, context);
				match.setPlayer_one_team_one(pl11.getSurname());
				
				Player pl21 = Qplayer.getPlayer(pl2Id, context);
				match.setPlayer_one_team_two(pl21.getSurname());
				
				if (isSingle.equals("0")) {
					Player pl12 = Qplayer.getPlayer(pl12Id, context);
					match.setPlayer_two_team_one(pl12.getSurname());
					
					Player pl22 = Qplayer.getPlayer(pl22Id, context);
					match.setPlayer_two_team_two(pl22.getSurname());
				}
				matchList.add(match);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		}
		return matchList;
	}
	
	public static List<Match> getPausedMatches(Context context){
        String sqlMatchesQuery = "SELECT * FROM match WHERE state ='" + Match.STATE.INTERUPTED + "' ORDER BY start_date_time ASC;";
		
		List<Match> matchList = new ArrayList<Match>();
		Connection connection = PostgreSqlConnection.connect(context);
		ResultSet result = null;
		Statement statement = null;
		if (connection != null) {
			
		
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_READ_ONLY, 
	                ResultSet.HOLD_CURSORS_OVER_COMMIT);
			result = statement.executeQuery(sqlMatchesQuery);
			
			while (result.next()) {

				Match match = new Match();
				int matchId = result.getInt("id");
				match.setId(matchId);
				
				int pl1Id = result.getInt("id_player11");
				match.setPl11id(pl1Id);
				
				int pl2Id = result.getInt("id_player21");
				match.setPl21id(pl2Id);
				
				int pl12Id = result.getInt("id_player12");
				match.setPl12id(pl12Id);
				
				int pl22Id = result.getInt("id_player22");
				match.setPl22id(pl22Id);
				
				String isSingle = result.getString("isSingle");
				if (isSingle.equals("0")) {
					match.setSingle(false);
				}
				else {
					match.setSingle(true);
				}
				int state = result.getInt("state");
				match.setState(state);
				
				Player pl11 = Qplayer.getPlayer(pl1Id, context);
				match.setPlayer_one_team_one(pl11.getSurname());
				
				Player pl21 = Qplayer.getPlayer(pl2Id, context);
				match.setPlayer_one_team_two(pl21.getSurname());
				
				if (isSingle.equals("0")) {
					Player pl12 = Qplayer.getPlayer(pl12Id, context);
					match.setPlayer_two_team_one(pl12.getSurname());
					
					Player pl22 = Qplayer.getPlayer(pl22Id, context);
					match.setPlayer_two_team_two(pl22.getSurname());
				}
				
				matchList.add(match);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		}
		return matchList;
	}

}
