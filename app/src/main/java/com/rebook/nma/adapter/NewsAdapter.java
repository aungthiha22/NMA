package com.rebook.nma.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.rebook.nma.R;
import com.rebook.nma.model.Batch;
import com.rebook.nma.ui.AnnouncementActivity;
import com.rebook.nma.ui.TestingActivity;
import com.rebook.nma.widget.ZawgyiTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 3/21/2019.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context context;
    ArrayList<Batch> arrayList = new ArrayList();
    public NewsAdapter(Context context){
        this.context=context;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        ViewHolder loadingViewHolder=new ViewHolder(view);
        return loadingViewHolder;
    }

    @Override
    public void onBindViewHolder(final NewsAdapter.ViewHolder holder, final int position) {

        if (arrayList == null){
        }else {
            holder.imageView.setImageResource(R.drawable.news_1);
            holder.textView.setText("ဘြဲ႔ျကိဳမ်ားလည္းတတ္ေရာက္နိုင္သည္။");
            holder.ratingBar.setVisibility(View.GONE);
            holder.linearLayout.setVisibility(View.GONE);
            holder.viewHide.setVisibility(View.GONE);

           // holder.image.setImageResource(R.mipmap.ic_launcher);
            //  holder.title.setText(Html.fromHtml(products1.get(position).getName()));
                /*Picasso.with(context).load("http://68.183.189.90/storage/"+bestSeller.get(position).getImage1())
                        .resize(Utils.getPx(context,200),                                Utils.getPx(context,180)).into((holder).image);
                (holder).ratingBar.setRating(bestSeller.get(position).getQuantity());*/

            /*Glide.with(context).load("http://68.183.189.90/storage/"+bestSeller.get(position).getImage1())
                    .override(Utils.getPx(context,200),
                            Utils.getPx(context,180))
                    .into(holder.image);
            (holder).ratingBar.setRating(4);*/
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TestingActivity.class);
                    ((Activity) context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    context.startActivity(intent);
                }
            });

        }
    }

    public void append(ArrayList<Batch> list){
        // products1.clear();
        // bestSeller.addAll(list);
        arrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_title)ZawgyiTextView textView;
        @BindView(R.id.ratingBar)RatingBar ratingBar;
        @BindView(R.id.layoutEnroll)LinearLayout linearLayout;
        @BindView(R.id.viewHide)View viewHide;
        @BindView(R.id.item_image)ImageView imageView;
        @BindView(R.id.item_layout)LinearLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

}