package com.aoslec.contactproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.contactproject.Bean.Explain;
import com.aoslec.contactproject.R;

import java.util.List;

public class ExplainAdapter extends RecyclerView.Adapter<ExplainAdapter.MyViewHolder> {

    List<Explain> list;

    public ExplainAdapter(List<Explain> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.explain_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExplainAdapter.MyViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImages());

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Explain_image);
        }
    }
}

