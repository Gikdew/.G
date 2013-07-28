package com.example.gridview;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
 
public class ImagePagerAdapter extends PagerAdapter {
	LayoutInflater inflater;
	Context context;
	ImageView imageView;
	
    private List<ImageView> images;
 

    public ImagePagerAdapter(Context context, List<ImageView> images) {
    	this.images = images;
        this.context = context;
	}

	public ImagePagerAdapter(ViewImage context2, List<ImageView> images2) {
		this.images = images2;
        this.context = context2;
	}

	@Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_image, container,
                false);
        imageView = (ImageView) images.get(position);
        container.addView(imageView);
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(images.get(position));
    }
 
    @Override
    public int getCount() {
        return images.size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}