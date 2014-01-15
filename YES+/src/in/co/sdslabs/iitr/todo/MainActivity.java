package in.co.sdslabs.iitr.todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	EditText task;
	Button add;
	ImageButton speak;
	TextView result;
	String data = "Not there";
	ListView display;
	String[] dates = {};
	ArrayList<Product> products;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// task=(EditText)findViewById(R.id.etTask);
		add = (Button) findViewById(R.id.bAdd);
		// speak=(ImageButton)findViewById(R.id.ibSpeak); */
		result = (TextView) findViewById(R.id.result);
		display = (ListView) findViewById(R.id.listResult);
		ArrayList<Product> listAct = new ArrayList<Product>();

		products = new ArrayList<Product>();
		ListAdapter boxAdapter;
		ArrayList<String> datesarray = new ArrayList<String>();
		DatabaseHelper info = new DatabaseHelper(this);
		String d = "";
		try {
			info.open();
			data = info.getData();
			// listAct=info.getAllTasks();
			products = info.getAllTasks();
			info.close();
		} catch (Exception e) {

		}
		class According_To_Num implements Comparator<Product> {
			public int compare(Product left, Product right) {

				return right.num - left.num;

			}
		}
		class DateSort implements Comparator<Product> {
			public int compare(Product left, Product right) {
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

				return ldate.compareTo(rdate);

			}
		}
		Collections.sort(products, new DateSort());
		listAct = products;
		// Bundle gotBasket = getIntent().getExtras();
		// Boolean done = gotBasket.getBoolean("done");
		boxAdapter = new ListAdapter(this, products);

		// for (int i = 0; i < products.size(); i++) {
		// if(products.get(i).done == 1){

		// }
		// }

		display.setAdapter(boxAdapter);
		// display = getListView();

		display.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// int a=products.get(position).id;

				DailogSet((int) id);

			}

			private void DailogSet(final int id) {
				// TODO Auto-generated method stub
				final String items[] = { "Modify", "Delete" };
				AlertDialog.Builder ab = new AlertDialog.Builder(
						MainActivity.this);
				ab.setTitle("Dialog Title");
				ab.setItems(items, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface d, int choice) {

						int id_delete = products.get(id).id;
						if (choice == 0) {
							Bundle b = new Bundle();

							Intent i = new Intent(MainActivity.this,
									ModifyClass.class);
							b.putString("_id", Integer.toString(id_delete));
							b.putString("_edit", products.get(id).task);
							b.putString("_date", products.get(id).date);
							b.putString("_time", products.get(id).time);
							b.putString("_num",
									Integer.toString(products.get(id).num));

							i.putExtras(b);
							finish();
							startActivity(i);
						} else if (choice == 1) {
							try {
								DatabaseHelper entry = new DatabaseHelper(
										MainActivity.this);
								entry.open();
								entry.deleteEntry(id_delete);

								finish();
								Intent intent = new Intent(MainActivity.this,
										MainActivity.class);
								startActivity(intent);
								entry.close();
							} catch (Exception e) {

							}
						}

					}
				});
				ab.show();
			}
		});

		// result.setText(Integer.toString(products.get(0).done));

		add.setOnClickListener(this);
		// speak.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bAdd:
			Intent i = new Intent(MainActivity.this, SecondClass.class);

			// Bundle b=new Bundle();
			// String s=task.getText().toString();
			// b.putString("task", s);
			// i.putExtras(b);
			startActivity(i);
			finish();

			break;
		/*
		 * case R.id.ibSpeak:
		 * 
		 * break;
		 */
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
