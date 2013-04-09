package quintec.tenis.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class PostgreSqlConnection {
	public static final String HOST_KEY = "HOST_KEY";
	public static final String PORT_KEY = "PORT_KEY";
	public static final String DB_NAME_KEY = "DB_NAME_KEY";
	public static final String USER_KEY = "USER_KEY";
	public static final String PASS_KEY = "PASS_KEY";
	
	private static  String URL = "jdbc:postgresql://webdev.qintec.sk:5432/tenis";
	private static String USER = "tenista";
	private static  String PASS = "t3n1st4";

	public static Connection connect(Context context) {
		SharedPreferences dataStore = context.getSharedPreferences("pref", 0);
		String host = dataStore.getString(HOST_KEY, "webdev.qintec.sk");
		String port = dataStore.getString(PORT_KEY, "5432");
		String dbName = dataStore.getString(DB_NAME_KEY, "tenis");
		String userName = dataStore.getString(USER_KEY, USER);
		String pass = dataStore.getString(PASS_KEY, PASS);
		
		String url = "jdbc:postgresql://"+ host + ":"+ port + "/"+ dbName ;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection connection;
		try {
			connection = DriverManager.getConnection(url, userName, pass);
		} catch (SQLException e) {
			e.printStackTrace();
			connection = null;
		}
		return connection;
	}
}
