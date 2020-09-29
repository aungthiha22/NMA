package com.rebook.nma.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.model.Batch;
import com.rebook.nma.model.Course;
import com.rebook.nma.ui.CourseDetailActivity;
import com.rebook.nma.ui.EventDetailActivity;
import com.rebook.nma.ui.JobDetailActivity;
import com.rebook.nma.ui.NotificationDetailActivity;
import com.rebook.nma.util.Utils;
import com.rebook.nma.widget.ZawgyiTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YDN on 7/3/2017.
 */
public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_LOADING=2;
    public static final int TYPE_ITEM=1;
    List<Batch> list=new ArrayList<>();
    Context context;
    String type;
    int width,height;
    boolean isShow;
    public ItemAdapter(Context context){
        this.context=context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }
    public void setType(String type) {
        this.type=type;
    }
    public void append(List<Batch> list){
        list.addAll(list);
        notifyDataSetChanged();
    }
    public void replaceWith(List<Batch> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_ITEM){
            View view= LayoutInflater.from(context).inflate(R.layout.event_item,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }
        else if(viewType==TYPE_LOADING){
            View view=LayoutInflater.from(context).inflate(R.layout.layout_loading,parent,false);
            LoadingViewHolder loadingViewHolder=new LoadingViewHolder(view);
            return loadingViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        //  Post post = new Post();
        if(holder instanceof LoadingViewHolder){
            if(isShow){
                ((LoadingViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
            }
            else {
                ((LoadingViewHolder)holder).progressBar.setVisibility(View.GONE);
                //notifyDataSetChanged();
            }
        }
        if(holder instanceof ViewHolder){

            if (type.equals("Events")){
                ((ViewHolder) holder).txtEventTime.setText(list.get(position).getStartTime()+"\t-\t"+list.get(position).getEndTime());
                ((ViewHolder) holder).txtEventName.setText(list.get(position).getName());
                ((ViewHolder) holder).txtEventDescription.setText(list.get(position).getDescription());
                Log.e("item","192.168.8.11/storage/" + list.get(position).getImageUrl());
                Glide.with(context).load( "192.168.8.11/storage/events/CQ2TQhBZYXSvrtpS46ND1Gc88fKSY7b9sQV1zRb9.png")
                        .override(Utils.getPx(context, 100),
                                Utils.getPx(context, 80))
                        .into(((ViewHolder) holder).imgEvents);
                ((ViewHolder) holder).cardViewEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EventDetailActivity.class);
                        intent.putExtra(EventDetailActivity.EVENT_ID,list.get(position).getId().toString());
                        context.startActivity(intent);
                        //Toast.makeText(context, "This is Card View\t"+list.get(position).getId(), Toast.LENGTH_SHORT).show();
                    }
                });

            }else if (type.equals("Jobs")){
                ((ViewHolder) holder).txtEventName.setText(list.get(position).getName());
                ((ViewHolder) holder).imgEvents.setVisibility(View.GONE);
                ((ViewHolder) holder).txtEventTime.setText(list.get(position).getSalary());
                ((ViewHolder) holder).txtEventDescription.setText(list.get(position).getDescription());
                ((ViewHolder) holder).cardViewEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, JobDetailActivity.class);
                        intent.putExtra(JobDetailActivity.JOB_INTENT,list.get(position).getId().toString());
                        context.startActivity(intent);
                    }
                });

            }


        }
    }

    @Override
    public int getItemCount() {
        if(list==null || list.size()==0){
            return 0;
        }
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position!=0 && position==list.size())? TYPE_LOADING : TYPE_ITEM;
    }
    public void showLoading(boolean status){
        isShow=status;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.event_name)ZawgyiTextView txtEventName;
        @BindView(R.id.event_image)ImageView imgEvents;
        @BindView(R.id.event_time)ZawgyiTextView txtEventTime;
        @BindView(R.id.event_location)ZawgyiTextView txtEventlocation;
        @BindView(R.id.event_description)ZawgyiTextView txtEventDescription;
        @BindView(R.id.cardView_event)CardView cardViewEvent;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.loading)ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
