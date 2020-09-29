package com.rebook.nma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.rebook.nma.R;
import com.rebook.nma.model.Course;
import com.rebook.nma.ui.SearchActivity;
import java.util.ArrayList;
import java.util.Locale;

public class SearchListViewAdapter extends BaseAdapter {


    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Course> arraylist;

    public SearchListViewAdapter(Context context ) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Course>();
        this.arraylist.addAll(SearchActivity.movieNamesArrayList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return SearchActivity.movieNamesArrayList.size();
    }

    @Override
    public Course getItem(int position) {
        return SearchActivity.movieNamesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_list_item,null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(SearchActivity.movieNamesArrayList.get(position).getName());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        SearchActivity.movieNamesArrayList.clear();
        if (charText.length() == 0) {
            SearchActivity.movieNamesArrayList.addAll(arraylist);
        } else {
            for (Course wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    SearchActivity.movieNamesArrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
