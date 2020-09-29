package com.rebook.nma.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rebook.nma.R;
import com.rebook.nma.model.Announcement;
import com.rebook.nma.ui.AnnouncementDetailActivity;
import com.rebook.nma.widget.ZawgyiTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YDN on 7/3/2017.
 */
public class AnnouncementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_LOADING=2;
    public static final int TYPE_ITEM=1;
    List<Announcement> list=new ArrayList<>();
    Context context;
    String type;
    int width,height;
    boolean isShow;
    public AnnouncementAdapter(Context context){
            this.context=context;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = displayMetrics.widthPixels;
    }
    public void setType(String type) {
            this.type=type;
    }
    public void append(List<Announcement> list){
            list.addAll(list);
            notifyDataSetChanged();
    }
    public void replaceWith(List<Announcement> list){
            this.list=list;
            notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType==TYPE_ITEM){
                    View view= LayoutInflater.from(context).inflate(R.layout.attendance_item,parent,false);
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

            if (type.equals("announcement")){
                ((ViewHolder)holder).lblCourseName.setText("Announce 1");
                ((ViewHolder)holder).lblBatchName.setText("Announce 2");
                ((ViewHolder)holder).txtMarkName.setText(list.get(position).getExpiredDate());
                ((ViewHolder)holder).txtMarkBatch.setText(list.get(position).getName());
                ((ViewHolder)holder).txtMarkSeeDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AnnouncementDetailActivity.class);
                        intent.putExtra(AnnouncementDetailActivity.ANNOUNCEMENT_ID,list.get(position).getId().toString());
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
        @BindView(R.id.attendance_name)ZawgyiTextView txtMarkName;
        @BindView(R.id.attendance_batch)ZawgyiTextView txtMarkBatch;
        @BindView(R.id.attendance_see_detail)ZawgyiTextView txtMarkSeeDetail;
        @BindView(R.id.lblCourseName)ZawgyiTextView lblCourseName;
        @BindView(R.id.lblBatchName)ZawgyiTextView lblBatchName;

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
