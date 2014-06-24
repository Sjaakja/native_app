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

public class FilteredDatabase extends ListActivity {

	PlayersDatabaseOpenHelper mDatabase;
	MySquadDatabaseOpenHelper mMySquadDatabase;
	SimpleCursorAdapter mCursorAdapter;
	SharedPreferences mMySquad;
	Cursor mCursorFiltered;
	ListView mFilteredView;	
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtered_database);
		
		mDatabase = new PlayersDatabaseOpenHelper(this);
		mCursorFiltered = mDatabase.getPlayers(this);
		String[] fromColumns = {"name", "club", "position", "side", "value"};
		int[] toControlIDs = {R.id.name, R.id.club, R.id.position, R.id.side, R.id.value};
		// use a SimpleCursorAdapter to show filtered players
		mCursorAdapter = new SimpleCursorAdapter (this, R.layout.list_view_layout, mCursorFiltered,
		       fromColumns,
		       toControlIDs, 0);
		setListAdapter(mCursorAdapter);
		
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		    	
		    	final Cursor clickedPlayer = (Cursor) parent.getAdapter().getItem(position);
		    	
		    	// alert dialog to add player to your squad
		    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
				alertDialog.setTitle("Add Player");
				alertDialog.setMessage("Do you want to add " + clickedPlayer.getString(clickedPlayer.getColumnIndex("name")) + " to your squad?");
				alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						// store player's values in SharedPreferences
						mMySquad = context.getSharedPreferences("MySquad", Context.MODE_PRIVATE);
				    	SharedPreferences.Editor editorMySquad = mMySquad.edit();
				    	
				    	editorMySquad.putString("name", clickedPlayer.getString(clickedPlayer.getColumnIndex("name")));
				    	editorMySquad.putString("club", clickedPlayer.getString(clickedPlayer.getColumnIndex("club")));
				    	editorMySquad.putString("position", clickedPlayer.getString(clickedPlayer.getColumnIndex("position")));
				    	editorMySquad.putString("side", clickedPlayer.getString(clickedPlayer.getColumnIndex("side")));
				    	editorMySquad.putInt("value", clickedPlayer.getInt(clickedPlayer.getColumnIndex("value")));
				    	editorMySquad.commit();
				    	
				    	// add player to squad
				    	mMySquadDatabase = new MySquadDatabaseOpenHelper(context);
						mMySquadDatabase.addPlayer(context);
				    	
						Toast.makeText(context, "Player added to squad", Toast.LENGTH_SHORT).show();
					}
				});
				alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				alertDialog.setIcon(R.drawable.football_player);
				alertDialog.show();	
		    
		    }});
	}
}
