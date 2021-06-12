package com.aoslec.contactproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private Context mContext = null;
    private int Layout = 0;
    private LayoutInflater inflater = null;
    private ArrayList<People> data = null;
    private String url;

    public ContactAdapter(Context mContext, int layout, ArrayList<People> data) {
        this.mContext = mContext;
        Layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_recycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, int position) {
        Share share = new Share();
        url = share.sUrl;

        holder.tv_name.setText(data.get(position).getpName());

        Log.v("ggg","glide" + data.get(position).getpImg());

        Glide.with(mContext)
                .load(url+"img/"+data.get(position).getpImg())
                .circleCrop()
                .error(R.drawable.face)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_name;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.v("ggg","viewHolder");
            tv_name = itemView.findViewById(R.id.recycler_name);
            img = itemView.findViewById(R.id.recycler_image);
        }

    }//Class
}//-----



