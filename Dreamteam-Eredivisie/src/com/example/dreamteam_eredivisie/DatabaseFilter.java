package com.example.dreamteam_eredivisie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class DatabaseFilter extends Activity {
	
	Spinner mSpinnerPosition;
	Spinner mSpinnerSide;
	Spinner mSpinnerClub;
	SharedPreferences mSpinner;
	String mStar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database_filter);
		
		// fill spinners with data
		mSpinnerPosition = (Spinner) findViewById(R.id.spinner_position);
		ArrayAdapter<CharSequence> mSpinnerPositionArrayAdapter = ArrayAdapter.createFromResource(this,
		        R.array.position, android.R.layout.simple_spinner_item);
		mSpinnerPositionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerPosition.setAdapter(mSpinnerPositionArrayAdapter);
		
		mSpinnerSide = (Spinner) findViewById(R.id.spinner_side);
		ArrayAdapter<CharSequence> mSpinnerSideArrayAdapter = ArrayAdapter.createFromResource(this,
		        R.array.side, android.R.layout.simple_spinner_item);
		mSpinnerSideArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerSide.setAdapter(mSpinnerSideArrayAdapter);
		
		mSpinnerClub = (Spinner) findViewById(R.id.spinner_club);
		ArrayAdapter<CharSequence> mSpinnerClubArrayAdapter = ArrayAdapter.createFromResource(this,
		        R.array.club, android.R.layout.simple_spinner_item);
		mSpinnerClubArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerClub.setAdapter(mSpinnerClubArrayAdapter);
		
		// react on buttonClick
		Button buttonSearch = (Button) findViewById(R.id.button_search);
		buttonSearch.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent activityChangeIntent = new Intent(DatabaseFilter.this, FilteredDatabase.class);
		    	DatabaseFilter.this.startActivity(activityChangeIntent);
		    }
		});
	}
	
	@Override
	protected void onPause(){
		mSpinner = getSharedPreferences("MySpinners", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = mSpinner.edit();
		mStar = "Not Specified";
		
		//store spinner values in SharedPreferences
		if (mSpinnerPosition.getSelectedItem().toString().equals(mStar)){
			editor.putString("spinnerPosition", "'%'");
		}
		else{
			editor.putString("spinnerPosition", "'" + mSpinnerPosition.getSelectedItem().toString() + "'");
		}
		if (mSpinnerSide.getSelectedItem().toString().equals(mStar)){
			editor.putString("spinnerSide", "'%'");
		}
		else{
			editor.putString("spinnerSide", "'" + mSpinnerSide.getSelectedItem().toString() + "'");
		}
		if (mSpinnerClub.getSelectedItem().toString().equals(mStar)){
			editor.putString("spinnerClub", "'%'");
		}
		else{
			editor.putString("spinnerClub", "'" + mSpinnerClub.getSelectedItem().toString() + "'");
		}
		editor.commit();
		
		super.onPause();
	}
}
