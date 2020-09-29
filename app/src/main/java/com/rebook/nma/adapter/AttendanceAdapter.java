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
import com.rebook.nma.model.Batch;
import com.rebook.nma.ui.AttendanceDetail;
import com.rebook.nma.widget.ZawgyiTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YDN on 7/3/2017.
 */
public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int TYPE_LOADING=2;
    public static final int TYPE_ITEM=1;
    List<Batch> batches =new ArrayList<>();
    Context context;
    String type;
    int width,height;
    boolean isShow;
    public AttendanceAdapter(Context context){
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
        batches.addAll(list);
        notifyDataSetChanged();
    }
    public void replaceWith(List<Batch> list){
        batches=list;
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
            View view= LayoutInflater.from(context).inflate(R.layout.layout_loading,parent,false);
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
           // ((ViewHolder) holder).txtMarkName.setText(batches.get(position).getName());
            ((ViewHolder) holder).txtMarkName.setText(batches.get(position).getCourse().getName());
            ((ViewHolder) holder).txtMarkBatch.setText(batches.get(position).getName());
            ((ViewHolder) holder).txtMarkSeeDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,AttendanceDetail.class);
                    intent.putExtra(AttendanceDetail.BATCH_ID,batches.get(position).getId().toString());
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if(batches==null || batches.size()==0){
            return 0;
        }
                /*if(itemsCount==posts.size()){
                    return posts.size()+1;
                }else {
                    return posts.size();
                }*/
        return batches.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position!=0 && position==batches.size())? TYPE_LOADING : TYPE_ITEM;
    }
    public void showLoading(boolean status){
        isShow=status;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.attendance_name)ZawgyiTextView txtMarkName;
        @BindView(R.id.attendance_batch)ZawgyiTextView txtMarkBatch;
        @BindView(R.id.attendance_see_detail)ZawgyiTextView txtMarkSeeDetail;

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
