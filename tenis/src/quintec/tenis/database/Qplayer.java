package quintec.tenis.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import android.content.Context;

import quintec.tenis.Match;
import quintec.tenis.Player;

public class Qplayer {

	public static Player getPlayer(int id, Context context) {

		ResultSet result = null;
		Player player = new Player();
		String sqlPlayersQuery = "SELECT name1, surname FROM players where id=" +id+ " ;";

		Connection connection = PostgreSqlConnection.connect(context);
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
		
			result = statement.executeQuery(sqlPlayersQuery);
			while (result.next()) {
				player.setName1(result.getString("name1"));
				player.setSurname(result.getString("surname"));
			}
			
			connection.close();
			statement.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
			player = null;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return player;
	}
}
