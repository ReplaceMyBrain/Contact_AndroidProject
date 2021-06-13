package com.aoslec.contactproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.R;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolderG> {
    private Context mContext = null;
    private int Layout = 0;
    private LayoutInflater inflater = null;
    private ArrayList<People> data = null;

    public GroupAdapter(Context mContext, int layout, ArrayList<People> data) {
        this.mContext = mContext;
        Layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.v("ggg", "GAdapter");
    }

    @Override
    public GroupAdapter.ViewHolderG onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_recycler,parent,false);
        ViewHolderG viewHolderG = new ViewHolderG(v);
        Log.v("ggg", "gCreat");
        return viewHolderG;
    }

    @Override
    public void onBindViewHolder(GroupAdapter.ViewHolderG holder, int position) {
        holder.tv_group.setText(data.get(position).getpGroup());
        holder.tv_count.setText(data.get(position).getCount());
        Log.v("ggg", "onB");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolderG extends RecyclerView.ViewHolder {

        public TextView tv_group;
        public TextView tv_count;


        public ViewHolderG(View itemView) {
            super(itemView);
            Log.v("ggg", "gView");
            tv_group = itemView.findViewById(R.id.recycler_group);
            tv_count = itemView.findViewById(R.id.recycler_count);
        }
    }
}

