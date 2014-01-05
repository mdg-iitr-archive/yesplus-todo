package com.example.yes;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Main extends Activity implements OnCheckedChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		DatabaseHelper db = new DatabaseHelper(this);
		db.addTask(new Task(2, "fnds", "work on database"));
		db.addTask(new Task(0, "4ds", "work on the layouts"));
		db.addTask(new Task(1, "4dSDCAISNs", "work on the project"));
		List<Task> list = db.getAllTasks();

	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

}
