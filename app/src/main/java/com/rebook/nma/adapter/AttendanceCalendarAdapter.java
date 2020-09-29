package com.rebook.nma.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rebook.nma.R;
import com.rebook.nma.model.Announcement;
import com.rebook.nma.model.AttendanceCalendar;
import com.rebook.nma.widget.ZawgyiTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YDN on 7/3/2017.
 */
public class AttendanceCalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_LOADING=2;
    public static final int TYPE_ITEM=1;
    List<AttendanceCalendar> list=new ArrayList<>();
    Context context;
    String type;
    int width,height;
    boolean isShow;
    public AttendanceCalendarAdapter(Context context){
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
    public void replaceWith(List<AttendanceCalendar> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_ITEM){
            View view= LayoutInflater.from(context).inflate(R.layout.mark_item,parent,false);
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
            Log.e("Detail","this is adapter _____________"+list.size());

            if (list.get(position).getStatus().equals("Absent")){
                ((ViewHolder) holder).txtMarkStatus.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
            }
            else if (list.get(position).getStatus().equals("Present")){
                ((ViewHolder) holder).txtMarkStatus.setTextColor(ContextCompat.getColor(context,R.color.green));
            }
            ((ViewHolder) holder).txtMarkName.setText(list.get(position).getName());
            ((ViewHolder) holder).txtMarkStatus.setText(list.get(position).getStatus());
            if (list.get(position).getRemark()==null){
                ((ViewHolder) holder).txtMarkReMark.setText("No Remark");
            }else {
                ((ViewHolder) holder).txtMarkReMark.setText((String)list.get(position).getRemark());
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
        @BindView(R.id.mark_name)ZawgyiTextView txtMarkName;
        @BindView(R.id.mark_status)ZawgyiTextView txtMarkStatus;
        @BindView(R.id.mark_remark)ZawgyiTextView txtMarkReMark;

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
