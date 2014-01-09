package com.example.yes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Main extends Activity implements OnClickListener {

	EditText etTask;
	Button add;
	ImageButton speak;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		etTask = (EditText) findViewById(R.id.etTask);
		add = (Button) findViewById(R.id.bAdd);
		speak = (ImageButton) findViewById(R.id.ibSpeak);
		add.setOnClickListener(this);
		speak.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bAdd:
			Intent intent = new Intent(Main.this, AddTask.class);
			//put edit text string into bundle and transfer it
			startActivity(intent);
			break;
		case R.id.ibSpeak:
			// convert speech to text
			// merge with Anindya's code
			break;
		}

	}

}
