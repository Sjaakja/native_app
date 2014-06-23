package com.example.dreamteam_eredivisie;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FilteredDatabase extends ListActivity {

	PlayersDatabaseOpenHelper mDatabase;
	SimpleCursorAdapter mCursorAdapter;
	Cursor mCursorFiltered;
	ListView mFilteredView;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtered_database);
		
		mDatabase = new PlayersDatabaseOpenHelper(this);
		mCursorFiltered = mDatabase.getPlayers(this);
		String[] fromColumns = {"name", "club", "position", "side", "value"};
		int[] toControlIDs = {R.id.name, R.id.club, R.id.position, R.id.side, R.id.value};
		// use a SimpleCursorAdapter
		mCursorAdapter = new SimpleCursorAdapter (this, R.layout.list_view_layout, mCursorFiltered,
		       fromColumns,
		       toControlIDs, 0);
		mFilteredView = (ListView) findViewById(android.R.id.list);
		mFilteredView.setAdapter(mCursorAdapter);
	}
}
