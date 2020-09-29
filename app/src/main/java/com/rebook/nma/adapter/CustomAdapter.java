package com.rebook.nma.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
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
import com.rebook.nma.ui.NotificationDetailActivity;
import com.rebook.nma.util.Utils;
import com.rebook.nma.widget.ZawgyiTextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YDN on 7/3/2017.
 */
public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_LOADING=2;
    public static final int TYPE_ITEM=1;
    List<Batch> list=new ArrayList<>();
    Context context;
    String type;
    int width,height;
    boolean isShow;
    public CustomAdapter(Context context){
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
            Course course = new Course();
            course = list.get(position).getCourse();

            if (type.equals("notification")){
                ((ViewHolder) holder).courseTitle.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).courseTitle.setText(list.get(position).getCourse().getName());
                // ((ViewHolder) holder).txtDescription.setText(list.get(position).getCourse().getDescription());
                ((ViewHolder) holder).txtDescription.setText(Html.fromHtml(list.get(position).getCourse().getDescription()));
                ((ViewHolder) holder).lyPrice.setVisibility(View.GONE);

                ((ViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "this is notification", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, NotificationDetailActivity.class);
                        context.startActivity(intent);
                    }
                });

            }
            else if (type.equals("course") || type.equals("Current_course") || type.equals("HistoryCourse")){
                ((ViewHolder) holder).generalImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(Config.MAIN_URL+"storage/"+course.getImage())
                        .override(Utils.getPx(context, 200),
                                Utils.getPx(context, 180))
                        .into(((ViewHolder) holder).generalImage);
                ((ViewHolder) holder).courseTitle.setText(course.getName());
                ((ViewHolder) holder).txtPrice.setText(course.getAmount()+"\tက်ပ္");
                ((ViewHolder) holder).txtDescription.setText(course.getDescription());
                ((ViewHolder) holder).txtDescription.setText(Html.fromHtml(course.getDescription()));
                ((ViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "this is Course"+list.get(position).getCourse().getImage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, CourseDetailActivity.class);
                        intent.putExtra(CourseDetailActivity.IMG_URL,list.get(position).getCourse().getImage());
                        intent.putExtra(CourseDetailActivity.COURSE_ID,list.get(position).getCourse().getId());
                        context.startActivity(intent);
                    }
                });
                // This is listener is long press click card view
                ((ViewHolder) holder).cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Dialog dialog = new Dialog(context);
                        dialog.show();
                        return false;
                    }
                });


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
        @BindView(R.id.course_title)ZawgyiTextView courseTitle;
        @BindView(R.id.general_image)ImageView generalImage;
        //@BindView(R.id.course_price)ZawgyiTextView txtPrice;
        @BindView(R.id.discount_price)ZawgyiTextView txtPrice;
        @BindView(R.id.description)ZawgyiTextView txtDescription;
        // @BindView(R.id.linearLayout_button)LinearLayout linearLayoutButton;
        @BindView(R.id.card_view)CardView cardView;
        //@BindView(R.id.enroll)ZawgyiTextView txtEnroll;
        @BindView(R.id.view_detail)ZawgyiTextView txtViewDetail;
        @BindView(R.id.price_layout)LinearLayout lyPrice;


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
