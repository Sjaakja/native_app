package com.example.dreamteam_eredivisie;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class PlayersDatabaseOpenHelper extends SQLiteOpenHelper{
	
	SharedPreferences mMySquad;
	String[] mSqlStatements;
	SharedPreferences mSpinner;
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Dreamteam";
    private static final String DATABASE_TABLE_NAME_PLAYERS = "players";
    private static final String DATABASE_TABLE_NAME_MYSQUAD = "mysquad";
    private static final String DATABASE_TABLE_CREATE_PLAYERS =
    	"CREATE TABLE " + DATABASE_TABLE_NAME_PLAYERS + "(" +
		"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
		"name TEXT," +
		"club TEXT," +
		"position TEXT," +
		"side TEXT," +
		"value INTEGER);";
    private static final String DATABASE_TABLE_CREATE_MYSQUAD =
    	"CREATE TABLE " + DATABASE_TABLE_NAME_MYSQUAD + "(" +
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
		mSqlStatements = context.getResources().getStringArray(stringArrayID);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  create tables
    	db.execSQL(DATABASE_TABLE_CREATE_PLAYERS);
    	db.execSQL(DATABASE_TABLE_CREATE_MYSQUAD);
        
    	// load in table players
        for (int i = 0; i < mSqlStatements.length; i++){
        	db.execSQL(mSqlStatements[i]);
        }        
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " +  DATABASE_TABLE_NAME_PLAYERS);
		db.execSQL("DROP TABLE IF EXISTS " +  DATABASE_TABLE_NAME_MYSQUAD);
	    onCreate(db);
	}
    
	// get a filtered list of players to show
    public Cursor getFilteredPlayers(Context context){
    	mSpinner = context.getSharedPreferences("MySpinners", Context.MODE_PRIVATE);
    	SQLiteDatabase db = this.getReadableDatabase();
    	String query = "SELECT * FROM " + DATABASE_TABLE_NAME_PLAYERS +
    		" WHERE position LIKE \'" + mSpinner.getString("spinnerPosition", "FAILED") + "\'" +
    		" AND side LIKE \'" + mSpinner.getString("spinnerSide", "FAILED") + "\'" +
    		" AND club LIKE \'" + mSpinner.getString("spinnerClub", "FAILED") + "\'";
    	Cursor selected = db.rawQuery(query, null);
    	selected.moveToFirst();
    	return selected;
    }
    
    // get all players to show in activity MySquad
    public Cursor getAllPlayersMySquad(Context context){
    	SQLiteDatabase db = this.getReadableDatabase();
    	String query = "SELECT * FROM " + DATABASE_TABLE_NAME_MYSQUAD;
    	Cursor allPlayers = db.rawQuery(query, null);
    	allPlayers.moveToFirst();
    	return allPlayers;
    }
    
    public void addPlayerMySquad(Context context, String name, String club, String position, String side, int value){
    	
    	// add selected player to table mysquad
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	String queryCheck = "SELECT * FROM " + DATABASE_TABLE_NAME_MYSQUAD
        		+ " WHERE name LIKE \'" + name + "\'";
    	Cursor checkCursorMySquadName = db.rawQuery(queryCheck, null);
    	
    	String queryCheckPositionSide = "SELECT * FROM " + DATABASE_TABLE_NAME_MYSQUAD +
        		" WHERE position LIKE \'" + position + "\'" +
        		" AND side LIKE \'" + side + "\'";
    	Cursor checkCursorMySquadPositionSide = db.rawQuery(queryCheckPositionSide, null);
    	
    	if (checkCursorMySquadName.getCount() == 0 && checkCursorMySquadPositionSide.getCount() == 0){
    		String query = "INSERT INTO "+ DATABASE_TABLE_NAME_MYSQUAD + " (name,club,position,side,value) VALUES (\'" + 
        			name + "\', \'" +
        			club + "\', \'" +
        			position + "\', \'" +
        			side + "\', " +
        			value + ")";
        	db.execSQL(query);   
    	}	
    	else{
    		if(checkCursorMySquadName.getCount() != 0){
    			Toast.makeText(context, "You already have this player in your squad", Toast.LENGTH_LONG).show();
    		}
    		else{
    			Toast.makeText(context, "You already have a player for this position in your squad", Toast.LENGTH_LONG).show();
    		}
    	}   	 	
    }
    
    // delete selected player from table mysquad
    public void deletePlayerMySquad(Context context, String name, String club, String position, String side, int value){
    	SQLiteDatabase db = this.getWritableDatabase();
    	String query = "DELETE FROM "+ DATABASE_TABLE_NAME_MYSQUAD + " WHERE name = \'" + name + "\'" +
    			" AND club = \'" + club + "\'" +
    			" AND position = \'" + position + "\'" +
    			" AND side = \'" + side + "\'" +
    			" AND value = \'" + value + "\'";
    	db.execSQL(query);
    }
    
    // get the player (from given position) from table mysquad
    public Cursor getYourSquadFiller(Context context, String position, String side){
    	SQLiteDatabase db = this.getReadableDatabase();
    	String query = "SELECT * FROM " + DATABASE_TABLE_NAME_MYSQUAD +
    		" WHERE position LIKE \'" + position + "\'" +
    		" AND side LIKE \'" + side + "\'";
    	Cursor selected = db.rawQuery(query, null);
    	selected.moveToFirst();
    	return selected;
	}
}


