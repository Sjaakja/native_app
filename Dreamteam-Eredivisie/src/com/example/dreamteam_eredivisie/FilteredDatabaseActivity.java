package com.example.dreamteam_eredivisie;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class FilteredDatabaseActivity extends ListActivity {

	PlayersDatabaseOpenHelper mDatabase;
	SimpleCursorAdapter mCursorAdapter;
	Cursor mCursorFiltered;
	Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtered_database);
		
		this.mContext = this;
		
		mDatabase = new PlayersDatabaseOpenHelper(this);
		mCursorFiltered = mDatabase.getFilteredPlayers(this);
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
		    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
				alertDialog.setTitle("Add Player");
				alertDialog.setMessage("Do you want to add " + clickedPlayer.getString(clickedPlayer.getColumnIndex("name")) + " to your squad?");
				alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
										    	
				    	// add player to squad
						mDatabase.addPlayerMySquad(mContext, 
								clickedPlayer.getString(clickedPlayer.getColumnIndex("name")), 
								clickedPlayer.getString(clickedPlayer.getColumnIndex("club")), 
								clickedPlayer.getString(clickedPlayer.getColumnIndex("position")), 
								clickedPlayer.getString(clickedPlayer.getColumnIndex("side")), 
								clickedPlayer.getInt(clickedPlayer.getColumnIndex("value")));
				    	
						Toast.makeText(mContext, "Player added to squad", Toast.LENGTH_SHORT).show();
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
