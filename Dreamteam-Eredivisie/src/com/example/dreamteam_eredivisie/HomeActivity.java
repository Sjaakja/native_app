package com.example.dreamteam_eredivisie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Button buttonMySquad = (Button) findViewById(R.id.button_my_squad);
		buttonMySquad.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent activityChangeIntent = new Intent(HomeActivity.this, MySquadActivity.class);
		    	HomeActivity.this.startActivity(activityChangeIntent);
		    }
		});
		
		Button buttonDatabase = (Button) findViewById(R.id.button_database_filter);
		buttonDatabase.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent activityChangeIntent = new Intent(HomeActivity.this, DatabaseFilterActivity.class);
		    	HomeActivity.this.startActivity(activityChangeIntent);
		    }
		});
		
		Button buttonPostTeam = (Button) findViewById(R.id.button_post_team);
		buttonPostTeam.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent activityChangeIntent = new Intent(HomeActivity.this, PostTeamActivity.class);
		    	HomeActivity.this.startActivity(activityChangeIntent);
		    }
		});
	}
}
