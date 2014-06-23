package com.example.dreamteam_eredivisie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Button buttonMySquad = (Button) findViewById(R.id.button_my_squad);
		buttonMySquad.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent activityChangeIntent = new Intent(Home.this, MySquad.class);
		    	Home.this.startActivity(activityChangeIntent);
		    }
		});
		
		Button buttonDatabase = (Button) findViewById(R.id.button_database_filter);
		buttonDatabase.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent activityChangeIntent = new Intent(Home.this, DatabaseFilter.class);
		    	Home.this.startActivity(activityChangeIntent);
		    }
		});
		
		Button buttonPostTeam = (Button) findViewById(R.id.button_post_team);
		buttonPostTeam.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent activityChangeIntent = new Intent(Home.this, PostTeam.class);
		    	Home.this.startActivity(activityChangeIntent);
		    }
		});
	}
}
