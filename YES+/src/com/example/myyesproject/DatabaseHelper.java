package com.example.myyesproject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper {

	static String KEY_ROWID = "_id";
	static String KEY_REASON = "REASON";
	static String KEY_PRIORITY = "PRIORITY";
	static String KEY_DATE = "DATE";
	static String KEY_TIME = "TIME";
	static String KEY_DONE = "DONE";
	static String KEY_YEAR = "YEAR";
	static String DATABASE_NAME = "DATEBASEINFO";
	static String DATABASE_TABLE = "INFORMATION";
	static int DATABASE_VERSION = 1;
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_REASON
					+ " TEXT NOT NULL, " + KEY_PRIORITY + " INTEGER, "
					+ KEY_DATE + " TEXT NOT NULL, " + KEY_TIME
					+ " TEXT NOT NULL, " + KEY_DONE + " INTEGER);"

			);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public DatabaseHelper(Context context) {
		ourContext = context;
	}

	public DatabaseHelper open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createTask(String task, int priority, String date, String time,
			int done) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_REASON, task);
		cv.put(KEY_PRIORITY, priority);
		cv.put(KEY_DATE, date);
		cv.put(KEY_TIME, time);
		cv.put(KEY_DONE, done);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);

	}

	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_REASON, KEY_PRIORITY,
				KEY_DATE, KEY_TIME };
		Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null,
				null, null, null);
		String result = "";
		int iRow = cursor.getColumnIndex(KEY_ROWID);
		int iTask = cursor.getColumnIndex(KEY_REASON);
		int iNum = cursor.getColumnIndex(KEY_PRIORITY);
		int iDate = cursor.getColumnIndex(KEY_DATE);
		@SuppressWarnings("unused")
		int iTime = cursor.getColumnIndex(KEY_TIME);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			result = result + cursor.getString(iRow) + "  "
					+ cursor.getString(iTask) + "  " + cursor.getString(iNum)
					+ "  " + cursor.getString(iDate) + "\n";
		}
		Log.i("info", "data received");
		return result;
	}

	public ArrayList<Task> getAllTasks() {
		// ArrayList<String> tasks = new ArrayList<String>();
		String[] columns = new String[] { KEY_ROWID, KEY_REASON, KEY_PRIORITY,
				KEY_DATE, KEY_TIME, KEY_DONE };
		Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null,
				null, null, null);
		ArrayList<Task> tasks = new ArrayList<Task>();
		int iRow = cursor.getColumnIndex(KEY_ROWID);
		int iTask = cursor.getColumnIndex(KEY_REASON);
		int iPriority = cursor.getColumnIndex(KEY_PRIORITY);
		int iDate = cursor.getColumnIndex(KEY_DATE);
		int iTime = cursor.getColumnIndex(KEY_TIME);
		int iDone = cursor.getColumnIndex(KEY_DONE);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			tasks.add(new Task(cursor.getString(iTask), Integer.parseInt(cursor
					.getString(iPriority)), cursor.getString(iDate), cursor
					.getString(iTime), false, Integer.parseInt(cursor
					.getString(iRow)),
					Integer.parseInt(cursor.getString(iDone))));
		}
		Log.i("tasks", "All tasks added");
		return tasks;

	}

	public void updateTask(int row, String Task, int priority, String date,
			String time, int done) {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_REASON, Task);
		cvUpdate.put(KEY_PRIORITY, priority);
		cvUpdate.put(KEY_DATE, date);
		cvUpdate.put(KEY_TIME, time);
		cvUpdate.put(KEY_DONE, done);
		ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + row,
				null);
	}

	public void deleteTask(int row) {
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + row, null);
	}

	// create a task and modify its priority to 0
	public void moveTaskToEnd(int row, String Task, String time, String date,
			int done) {
		ContentValues moveValues = new ContentValues();
		moveValues.put(KEY_REASON, Task);
		moveValues.put(KEY_PRIORITY, 0);
		// String date = changeDate();
		moveValues.put(KEY_DATE, date);
		moveValues.put(KEY_TIME, time);
		moveValues.put(KEY_DONE, done);
		ourDatabase.update(DATABASE_TABLE, moveValues, KEY_ROWID + "=" + row,
				null);
	}

	// create a function that changes date to this text "deadline exceeded"
	// public String changeDate() {
	// String date;
	// int year, month, day;
	// year = 2015;
	// month = 11;
	// day = 31;
	// date = Integer.toString(day) + "-" + Integer.toString(month + 1) + "-"
	// + Integer.toString(year);
	// return date;
	//
	// }

}
