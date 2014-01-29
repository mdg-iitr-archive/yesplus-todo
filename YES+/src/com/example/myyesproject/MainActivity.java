package com.example.myyesproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {

	Button add, Graph;
	String data = "Not there";
	ListView display;
	ListView displayDone;
	String[] dates = {};
	ArrayList<Task> tasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		add = (Button) findViewById(R.id.bAdd);
		display = (ListView) findViewById(R.id.listResult);
		displayDone = (ListView) findViewById(R.id.listResultDone);
		add.setOnClickListener(this);
		
		Graph = (Button) findViewById(R.id.bGraph);
		Graph.setOnClickListener(this);
		@SuppressWarnings("unused")
		ArrayList<Task> listAct = new ArrayList<Task>();
		tasks = new ArrayList<Task>();
		ListAdapter boxAdapter;
		// ListAdapter boxAdapterDone;
		DatabaseHelper info = new DatabaseHelper(this);
		try {
			info.open();
			data = info.getData();
			tasks = info.getAllTasks();
			info.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		class DateSort implements Comparator<Task> {
			@SuppressWarnings("deprecation")
			public int compare(Task left, Task right) {
				String lday = left.date.split("-")[0];
				String lmonth = left.date.split("-")[1];
				String lyear = left.date.split("-")[2];
				Date ldate = new Date();
				ldate.setDate(Integer.parseInt(lday));
				ldate.setMonth(Integer.parseInt(lmonth));
				ldate.setYear(Integer.parseInt(lyear));
				String rday = right.date.split("-")[0];
				String rmonth = right.date.split("-")[1];
				String ryear = right.date.split("-")[2];
				Date rdate = new Date();
				rdate.setDate(Integer.parseInt(rday));
				rdate.setMonth(Integer.parseInt(rmonth));
				rdate.setYear(Integer.parseInt(ryear));
				if (left.date.equals(right.date)) {
					sortPriority(left, right);
					Log.i("p", "priority sorted");
				}
				if (sortPriority(left, right)) {
					return ldate.compareTo(rdate);
				} else {
					return rdate.compareTo(ldate);
				}

			}

			private boolean sortPriority(Task left, Task right) {
				// TODO Auto-generated method stub
				Log.i("pi", "sort priority called");
				if (left.priority > right.priority) {
					return true;
				} else {
					return false;
				}
			}
		}
		Collections.sort(tasks, new DateSort());
		listAct = tasks;
		boxAdapter = new ListAdapter(this, tasks);
		display.setAdapter(boxAdapter);
		display.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DailogSet((int) id);
			}

			private void DailogSet(final int id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ab = new AlertDialog.Builder(
						MainActivity.this);
				ab.setTitle("Modify");
				ab.setMessage("Do you want to modify this task ?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Bundle b = new Bundle();
										int id_delete = tasks.get(id).id;
										Intent i = new Intent(
												MainActivity.this,
												ModifyClass.class);
										b.putString("_id",
												Integer.toString(id_delete));
										b.putString("_edit",
												tasks.get(id).taskName);
										b.putString("_date", tasks.get(id).date);
										b.putString("_time", tasks.get(id).time);
										b.putString(
												"_priority",
												Integer.toString(tasks.get(id).priority));
										i.putExtras(b);
										finish();
										startActivity(i);
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.cancel();
									}
								});
				AlertDialog deleteTask = ab.create();
				deleteTask.show();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bAdd:
			Intent i = new Intent(MainActivity.this, SecondClass.class);
			startActivity(i);
			finish();
			break;
		case R.id.bGraph:
			Intent a = new Intent(MainActivity.this, Graph.class);
			startActivity(a);
			finish();
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater mf = getMenuInflater();
		mf.inflate(R.menu.mainpage_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.today_task:
			Intent i = new Intent(MainActivity.this, ShowTodayTask.class);
			startActivity(i);
			break;
		case R.id.Setting:
			Intent e = new Intent(MainActivity.this, NotifyTimeSetting.class);
			startActivity(e);
			break;
		}
		return false;
	}

}
