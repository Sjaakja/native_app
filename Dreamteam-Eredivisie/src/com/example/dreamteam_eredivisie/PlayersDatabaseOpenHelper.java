package com.example.dreamteam_eredivisie;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayersDatabaseOpenHelper extends SQLiteOpenHelper{
	
	String[] sqlStatements;
	SharedPreferences mSpinner;
	String mGetPosition;
	String mGetSide;
	String mGetClub;
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Dreamteam";
    private static final String DATABASE_TABLE_NAME = "players";
    private static final String DATABASE_TABLE_CREATE =
    	"CREATE TABLE " + DATABASE_TABLE_NAME + "(" +
		"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
		"name TEXT," +
		"club TEXT," +
		"position TEXT," +
		"side TEXT," +
		"value INTEGER);";

    PlayersDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
        // retrieve string array as base of database
		Integer stringArrayID = context.getResources().getIdentifier("players", "array", context.getPackageName()); 
		sqlStatements = context.getResources().getStringArray(stringArrayID);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  create database
    	db.execSQL(DATABASE_TABLE_CREATE);
        
        // load in database
        for (int i = 0; i < sqlStatements.length; i++){
        	db.execSQL(sqlStatements[i]);
        }
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " +  DATABASE_TABLE_NAME);
	    onCreate(db);
	}
    
    public Cursor getPlayers(Context context){
    	mSpinner = context.getSharedPreferences("MySpinners", Context.MODE_PRIVATE);
    	SQLiteDatabase db = this.getReadableDatabase();
    	String query = "SELECT _id, name, club, position, side, value FROM " + DATABASE_TABLE_NAME 
    		+ " WHERE position LIKE " + mSpinner.getString("spinnerPosition", "FAILED")
    		+ " AND side LIKE " + mSpinner.getString("spinnerSide", "FAILED")
    		+ " AND club LIKE " + mSpinner.getString("spinnerClub", "FAILED");
    	Cursor selected = db.rawQuery(query, null);
    	selected.moveToFirst();
    	return selected;
    }
}


