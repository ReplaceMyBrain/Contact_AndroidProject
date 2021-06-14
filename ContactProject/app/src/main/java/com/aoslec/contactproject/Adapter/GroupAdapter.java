package com.aoslec.contactproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.R;

import java.util.ArrayList;

public class GroupAdapter extends BaseAdapter{

    private Context mContext = null;
    private int Layout = 0;
    private LayoutInflater inflater = null;
    private ArrayList<People> data = null;

    public GroupAdapter(Context mContext, int layout, ArrayList<People> data) {
        Log.v("ggg","group ");

        this.mContext = mContext;
        this.Layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getpGroup();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       convertView = inflater.inflate(this.Layout,parent,false);

       Log.v("ggg","group getView");

        ImageView imageView = convertView.findViewById(R.id.list_img);
        TextView group = convertView.findViewById(R.id.list_group);
        TextView count = convertView.findViewById(R.id.list_count);

        group.setText(data.get(position).getpGroup());
        count.setText("   (" + data.get(position).getCount() + ")");

        Log.v("ggg","group" + group + "count" + count);

        return convertView;
    }
}
