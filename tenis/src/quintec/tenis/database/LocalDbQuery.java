package quintec.tenis.database;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import quintec.tenis.Score;

public class LocalDbQuery {
	
	public static Score getPreviouseScore(Context context, int matchId, int id) {
		Score score = new Score();
		int rowId;
		if (id == 0) {
			rowId = getSecondBiggestID(context,matchId)-1;
		}
		else {
			rowId = id-1;
		}
		
		String sql = "SELECT * FROM score where id = "+rowId+"  limit 1;";
			
		LocalDb myDbHelper = new LocalDb(context);
		SQLiteDatabase db = myDbHelper.openDataBase();
		
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			score.setId(cursor.getInt(0));
			score.setPl1_score(cursor.getString(1));
			score.setPl2_score(cursor.getString(2));
			score.setPl1_set1(cursor.getInt(3));
			score.setPl1_set2(cursor.getInt(4));
			score.setPl1_set3(cursor.getInt(5));
			score.setPl1_set4(cursor.getInt(6));
			score.setPl1_set5(cursor.getInt(7));
			score.setPl2_set1(cursor.getInt(8));
			score.setPl2_set2(cursor.getInt(9));
			score.setPl2_set3(cursor.getInt(10));
			score.setPl2_set4(cursor.getInt(11));
			score.setPl2_set5(cursor.getInt(12));
			score.setPl1_set_score(cursor.getInt(13));
			score.setPl2_set_score(cursor.getInt(14));
			score.setPl1TieScore(cursor.getInt(15));
			score.setPl2TieScore(cursor.getInt(16));
			score.setGameProfile(cursor.getInt(17));
			score.setGameMode(cursor.getInt(18));
			score.setWhichPlayerServing(cursor.getInt(19));
			score.setFlag(cursor.getInt(20));
			score.setMatchId(cursor.getInt(21));
			score.setTimeStamp(cursor.getString(22));
			score.setOnWeb(cursor.getInt(23));
		}
		cursor.close();
		db.close();
		return score;
		
	}
	
	public static Score getPausedScore(Context context, int matchId) {
		Score score = new Score();
		String sql = "SELECT * FROM score where matchID = "+matchId+" and isPaused="+ 1+ " order by id desc limit 1;";
			  
		LocalDb myDbHelper = new LocalDb(context);
		SQLiteDatabase db = myDbHelper.openDataBase();
		
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.getCount() == 0) {
			score = null;
		}
		else {
			while (cursor.moveToNext()) {
				score.setId(cursor.getInt(0));
				score.setPl1_score(cursor.getString(1));
				score.setPl2_score(cursor.getString(2));
				score.setPl1_set1(cursor.getInt(3));
				score.setPl1_set2(cursor.getInt(4));
				score.setPl1_set3(cursor.getInt(5));
				score.setPl1_set4(cursor.getInt(6));
				score.setPl1_set5(cursor.getInt(7));
				score.setPl2_set1(cursor.getInt(8));
				score.setPl2_set2(cursor.getInt(9));
				score.setPl2_set3(cursor.getInt(10));
				score.setPl2_set4(cursor.getInt(11));
				score.setPl2_set5(cursor.getInt(12));
				score.setPl1_set_score(cursor.getInt(13));
				score.setPl2_set_score(cursor.getInt(14));
				score.setPl1TieScore(cursor.getInt(15));
				score.setPl2TieScore(cursor.getInt(16));
				score.setGameProfile(cursor.getInt(17));
				score.setGameMode(cursor.getInt(18));
				score.setWhichPlayerServing(cursor.getInt(19));
				score.setFlag(cursor.getInt(20));
				score.setMatchId(cursor.getInt(21));
				score.setTimeStamp(cursor.getString(22));
				score.setOnWeb(cursor.getInt(23));
			}
		}
		cursor.close();
		db.close();
		return score;
		
	}
	
	public static int getSecondBiggestID( Context context, int matchId) {
		int id = 0;
        String sql = "SELECT id FROM score where matchID = "+matchId+" order by id desc limit 1;";
		
        LocalDb myDbHelper = new LocalDb(context);
		SQLiteDatabase db = myDbHelper.openDataBase();
		
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.getCount() != 0) {
			while (cursor.moveToNext()) {
				id = cursor.getInt(0);
			}
		}
		cursor.close();
		db.close();
        
		return id;
	}
	
	public static void deleteRowsOnMatchPause(Context context) {
		String sql = "delete FROM score where isPaused =0;";
		
        LocalDb myDbHelper = new LocalDb(context);
		SQLiteDatabase db = myDbHelper.openDataBase();
		
		db.execSQL(sql);
		
		db.close();
	}
	
	public static void deleteRowsOnMatchFinish(Context context, int matchId) {
		String sql = "delete FROM score where matchId =" + matchId + ";";
		
        LocalDb myDbHelper = new LocalDb(context);
		SQLiteDatabase db = myDbHelper.openDataBase();
		
		db.execSQL(sql);
		
		db.close();
	}

}
