package com.rebook.nma.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rebook.nma.R;
import com.rebook.nma.model.Batch;

import java.util.ArrayList;


public class HomeSlidePagerAdapter extends PagerAdapter {

    ArrayList<Batch> sliderList = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    //int position = 3;

    public HomeSlidePagerAdapter(Activity mainActivity, ArrayList<Batch> sliderList) {
        this.context = mainActivity;
       // this.img = img;
        this.sliderList = sliderList;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((RelativeLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView image;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.slider_item, container, false);
        image = (ImageView) itemview.findViewById(R.id.imageView1);

        //image.setImageResource(img[position]);

        //Picasso.with(context).load("http://68.183.189.90/storage/"+sliderList.get(position).getImage())
        //        .resize(Utils.getPx(context,200),Utils.getPx(context,180)).into((image));
        //Log.e("get image : ","______"+sliderList.get(position).getImage1());


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // context.startActivity(intent);
            }
        });

        //add item.xml to viewpager
        ((ViewPager) container).addView(itemview);
        return itemview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    @Override
    public float getPageWidth(int position) {
        return 1f;   //it is used for set page width of view pager
    }
}
