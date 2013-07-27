package com.example.gridview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
 
public class ViewImage extends ActionBarActivity{
    int position;
    int wallpaper;
    String filename;
    File file = null, path = null;
    Bitmap bm;
    AlertDialog.Builder builder;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Image");
        
        setContentView(R.layout.view_image);                    
        ActionBar actionBar = getSupportActionBar();        
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        
        // Get intent data from GridViewActivity.java
        Intent i = getIntent();
        position = i.getExtras().getInt("id");
 
        // Open the Image adapter
        MyAdapter imageAdapter = new MyAdapter(this);
 
        // Displaying selected Image
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
 
        // Set the image position that is passed from GridViewActivity.java
        imageView.setImageResource(imageAdapter.items.get(position).drawableId);
        
        wallpaper = imageAdapter.items.get(position).drawableId; //Gets the R.drawable file
        filename = imageAdapter.items.get(position).name + ".PNG"; //Ads jpg to it (I have to change it to png)
        bm = BitmapFactory.decodeResource( getResources(), wallpaper);
		path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		file = new File(path, filename);      
                
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.setWallpaper:
	        	builder = new AlertDialog.Builder(this);
	            builder.setMessage("Set as Wallpaper?");
	            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

	                public void onClick(DialogInterface dialog, int which) {
	                	setWallie();
	                    dialog.dismiss();
	                }

	            });

	            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    // Do nothing
	                    dialog.dismiss();
	                }
	            });

	            builder.create().show();
	        	
	        	return true;
	        case R.id.saveSD:
	        	builder = new AlertDialog.Builder(this);
	            builder.setMessage("Save in SD storage?");
	            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

	                public void onClick(DialogInterface dialog, int which) {
	                	saveSD();
	                    dialog.dismiss();
	                }

	            });

	            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    // Do nothing
	                    dialog.dismiss();
	                }
	            });

	            builder.create().show();	             
	            return true;
	            
	        case R.id.share:
	        	shareIntent();
	        	return true;
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}


	private void shareIntent() {
		Uri uriToImage = Uri.parse("android.resource://"+ getResources().getText(R.string.package_name) +"/" + wallpaper);
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
		shareIntent.setType("image/*");
		startActivity(Intent.createChooser(shareIntent,  "Share images to.."));
		
	}


	private void saveSD() {
		Toast.makeText(ViewImage.this,"Saving...", Toast.LENGTH_SHORT).show();        
		path.mkdirs();
        try {

            InputStream is = getResources().openRawResource(wallpaper);
            OutputStream os = new FileOutputStream(file);
            byte[] data = new byte[is.available()];
            is.read(data);
            os.write(data);
            is.close();
            os.close();

            Toast.makeText(ViewImage.this,"The Image has been Saved", Toast.LENGTH_SHORT).show();
            
            updateMediaScanner();
            //Update MediaScanner Files for user to use
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	    		
	}

	private void updateMediaScanner() {
		MediaScannerConnection.scanFile(ViewImage.this,
                new String[] {file.toString()},
                null,
                new MediaScannerConnection.MediaScannerConnectionClient() {
                    @Override
                    public void onMediaScannerConnected() {                       
                    }

                    @Override
                    public void onScanCompleted(String s, Uri uri) {

                    }
                });		
	}


	private void setWallie() {
    	Toast.makeText(ViewImage.this,"Loading...", Toast.LENGTH_SHORT).show();
		InputStream iS = getResources().openRawResource(wallpaper);
		Bitmap bitmap = BitmapFactory.decodeStream(iS);
		WallpaperManager myWM = WallpaperManager.getInstance(getApplicationContext());
		
		try{
			myWM.setBitmap(bitmap);				
		}catch(IOException e){
			e.printStackTrace();			
		}finally{	    		
			Toast.makeText(ViewImage.this, filename, Toast.LENGTH_SHORT).show();
		}		          
	}
}     