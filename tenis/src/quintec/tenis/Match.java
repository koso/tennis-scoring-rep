package quintec.tenis;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import android.content.Context;

import quintec.tenis.database.PostgreSqlConnection;

public class Match {
	private String player_one_team_one;
	private  String player_two_team_one;
	private String player_one_team_two;
	private String player_two_team_two;
	private  boolean isSingle;
	private  int numberOfSets;
	private  int lastSet;
	private int state;
	private Timestamp startDate;
	private Timestamp finishDate;
	private int pl11id;
	private int pl12id;
	private int pl21id;
	private int pl22id;
	private int id;
	
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Timestamp finishDate) {
		this.finishDate = finishDate;
	}
	public int getPl11id() {
		return pl11id;
	}
	public void setPl11id(int pl11id) {
		this.pl11id = pl11id;
	}
	public int getPl12id() {
		return pl12id;
	}
	public void setPl12id(int pl12id) {
		this.pl12id = pl12id;
	}
	public int getPl21id() {
		return pl21id;
	}
	public void setPl21id(int pl21id) {
		this.pl21id = pl21id;
	}
	public int getPl22id() {
		return pl22id;
	}
	public void setPl22id(int pl22id) {
		this.pl22id = pl22id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public  String getPlayer_one_team_one() {
		return player_one_team_one;
	}
	public  void setPlayer_one_team_one(String player_one_team_one) {
		this.player_one_team_one = player_one_team_one;
	}
	public  String getPlayer_two_team_one() {
		return player_two_team_one;
	}
	public void setPlayer_two_team_one(String player_two_team_one) {
		this.player_two_team_one = player_two_team_one;
	}
	public  String getPlayer_one_team_two() {
		return player_one_team_two;
	}
	public void setPlayer_one_team_two(String player_one_team_two) {
		this.player_one_team_two = player_one_team_two;
	}
	public String getPlayer_two_team_two() {
		return player_two_team_two;
	}
	public  void setPlayer_two_team_two(String player_two_team_two) {
		this.player_two_team_two = player_two_team_two;
	}
	public boolean isSingle() {
		return isSingle;
	}
	public  void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}
	public int getNumberOfSets() {
		return numberOfSets;
	}
	public void setNumberOfSets(int numberOfSets) {
		this.numberOfSets = numberOfSets;
	}
	public  int getLastSet() {
		return lastSet;
	}
	public void setLastSet(int lastSet) {
		this.lastSet = lastSet;
	}

	public static void setMatchState(int matchState, int matchId, Context context){
		String sql = "UPDATE match SET state = "+matchState +" WHERE id ="+matchId+";";
		
		Connection connection = PostgreSqlConnection.connect(context);
		Statement statement = null;
		try {
		    statement = connection.createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	public static class STATE {
		public static final int STARTED = 1;
		public static final int ENDED = 3;
		public static final int INTERUPTED = 2;
		public static final int NOT_STARTED = 0;
	}
}
