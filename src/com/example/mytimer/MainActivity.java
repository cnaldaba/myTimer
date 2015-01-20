package com.example.mytimer;



import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


public class MainActivity extends Activity {
	private  Chronometer timer;
	private String TAG = "TIME";
	Button start, stop;
	
	public int dum; 
	
	public long pastMsTime, nowMsTime, duration;
	public long hour, min, sec;
	public String hourString, minString, secString, totalTime;
	
	public TextView time1Txt, time2Txt, time3Txt, time4Txt, time5Txt;
	public String time1, time2,time3,time4,time5;
	
	public SharedPreferences settings;
	public SharedPreferences.Editor editor; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        time1Txt = (TextView)findViewById(R.id.time1);
        time2Txt = (TextView)findViewById(R.id.time2);
        time3Txt = (TextView)findViewById(R.id.time3);
        time4Txt = (TextView)findViewById(R.id.time4);
        time5Txt = (TextView)findViewById(R.id.time5);
        
        setUpButtons();
        setUpPreferences();
        restoreState();
        
        timer = (Chronometer) findViewById(R.id.chronometer1);
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public void setUpPreferences(){
    	settings = getSharedPreferences("MyGamePreferences", MODE_PRIVATE);
    	editor = settings.edit();
    }
    
    public void restoreState(){
    	time1 = settings.getString("TIME1", "--:--:--");
    	time2 = settings.getString("TIME2", "--:--:--");
    	time3 = settings.getString("TIME3", "--:--:--");
    	time4 = settings.getString("TIME4", "--:--:--");
    	time5 = settings.getString("TIME5", "--:--:--");
    	
    	String dummy;
    	dummy = "1.\t" + time1;
    	time1Txt.setText(dummy);
    	
    	dummy = "2.\t" + time2;
    	time2Txt.setText(dummy);
    	
    	dummy = "3.\t" + time3;
    	time3Txt.setText(dummy);
    	
    	dummy = "4.\t" + time4;
    	time4Txt.setText(dummy);
    	
    	dummy = "5.\t" + time5;
    	time5Txt.setText(dummy);
    	
    }
    
    public void saveState(){
    	time5 = time4;
    	time4 = time3;
    	time3 = time2;
    	time2 = time1;
    	time1 = totalTime;
    	
    	editor.putString("TIME1", time1);
    	editor.putString("TIME2", time2);
    	editor.putString("TIME3", time3);
    	editor.putString("TIME4", time4);
    	editor.putString("TIME5", time5);
    	editor.commit();
    	
    }
    
    public void setUpButtons(){
    	 start = (Button)findViewById(R.id.start);
         stop = (Button)findViewById(R.id.stop);
         
         start.setOnClickListener(new View.OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				 // TODO start timer
 				timer.setBase(SystemClock.elapsedRealtime());
 				timer.start();
 				
 				pastMsTime = System.currentTimeMillis();
 			}     
 	    });
         
         stop.setOnClickListener(new View.OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				 // TODO stop timer
 				
 				nowMsTime = System.currentTimeMillis();
 				duration = nowMsTime - pastMsTime;
 				
 				hour = duration / 3600000;
 				min = (duration - hour * 3600000)/60000;
 				sec = (duration - hour * 3600000 - min * 60000)/1000;
 				
 				//FORMAT HOUR
 				if( (hour>=0) && (hour<10)){
 					hourString = '0' + String.valueOf(hour);
 				}
 				else{
 					hourString = String.valueOf(hour);
 				}
 				//FORMAT MINUTE
 				if( (min>=0) && (min<10)){
 					minString = '0' + String.valueOf(min);
 				}
 				else{
 					minString = String.valueOf(min);
 				}
 				
 				//FORMAT SECOND
 				if( (sec>=0) && (sec<10)){
 					secString = '0' + String.valueOf(sec);
 				}
 				else{
 					secString = String.valueOf(sec);
 				}
 				
 				totalTime = hourString + ':' + minString + ':' + secString;
 						
 				long timeWhenStopped = timer.getBase() - SystemClock.elapsedRealtime();
 				Log.i(TAG, totalTime);
 				timer.stop();
 				dum =0;
 				
 				saveState();
 				restoreState();
 			}     
 	    });
    }
}
