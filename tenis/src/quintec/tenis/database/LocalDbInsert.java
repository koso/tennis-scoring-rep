package quintec.tenis.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import quintec.tenis.Log;
import quintec.tenis.Score;

public class LocalDbInsert {

	public static int insertScore(Score s, Context context, int isOnWeb, int isPaused, int idScore) {
		String timestamp = Log.GetUTCdatetimeAsString();
		int id;
		if (idScore == 0) {
			id = getBiggestID(context)+1;
		}
		else {
			id = idScore;
		}
		LocalDb myDbHelper = new LocalDb(context);
		SQLiteDatabase db = myDbHelper.openDataBase();
		String sql = "INSERT INTO score VALUES ("+id+",'"+ s.getPl1_score_String() + "','" + s.getPl2_score_String() +
				"',"+ s.getPl1_set1()+","+s.getPl1_set2()+","+s.getPl1_set3()+","+s.getPl1_set4()+","+s.getPl1_set5()+
				"," + s.getPl2_set1()+","+s.getPl1_set2()+","+s.getPl2_set3()+","+s.getPl2_set4()+","+s.getPl2_set5()+        
				"," + s.getPl1_set_score()+","+s.getPl2_set_score()+","+s.getPl1TieScore()+","+s.getPl2TieScore()+                   
				"," + s.getGameProfile()+","+s.getGameMode()+","+s.getWhichPlayerServing()+","+s.getFlag()+       
				"," + s.getMatchId()+",'"+timestamp+"',"+isOnWeb +","+ isPaused+");";
		db.execSQL(sql);
		db.close();
		return id;
		
	}
	
	public static int getBiggestID( Context context) {
		int id = 0;
        String sql = "SELECT id FROM score order by id desc limit 1;";
		
        LocalDb myDbHelper = new LocalDb(context);
		SQLiteDatabase db = myDbHelper.openDataBase();
		
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			id = cursor.getInt(0);
		}
		cursor.close();
		db.close();
        
       
		return id;
	}
}
