package com.example.dreamteam_eredivisie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PostTeamActivity extends Activity {
	
	PlayersDatabaseOpenHelper mDatabase;
	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_team);
		
		mContext = this;
		mDatabase = new PlayersDatabaseOpenHelper(this);
		final Cursor mySquad = mDatabase.getAllPlayersMySquad(mContext);
		
		// button which starts the build of an email to send to organiser
		Button buttonSend = (Button) findViewById(R.id.button_send);
		buttonSend.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	if (mySquad.getCount() == 11){
			    	EditText emailReceiver = (EditText) findViewById(R.id.editText_email_receiver);
			    	String emailReceiverFilled = emailReceiver.getText().toString();
			    	
			    	Intent emailIntent = new Intent(Intent.ACTION_SEND);
			    	emailIntent.setType("message/rfc822");
					emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailReceiverFilled});
			    	emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Dreamteam-Eredivisie");
			    	emailIntent.putExtra(Intent.EXTRA_TEXT, cursorToString(mySquad));
			    	startActivity(Intent.createChooser(emailIntent, "Send My Squad"));
		    	}
		    	else {
		    		Toast.makeText(mContext, "Your squad is not completed! Please add another " + (11 - mySquad.getCount()) + " players", Toast.LENGTH_SHORT).show();
		    	}
		    }
		});
	}
	
	// function to fill the content of the email with the selected squad
	public String cursorToString(Cursor cursor){
		StringBuilder buildingEmailText = new StringBuilder();
		while (!cursor.isAfterLast()){
			buildingEmailText.append(cursor.getString(cursor.getColumnIndex("name")));
			buildingEmailText.append("\t");
			buildingEmailText.append(cursor.getString(cursor.getColumnIndex("position")));
			buildingEmailText.append("\t");
			buildingEmailText.append(cursor.getString(cursor.getColumnIndex("side")));
			buildingEmailText.append("\n");
			cursor.moveToNext();
		}
		String text = buildingEmailText.toString();
		return text;
	}
}

