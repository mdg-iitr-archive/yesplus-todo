package com.example.myyesproject;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	Context ctx;
	LayoutInflater inflater;
	ArrayList<Task> tasks;
	CheckBox chkDone;

	ListAdapter(Context context, ArrayList<Task> task) {
		ctx = context;
		tasks = task;
		inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return tasks.size();
	}

	@Override
	public Object getItem(int position) {
		return tasks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.list_text, parent, false);
		}
		Task task = getTask(position);
		((TextView) view.findViewById(R.id.Task)).setText(task.taskName);
		((TextView) view.findViewById(R.id.date)).setText(task.date);
		((TextView) view.findViewById(R.id.time))
				.setText("time : " + task.time);
		chkDone = (CheckBox) view.findViewById(R.id.check);
		chkDone.setOnCheckedChangeListener(chkChangeListener);
		chkDone.setTag(position);
		chkDone.setChecked(task.doneTask);

		((TextView) view.findViewById(R.id.Task)).setTextColor(Color.BLUE);
		((TextView) view.findViewById(R.id.Task)).setTextSize((float) 15);
		return view;
	}

	public void SetDone() {
		chkDone.setChecked(true);
	}

	Task getTask(int position) {
		return ((Task) getItem(position));
	}

	ArrayList<Task> getTaskDone() {
		ArrayList<Task> taskDone = new ArrayList<Task>();
		for (Task task : tasks) {
			if (task.doneTask)
				taskDone.add(task);
		}
		return taskDone;
	}

	OnCheckedChangeListener chkChangeListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(final CompoundButton buttonView,
				boolean isChecked) {
			getTask((Integer) buttonView.getTag()).doneTask = isChecked;
			Log.i("msg", "list generated");
			AlertDialog.Builder askDone = new AlertDialog.Builder(ctx);
			askDone.setTitle("Task Info");
			askDone.setMessage("Have you completed this task ?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									setActionDialog();

								}

								private void setActionDialog() {
									// TODO Auto-generated method stub
									AlertDialog.Builder delete = new AlertDialog.Builder(
											ctx);
									delete.setTitle("Delete");
									delete.setMessage(
											"What do you want to do with this task ?")
											.setCancelable(false)
											.setPositiveButton(
													"Delete",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {
															// TODO
															// Auto-generated
															// method stub
															int taskId = getTask((Integer) buttonView
																	.getTag()).id;
															try {
																DatabaseHelper entry = new DatabaseHelper(
																		ctx);
																entry.open();
																entry.updateTask(
																		taskId,
																		getTask((Integer) buttonView
																				.getTag()).taskName,
																		getTask((Integer) buttonView
																				.getTag()).priority,
																		getTask((Integer) buttonView
																				.getTag()).date,
																		getTask((Integer) buttonView
																				.getTag()).time,
																		1);
																((Activity) ctx)
																		.finish();
																entry.deleteTask(taskId);
																Intent intent = new Intent(
																		ctx,
																		MainActivity.class);
																ctx.startActivity(intent);
																entry.close();
															} catch (Exception e) {
																e.printStackTrace();
															}

														}
													})
											.setNegativeButton(
													"Move to End",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {
															// TODO
															// Auto-generated
															// method stub
															int taskId = getTask((Integer) buttonView
																	.getTag()).id;
															try {
																DatabaseHelper entry = new DatabaseHelper(
																		ctx);
																entry.open();
																entry.moveTaskToEnd(
																		taskId,
																		getTask((Integer) buttonView
																				.getTag()).taskName,
																		getTask((Integer) buttonView
																				.getTag()).time,
																		getTask((Integer) buttonView
																				.getTag()).date,
																		1);
																((Activity) ctx)
																		.finish();
																Intent intent = new Intent(
																		ctx,
																		MainActivity.class);
																ctx.startActivity(intent);
																entry.close();
															} catch (Exception e) {
																e.printStackTrace();
															}

														}
													});
									AlertDialog deleteTask = delete.create();
									deleteTask.show();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									chkDone.setChecked(false);
									((Activity) ctx).finish();
									Intent intent = new Intent(ctx,
											MainActivity.class);
									ctx.startActivity(intent);
									dialog.cancel();
								}
							});
			AlertDialog alertDialog = askDone.create();
			alertDialog.show();
		}

	};
}