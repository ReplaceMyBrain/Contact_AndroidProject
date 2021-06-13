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


    //-------------------클릭리스너(커스텀)


    //커스텀 리스너(Custom Listener) 인터페이스 정의.
    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public interface OnItemLongClickListener
    {
        void onItemLongClick(View v, int pos);
    }

    //해당 메서드를 통해 전달된 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;
    private OnItemLongClickListener mLongListener = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener)
    {
        this.mLongListener = listener;
    }

    public ContactAdapter() {
    }

    //------------------------------------

    public ContactAdapter(Context mContext, int layout, ArrayList<People> data) {
        this.mContext = mContext;
        Layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.v("ggg","listAdapter");
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        Log.v("ggg","holder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, int position) {
        Share share = new Share();
        url = share.sUrl;

        holder.tv_name.setText(data.get(position).getpName());

        Log.v("ggg","glide");

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
            Log.v("ggg", "List viewHolder1");
            tv_name = itemView.findViewById(R.id.recycler_name);
            img = itemView.findViewById(R.id.recycler_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mLongListener != null) {
                            mLongListener.onItemLongClick(v, pos);
                            ;
                        }
                    }
                    return true;
                }
            });
        }

    }//Class

}//-----




