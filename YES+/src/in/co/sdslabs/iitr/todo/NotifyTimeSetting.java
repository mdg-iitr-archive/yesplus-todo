package in.co.sdslabs.iitr.todo;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class NotifyTimeSetting extends Activity {

	
	 TimePicker pickerTime;
	 Button buttonSetAlarm,stop,setsign;
	 TextView info;
	 AlarmManager alarmMgr0;
	 PendingIntent pendingIntent0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timenotify);
		info = (TextView)findViewById(R.id.tvresult);

		  pickerTime = (TimePicker)findViewById(R.id.scheduleTimePicker);
		  stop = (Button)findViewById(R.id.bstop);
		 
		  
		  Calendar now = Calendar.getInstance();
		

		  
		  pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
		  pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));
		  
		  buttonSetAlarm = (Button)findViewById(R.id.selectButton);
		  Calendar time = Calendar.getInstance();
	       int hour=time.get(Calendar.HOUR_OF_DAY);
	       int min=time.get(Calendar.MINUTE);
		  
			
		
			 
		  buttonSetAlarm.setOnClickListener(new OnClickListener(){

		   @Override
		   public void onClick(View arg0) {
			   
			   just();//to start the alarm
		   }
		  });
		  stop.setOnClickListener(new OnClickListener(){

			   @Override
			   public void onClick(View arg0) {
				   
				   stopAlarm();//to stop the alarm
			   }

		
			  });
		  
		
	}
			  void just()
		  {
			   int h=pickerTime.getCurrentHour();
			    int m=pickerTime.getCurrentMinute();
			   
			    Calendar n = Calendar.getInstance();
					n.set(h,m,00);
					
					
					  alarmMgr0 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

						 //Create pending intent & register it to your alarm notifier class
						 Intent intent0 = new Intent(this, Time.class);
						 intent0.putExtra("uur", "1e"); 
						 pendingIntent0 = PendingIntent.getBroadcast(this, 0, intent0, 0);
						 alarmMgr0.cancel(pendingIntent0);
						 //set timer you want alarm to work (here I have set it to 8.30)
						 Calendar timeOff9 = Calendar.getInstance();
						 timeOff9.set(Calendar.HOUR_OF_DAY, h);
						 timeOff9.set(Calendar.MINUTE,m);
						 timeOff9.set(Calendar.SECOND, 0);

						 //set that timer as a RTC Wakeup to alarm manager object
						 alarmMgr0.setRepeating(AlarmManager.RTC_WAKEUP, timeOff9.getTimeInMillis(),24*60*60*1000, pendingIntent0);
						 
						 Toast.makeText(this,
		" Alarm  set for : "+n.getTime() ,
			                        Toast.LENGTH_LONG).show();
						
						// info.setText(
					    	//	     "Alarm is set @  "
					    		//    );
		  
		 
		     
		    		  
		   }
			  void stopAlarm() {
					// TODO Auto-generated method stub
				  Intent intentstop = new Intent(this, Time.class);
				  PendingIntent senderstop = PendingIntent.getBroadcast(this,
				              0, intentstop, 0);
				  alarmMgr0 = (AlarmManager) getSystemService(ALARM_SERVICE);

				  alarmMgr0.cancel(senderstop);
				  
					 Toast.makeText(this,
	" Alarm  Stopped  " ,
		                        Toast.LENGTH_LONG).show();
				  
					
				}
			
			  
	     
		   
		 
		 }
		   




