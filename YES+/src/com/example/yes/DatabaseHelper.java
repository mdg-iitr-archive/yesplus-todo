package com.example.yes;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "taskManager";
	private static final String TABLE = "task_list";
	private static final String KEY_ID = "id";
	private static final String KEY_PRIORITY = "priority";
	private static final String KEY_DATE = "date";
	private static final String KEY_TASK = "task";
	private static final String[] COLUMNS = { KEY_ID, KEY_PRIORITY, KEY_DATE,
			KEY_TASK };

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_TABLE = "CREATE TABLE " + TABLE + " (" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PRIORITY
				+ " INTEGER, " + KEY_DATE + " TEXT NOT NULL, " + KEY_TASK
				+ " TEXT NOT NULL);";
		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS" + TABLE);
		this.onCreate(db);
	}

	public void addTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRIORITY, task.getPriority());
		values.put(KEY_DATE, task.getDate());
		values.put(KEY_TASK, task.getTaskName());
		db.insert(TABLE, null, values);
		db.close();
		Log.i("add", task.getTaskName());

	}

	public List<Task> getAllTasks() {
		List<Task> tasks = new LinkedList<Task>();
		String query = "Select * from " + TABLE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Task task = null;
		if (cursor.moveToFirst()) {
			do {
				task = new Task();
				// add fields
				task.setID(Integer.parseInt(cursor.getString(0)));
				task.setPriority(Integer.parseInt(cursor.getString(1)));
				task.setDueDate(cursor.getString(2));
				task.setTaskName(cursor.getString(3));
				tasks.add(task);
			} while (cursor.moveToNext());
		}
		return tasks;

	}

	public void deleteTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE, KEY_ID, new String[] { String.valueOf(task.getId()) });
		db.close();

	}

	public int updateTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRIORITY, task.getPriority());
		values.put(KEY_DATE, task.getDate());
		values.put(KEY_TASK, task.getTaskName());
		int i = db.update(TABLE, values, KEY_ID,
				new String[] { String.valueOf(task.getId()) });
		
		//code to sort the database in order of priority
		db.query(TABLE,
				new String[] { KEY_ID, KEY_PRIORITY, KEY_DATE, KEY_TASK },
				null, null, null, null, KEY_PRIORITY + " DESC");
		//set adapter to display contents
		
		db.close();
		return i;

	}
}
