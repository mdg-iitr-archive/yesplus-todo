package com.example.yes;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AddTask extends Activity implements OnClickListener {
	Button add, urg, impt, datepick;
	boolean urgent = false;
	boolean important = false;
	int key; // priority
	int year, month, day;
	EditText etTask;
	TextView tvResult;
	String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtask);
		final Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		add = (Button) findViewById(R.id.bAdd);
		urg = (Button) findViewById(R.id.bUrg);
		impt = (Button) findViewById(R.id.bImpt);
		datepick = (Button) findViewById(R.id.bDate);
		tvResult = (TextView) findViewById(R.id.tvResult);
		etTask = (EditText) findViewById(R.id.etAddTask);
		//take the edit text string and display it in the edit text here in this activity
		add.setOnClickListener(this);
		urg.setOnClickListener(this);
		impt.setOnClickListener(this);
		datepick.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// set entries for the database table
		DatabaseHelper db = new DatabaseHelper(this);
		Task task = new Task();
		
		switch (v.getId()) {
		case R.id.bAdd:
			task.setTaskName(etTask.getText().toString());
			break;
		case R.id.bUrg:
			urg.setBackgroundColor(Color.CYAN);
			urgent = true;
			break;
		case R.id.bImpt:
			impt.setBackgroundColor(Color.BLUE);
			important = true;
			break;
		case R.id.bDate:
			showDialog(999);
			String string = Integer.toString(day) + "-"
					+ Integer.toString(month + 1) + "-"
					+ Integer.toString(year);
			task.setDueDate(date);
			break;
		}
		// set priority
		key = (urgent ? 1 : 0) * 2 + (important ? 1 : 0);
		task.setPriority(key);
		db.addTask(task);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 999:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			// set the date variable/string
			date = month + 1 + " - " + day + " - " + year;

			// set selected date into textview
			tvResult.setText(new StringBuilder().append(month + 1).append("-")
					.append(day).append("-").append(year).append(" "));

		}
	};

}
