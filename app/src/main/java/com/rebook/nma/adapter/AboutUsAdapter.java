package com.rebook.nma.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rebook.nma.R;
import com.rebook.nma.model.Batch;
import com.rebook.nma.widget.ZawgyiTextView;
import com.rebook.nma.widget.ZgToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YDN on 7/3/2017.
 */
public class AboutUsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public static final int TYPE_LOADING=2;
        public static final int TYPE_ITEM=1;
        List<Batch> posts=new ArrayList<>();
        Context context;
        String type;
        int width,height;
        ZgToast zawgyiToast;
        boolean isShow;
        List<String> phoneNos ;
        public AboutUsAdapter(Context context){
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
                posts.addAll(list);
                notifyDataSetChanged();
                Log.e("AboutUsAdapter ","________"+posts.size());
        }
        public void replaceWith(List<Batch> list){
                posts=list;
                notifyDataSetChanged();
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if(viewType==TYPE_ITEM){
                        View view= LayoutInflater.from(context).inflate(R.layout.about_us_item,parent,false);
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
                Batch post = new Batch();
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

                        post = posts.get(position);
                       /* ((ViewHolder)holder).title.setText(Html.fromHtml(posts.get(position).getPostTitle()));
                        ((ViewHolder)holder).description.setText(Html.fromHtml(posts.get(position).getPostDescription()));*/
                       ((ViewHolder) holder).itemName.setText(post.getName());

                }
        }

        @Override
        public int getItemCount() {
                if(posts==null || posts.size()==0){
                        return 0;
                }
                /*if(itemsCount==posts.size()){
                    return posts.size()+1;
                }else {
                    return posts.size();
                }*/
                return posts.size();
        }

        @Override
        public int getItemViewType(int position) {
                return (position!=0 && position==posts.size())? TYPE_LOADING : TYPE_ITEM;
        }
        public void showLoading(boolean status){
                isShow=status;
        }
        public class ViewHolder extends RecyclerView.ViewHolder{

                @BindView(R.id.about_us_itemName)ZawgyiTextView itemName;
                public ViewHolder(View itemView) {
                        super(itemView);
                        ButterKnife.bind(this,itemView);
                }
        }
        public class LoadingViewHolder extends RecyclerView.ViewHolder{
                @BindView(R.id.loading) ProgressBar progressBar;
                public LoadingViewHolder(View itemView) {
                        super(itemView);
                        ButterKnife.bind(this,itemView);
                }
        }
}
