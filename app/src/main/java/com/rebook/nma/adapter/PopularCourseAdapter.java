package com.rebook.nma.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.model.Batch;
import com.rebook.nma.sync.SyncPostService;
import com.rebook.nma.ui.CourseDetailActivity;
import com.rebook.nma.ui.SlideActivity;
import com.rebook.nma.util.NetService;
import com.rebook.nma.util.TinyDB;
import com.rebook.nma.util.Utils;
import com.rebook.nma.widget.ZawgyiTextView;
import com.rebook.nma.widget.ZgToast;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Dell on 3/21/2019.
 */

public class PopularCourseAdapter extends RecyclerView.Adapter<PopularCourseAdapter.ViewHolder> {

    TinyDB tinyDB;
    Context context;
    ArrayList<Batch> arrayList = new ArrayList();
    OkHttpClient okHttpClient;
    SyncPostService syncPostService;
    ProgressDialog progressDialog;
    public PopularCourseAdapter(Context context){
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        ViewHolder loadingViewHolder=new ViewHolder(view);
        return loadingViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        tinyDB = new TinyDB(context);
        okHttpClient = new OkHttpClient();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("waiting....");
        CertificatePinner certificatePinner=new CertificatePinner.Builder()
                .add(Config.MAIN_URL)
                .build();
        okHttpClient.setCertificatePinner(certificatePinner);
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(Config.MAIN_URL+Config.API)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setClient(new OkClient(okHttpClient))
                .build();
        syncPostService=restAdapter.create(SyncPostService.class);
        if (arrayList == null){
            ZgToast zgToast = new ZgToast(context);
            zgToast.setError();
            zgToast.setZgText("no array list in popular course");
            zgToast.show();
        }else {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageResource(R.drawable.course);
            holder.itemTitle.setText( "ေသခ်ာစနစ္တက် သင္ၾကားေပးသည္။");
            holder.itemDate.setVisibility(View.GONE);
            holder.itemDescription.setVisibility(View.GONE);
            holder.itemBranchName.setText(arrayList.get(position).getCourse().getBranch().getName());

            holder.itemTitle.setText(arrayList.get(position).getCourse().getName());
            holder.txtPrice.setText(String.valueOf(arrayList.get(position).getCourse().getAmount()));

            Picasso.with(context)
                    .load(Config.MAIN_URL+"storage/"+arrayList.get(position).getCourse().getImage())
                    .resize(Utils.getPx(context,230), Utils.getPx(context,170))
                    .error(R.drawable.course)
                    .into((holder).imageView);
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"this is linear layout item"+arrayList.get(position).getCourse().getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CourseDetailActivity.class);
                    intent.putExtra(CourseDetailActivity.IMG_URL,arrayList.get(position).getCourse().getImage());
                    intent.putExtra(CourseDetailActivity.COURSE_ID,arrayList.get(position).getId());
                    context.startActivity(intent);
                    ((Activity)context).overridePendingTransition(0,0);

                }
            });
            ZgToast zgToast = new ZgToast(context);
            holder.itemEnroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetService.isInternetAvailable(context)){

                        Log.e("popular adapter","_________________"+arrayList.get(position).getId());
                        Log.e("popular adapter","_________________"+arrayList.get(position).getCourse().getId());
                        Log.e("popular adapter","_________________"+tinyDB.getString(Config.STORE_ID));


                        progressDialog.show();
                        syncPostService.getEnroll(String.valueOf(arrayList.get(position).getCourse().getId()),
                                String.valueOf(arrayList.get(position).getId()),
                                tinyDB.getString(Config.STORE_ID)
                                , new Callback<JsonObject>() {
                                    @Override
                                    public void success(JsonObject jsonObject, Response response) {
                                        progressDialog.dismiss();
                                        if(jsonObject.get("success").getAsBoolean()) {
                                            //jsonObject.get("message").getAsString()
                                            zgToast.setZgText("Your enroll is success");
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        progressDialog.dismiss();
                                    }
                                });
                        //overridePendingTransition(R.anim.from_bottom_to_top, 0);
                        //((Activity) context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);


                    }else {
                        zgToast.setZgText(context.getResources().getString(R.string.no_connection));
                    }
                    zgToast.show();

                }
            });

            //  holder.title.setText(Html.fromHtml(products1.get(position).getName()));
                /*Picasso.with(context).load("http://68.183.189.90/storage/"+bestSeller.get(position).getImage1())
                        .resize(Utils.getPx(context,200),                                Utils.getPx(context,180)).into((holder).image);
                (holder).ratingBar.setRating(bestSeller.get(position).getQuantity());*/
               /*Glide.with(context).load("http://68.183.189.90/storage/"+bestSeller.get(position).getImage1())
                    .override(Utils.getPx(context,200),
                            Utils.getPx(context,180))
                    .into(holder.image);
            (holder).ratingBar.setRating(4);*/

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

       // ImageView image;
       // RippleView rippleAddToCard;
        @BindView(R.id.item_title) ZawgyiTextView itemTitle;
        @BindView(R.id.branch_name) ZawgyiTextView itemBranchName;
        @BindView(R.id.item_description) ZawgyiTextView itemDescription;
        @BindView(R.id.item_date)ZawgyiTextView itemDate;
        @BindView(R.id.price) ZawgyiTextView txtPrice;
        @BindView(R.id.item_image)ImageView imageView;
        @BindView(R.id.item_layout)LinearLayout itemLayout;
        @BindView(R.id.item_enroll)ZawgyiTextView itemEnroll;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

           // image = itemView.findViewById(R.id.image);

        }
    }
}