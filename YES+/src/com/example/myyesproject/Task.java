package com.example.myyesproject;

public class Task {
	String taskName;
	String date;
	int priority;
	boolean doneTask;
	String time;
	int id;
	int done;

	Task(String _taskName, int _priority, String _date, String _time,
			boolean _donetask, int _id, int _done) {
		taskName = _taskName;
		date = _date;
		priority = _priority;
		doneTask = _donetask;
		time = _time;
		id = _id;
		done = _done;
	}
}
