package com.example.gridview;

import com.okvm.gcae136203.Airpush;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends ActionBarActivity {
	
	Airpush airpush;
	
	@Override	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    //Adds - Airpush Ads Callings
	    airpush = new Airpush(this, null);
	    airpush.startPushNotification(true);
	    airpush.showRichMediaInterstitialAd();
	    airpush.startIconAd();
	    
	    
	    //Start of Android Application
	    setContentView(R.layout.activity_main);
	    
	    //GridView System
	    GridView gridView = (GridView)findViewById(R.id.gridview);
	    gridView.setAdapter(new MyAdapter(this));	   
        gridView.setOnItemClickListener(new OnItemClickListener() {
        	
        	@Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        		Intent i = new Intent(getApplicationContext(), ViewImage.class);        		 
                // Send the click position to ViewImage.java using intent
                i.putExtra("id", position); 
                // Start ViewImage
                startActivity(i);

            }
        });
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);	    
	    
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.developer:
	            openMyApps();
	            return true;	        
	           
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void openMyApps() {
		try {
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=Gikdew")));
		} catch (android.content.ActivityNotFoundException anfe) {
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=Gikdew")));
		}
	
	}


	@Override
	public void onBackPressed() {
	      if (airpush!=null) {
	      //Use only one from below. SDK will ignore the other request.
	        airpush.startAppWall();
	    }
		super.onBackPressed();
	}

}
