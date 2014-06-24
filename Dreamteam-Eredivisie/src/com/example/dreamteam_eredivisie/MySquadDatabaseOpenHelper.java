package com.example.dreamteam_eredivisie;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySquadDatabaseOpenHelper extends SQLiteOpenHelper{
	
	String[] sqlStatements;
	SharedPreferences mMySquad;
	String mGetPosition;
	String mGetSide;
	String mGetClub;
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Dreamteam";
    private static final String DATABASE_TABLE_NAME = "mysquad";
    private static final String DATABASE_TABLE_CREATE =
    	"CREATE TABLE " + DATABASE_TABLE_NAME + "(" +
		"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
		"name TEXT," +
		"club TEXT," +
		"position TEXT," +
		"side TEXT," +
		"value INTEGER);";

    MySquadDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  create database
    	db.execSQL(DATABASE_TABLE_CREATE);     
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " +  DATABASE_TABLE_NAME);
	    onCreate(db);
	}
    
	// get all players to show in activity MySquad
    public Cursor getAllPlayers(Context context){
    	SQLiteDatabase db = this.getReadableDatabase();
    	String query = "SELECT * FROM " + DATABASE_TABLE_NAME;
    	Cursor allPlayers = db.rawQuery(query, null);
    	allPlayers.moveToFirst();
    	return allPlayers;
    }
    
    public void addPlayer(Context context){
    	mMySquad = context.getSharedPreferences("MySquad", Context.MODE_PRIVATE);
    	
    	// insert selected player via SharedPreferences into database mysquad
    	SQLiteDatabase db = this.getWritableDatabase();
    	String query = "INSERT INTO mysquad (name,club,position,side,value) VALUES (\'" + 
    			mMySquad.getString("name", "") + "\', \'" +
    			mMySquad.getString("club", "") + "\', \'" +
    			mMySquad.getString("position", "") + "\', \'" +
    			mMySquad.getString("side", "") + "\', " +
    			mMySquad.getInt("value", 0) + ")";
    	db.execSQL(query);
    	
    }
    
    public void deletePlayer(Context context, String name, String club, String position, String side, int value){
    	
    	// delete selected player from database mysquad
    	SQLiteDatabase db = this.getWritableDatabase();
    	String query = "DELETE FROM mysquad WHERE name = " + name + 
    			" AND club = " + club +
    			" AND position = " + position +
    			" AND side = " + side +
    			" AND value = " + value;
    	db.execSQL(query);
    }
}
