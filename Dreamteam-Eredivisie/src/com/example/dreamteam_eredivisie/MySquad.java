package com.example.dreamteam_eredivisie;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MySquad extends ListActivity {

	PlayersDatabaseOpenHelper mDatabase;
	MySquadDatabaseOpenHelper mMySquadDatabase;
	SimpleCursorAdapter mCursorAdapter;
	SharedPreferences mMySquad;
	Cursor mCursorAllPlayers;
	ListView mFilteredView;	
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_squad);
		
		mMySquadDatabase = new MySquadDatabaseOpenHelper(this);
		mCursorAllPlayers = mMySquadDatabase.getAllPlayers(this);
		String[] fromColumns = {"name", "club", "position", "side", "value"};
		int[] toControlIDs = {R.id.name, R.id.club, R.id.position, R.id.side, R.id.value};
		// use a SimpleCursorAdapter to show filtered players
		mCursorAdapter = new SimpleCursorAdapter (this, R.layout.list_view_layout, mCursorAllPlayers,
		       fromColumns,
		       toControlIDs, 0);
		setListAdapter(mCursorAdapter);
		
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		    	
		    	final Cursor clickedPlayer = (Cursor) parent.getAdapter().getItem(position);
		    	
		    	// alert dialog to delete player from your squad
		    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
				alertDialog.setTitle("Delete Player");
				alertDialog.setMessage("Do you want to delete " + clickedPlayer.getString(clickedPlayer.getColumnIndex("name")) + " from your squad?");
				alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						mMySquadDatabase.deletePlayer(context, 
								clickedPlayer.getString(clickedPlayer.getColumnIndex("name")),
								clickedPlayer.getString(clickedPlayer.getColumnIndex("club")),
								clickedPlayer.getString(clickedPlayer.getColumnIndex("position")),
								clickedPlayer.getString(clickedPlayer.getColumnIndex("side")),
								clickedPlayer.getInt(clickedPlayer.getColumnIndex("value")));
				    	
						Toast.makeText(context, "Player deleted from squad", Toast.LENGTH_SHORT).show();
					}
				});
				alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(context, "Deletion of player aborted", Toast.LENGTH_SHORT).show();
					}
				});
				alertDialog.setIcon(R.drawable.football_player);
				alertDialog.show();	
		    
		    }});
	}
}
