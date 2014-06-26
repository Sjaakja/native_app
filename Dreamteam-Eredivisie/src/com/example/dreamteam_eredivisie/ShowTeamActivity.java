package com.example.dreamteam_eredivisie;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowTeamActivity extends Activity {

	PlayersDatabaseOpenHelper mDatabase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_team);
		
		mDatabase = new PlayersDatabaseOpenHelper(this);
		String[] positions = {
	            "Goalkeeper",
	            "Defender",
	            "Defensive_Midfielder",
	            "Midfielder",
	            "Attacker"
	    };
		
		String[] positionsDatabase = {
	            "Goalkeeper",
	            "Defender",
	            "Defensive Midfielder",
	            "Midfielder",
	            "Attacker"
	    };
		
		String[] sides = {
				"None",
	            "Right",
	            "Left",
	            "Centre"
		};
		
		// show your players sorted in the right TextViews
		for (int i = 0; i < positions.length; i++){
			for(int j = 0; j < sides.length; j++){
				String idMaker = "textView_" + positions[i] + "_" + sides[j];
				int idIdMaker = getResources().getIdentifier(idMaker, "id", "com.example.dreamteam_eredivisie");
				TextView player = (TextView) findViewById(idIdMaker);
				Cursor playerToBeFilled = mDatabase.getYourSquadFiller(this, positionsDatabase[i], sides[j]);
				
				if (player != null && playerToBeFilled.getCount() >= 1){
					player.setText(playerToBeFilled.getString(playerToBeFilled.getColumnIndex("name")));
				}
			}
		}
		
		// button to get to activity PostTeam
		Button buttonPostTeam = (Button) findViewById(R.id.button_showPost_team);
		buttonPostTeam.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent activityChangeIntent = new Intent(ShowTeamActivity.this, PostTeamActivity.class);
		    	ShowTeamActivity.this.startActivity(activityChangeIntent);
		    }
		});
	}	
}
