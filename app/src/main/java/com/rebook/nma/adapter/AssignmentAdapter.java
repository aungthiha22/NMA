package com.rebook.nma.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.rebook.nma.R;
import com.rebook.nma.model.Assignment;
import com.rebook.nma.model.Batch;
import com.rebook.nma.model.Course;
import com.rebook.nma.ui.AssignmentUploadActivity;
import com.rebook.nma.widget.ZawgyiTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YDN on 7/3/2017.
 */
public class AssignmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_LOADING=2;
    public static final int TYPE_ITEM=1;
    List<Assignment> list=new ArrayList<>();
    Context context;
    String type;
    int width,height;
    boolean isShow;
    public AssignmentAdapter(Context context){
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
    public void replaceWith(List<Assignment> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_ITEM){
            View view= LayoutInflater.from(context).inflate(R.layout.general_item,parent,false);
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

            if (type.equals("mark")){
                ((ViewHolder) holder).imageViewDownload.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).imageViewDownload.setImageResource(R.drawable.ic_menu_gallery);
                ((ViewHolder) holder).txtAssignmentTitle.setText(list.get(position).getTitle());
                ((ViewHolder) holder).txtDiscountPrice.setText(list.get(position).getDueDate());
                ((ViewHolder) holder).txtAssignmentDesc.setText(list.get(position).getDescription());
                // ((ViewHolder) holder).txtPrice.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).txtPrice.setText("Due Date");
                ((ViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(context, AssignmentUploadActivity.class);
                                context.startActivity(intent);

                        }
                });
            }
            else if (type.equals("course")){

            }/*else if (type.equals("Current_course")){
                       ((ViewHolder) holder).courseTitle.setText(list.get(position).getCourse().getName());
                       Glide.with(context).load(Config.TESTING_BASE_URL+"storage/"+course.getImage())
                               .override(Utils.getPx(context, 200),
                                       Utils.getPx(context, 180))
                               .into(((ViewHolder) holder).generalImage);


            }*/

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
        @BindView(R.id.card_view)CardView cardView;
        @BindView(R.id.course_title)ZawgyiTextView txtAssignmentTitle;
        @BindView(R.id.description)ZawgyiTextView txtAssignmentDesc;
        @BindView(R.id.discount_price)ZawgyiTextView txtDiscountPrice;
        @BindView(R.id.course_price)ZawgyiTextView txtPrice;
        @BindView(R.id.general_image)
        ImageView imageViewDownload;

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
