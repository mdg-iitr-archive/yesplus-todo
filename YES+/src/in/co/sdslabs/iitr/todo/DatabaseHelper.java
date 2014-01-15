package in.co.sdslabs.iitr.todo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper {

	static String KEY_ROWID = "_id";
	static String KEY_REASON = "REASON";
	static String KEY_NUMBER = "NUMBER";
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
					+ " TEXT NOT NULL, " + KEY_NUMBER + " INTEGER, " + KEY_DATE
					+ " TEXT NOT NULL, " + KEY_TIME + " TEXT NOT NULL, "
					+ KEY_DONE + " INTEGER);"

			);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public DatabaseHelper(Context c) {
		ourContext = c;
	}

	public DatabaseHelper open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close()

	{
		ourHelper.close();
	}

	public long createEntry(String task, int num, String date, String time,int done) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_REASON, task);
		cv.put(KEY_NUMBER, num);
		cv.put(KEY_DATE, date);
		cv.put(KEY_TIME, time);
		cv.put(KEY_DONE, done);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);

	}

	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_REASON, KEY_NUMBER,
				KEY_DATE, KEY_TIME };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iTask = c.getColumnIndex(KEY_REASON);
		int iNum = c.getColumnIndex(KEY_NUMBER);
		int iDate = c.getColumnIndex(KEY_DATE);
		int iTime = c.getColumnIndex(KEY_TIME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRow) + "  " + c.getString(iTask)
					+ "  " + c.getString(iNum) + "  " + c.getString(iDate)
					+ "\n";

		}
		Log.i("sneha", "print");
		return result;
	}

	

	public ArrayList<Product> getAllTasks() {
		// ArrayList<String> tasks = new ArrayList<String>();
		String[] columns = new String[] { KEY_ROWID, KEY_REASON, KEY_NUMBER,
				KEY_DATE, KEY_TIME,KEY_DONE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		
		ArrayList<Product> tasks = new ArrayList<Product>();
		String result = "";

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iTask = c.getColumnIndex(KEY_REASON);
		int iNum = c.getColumnIndex(KEY_NUMBER);
		int iDate = c.getColumnIndex(KEY_DATE);
		int iTime = c.getColumnIndex(KEY_TIME);
		int iDone=c.getColumnIndex(KEY_DONE);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			// result = c.getString(iRow) + "  " + c.getString(iTask) + "  "
			// + c.getString(iNum) + "  " + c.getString(iDate);

			tasks.add(new Product(c.getString(iTask), Integer.parseInt(c
					.getString(iNum)), c.getString(iDate), c.getString(iTime),
					false, Integer.parseInt(c.getString(iRow)),Integer.parseInt(c.getString(iDone))));

		}
		Log.i("sneha", "print");
		return tasks;

	}

	public void updateEntry(int row, String Task, int num, String date,
			String time,int done) {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_REASON, Task);
		cvUpdate.put(KEY_NUMBER, num);
		cvUpdate.put(KEY_DATE, date);
		cvUpdate.put(KEY_TIME, time);
	    cvUpdate.put(KEY_DONE, done);
		ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + row,
				null);
	}

	public void deleteEntry(int row) {
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + row, null);
	}

}
