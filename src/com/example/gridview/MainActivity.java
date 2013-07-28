package com.example.gridview;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends ActionBarActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
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
		return true;
	}

}
