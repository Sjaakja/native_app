package com.example.dreamteam_eredivisie;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MySquadActivity extends ListActivity {
	
	PlayersDatabaseOpenHelper mDatabase;
	SimpleCursorAdapter mCursorAdapter;
	Cursor mCursorAllPlayers;
	Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_squad);
		
		mContext = this;
		mDatabase = new PlayersDatabaseOpenHelper(this);
		mCursorAllPlayers = mDatabase.getAllPlayersMySquad(this);
		String[] fromColumns = {"name", "club", "position", "side", "value"};
		int[] toControlIDs = {R.id.name, R.id.club, R.id.position, R.id.side, R.id.value};
		
		// use a SimpleCursorAdapter to show filtered players in ListView in MySquadActivity
		mCursorAdapter = new SimpleCursorAdapter (this, R.layout.list_view_layout, mCursorAllPlayers,
		       fromColumns,
		       toControlIDs, 0);
		setListAdapter(mCursorAdapter);
		
		// respond to click on player in ListView
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		    	
		    	final Cursor clickedPlayer = (Cursor) parent.getAdapter().getItem(position);
		    	
		    	// alert dialog to delete player from your squad
		    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
				alertDialog.setTitle("Delete Player");
				alertDialog.setMessage("Do you want to delete " + clickedPlayer.getString(clickedPlayer.getColumnIndex("name")) + " from your squad?");
				alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						// deletion of player from MySquad
						mDatabase.deletePlayerMySquad(mContext, 
								clickedPlayer.getString(clickedPlayer.getColumnIndex("name")),
								clickedPlayer.getString(clickedPlayer.getColumnIndex("club")),
								clickedPlayer.getString(clickedPlayer.getColumnIndex("position")),
								clickedPlayer.getString(clickedPlayer.getColumnIndex("side")),
								clickedPlayer.getInt(clickedPlayer.getColumnIndex("value")));
																				    	
						Toast.makeText(mContext, "Player deleted from squad", Toast.LENGTH_SHORT).show();
						
						// reload mCursorAdapter
						mCursorAdapter.changeCursor(mDatabase.getAllPlayersMySquad(mContext));
						mCursorAdapter.notifyDataSetChanged();
					}
				});
				alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(mContext, "Deletion of player aborted", Toast.LENGTH_SHORT).show();
					}
				});
				alertDialog.setIcon(R.drawable.red_card);
				alertDialog.show();	
		    
		    }});
		
		// button to get to activity ShowTeam
		Button showTeam = (Button) findViewById(R.id.button_show_team);
		showTeam.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent activityChangeIntent = new Intent(MySquadActivity.this, ShowTeamActivity.class);
		    	MySquadActivity.this.startActivity(activityChangeIntent);
		    }
		});
	}
}
