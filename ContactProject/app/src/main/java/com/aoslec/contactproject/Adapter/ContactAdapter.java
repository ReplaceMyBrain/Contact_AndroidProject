package com.aoslec.contactproject.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.contactproject.Activity.MainActivity;
import com.aoslec.contactproject.Activity.ProfileEditActivity;
import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.NetworkTask.NetworkTask;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private Context mContext = null;
    private int Layout = 0;
    private LayoutInflater inflater = null;
    private ArrayList<People> data = null;
    Share share = new Share();
    private String url, urlAddr;


    //-------------------클릭리스너(커스텀)
//
//
//    //커스텀 리스너(Custom Listener) 인터페이스 정의.
//    public interface OnItemClickListener {
//        void onItemClick(View v, int position) ;
//    }
//
//    public interface OnItemLongClickListener
//    {
//        void onItemLongClick(View v, int pos);
//    }
//
//    //해당 메서드를 통해 전달된 리스너 객체 참조를 저장하는 변수
//    private OnItemClickListener mListener = null;
//    private OnItemLongClickListener mLongListener = null;
//
//    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mListener = listener ;
//    }
//
//    public void setOnItemLongClickListener(OnItemLongClickListener listener)
//    {
//        this.mLongListener = listener;
//    }
//
//    public ContactAdapter() {
//    }
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
        int pos = 0;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.v("ggg", "List viewHolder1");
            tv_name = itemView.findViewById(R.id.recycler_name);
            img = itemView.findViewById(R.id.recycler_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    Log.v("ggg", "List viewHolder2" + pos);
                    if (pos != RecyclerView.NO_POSITION) {
                        Log.v("ggg", "List viewHolder3" + pos);

                        Intent intent = new Intent(mContext, ProfileEditActivity.class);

                        intent.putExtra("no",data.get(pos).getpNo());
                        intent.putExtra("name",data.get(pos).getpName());
                        intent.putExtra("tel",data.get(pos).getpTel());
                        intent.putExtra("img",data.get(pos).getpImg());
                        intent.putExtra("group",data.get(pos).getpGroup());
                        intent.putExtra("favorite",data.get(pos).getpFavorite());
                        mContext.startActivity(intent);

                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        new AlertDialog.Builder(mContext)
                                .setTitle("삭제")
                                .setMessage(data.get(pos).getpName() + "님을 삭제하시겠습니까?")
                                .setIcon(R.drawable.delete)
                                .setCancelable(false)
                                .setPositiveButton("삭제",delete)
                                .setNegativeButton("취소",null)
                                .show();
                    }
                    return true;
                }
            });
        }

        DialogInterface.OnClickListener delete = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                url = share.sUrl;

                urlAddr = url + "profileDelete.jsp?no=" + data.get(pos).getpNo();
                Log.v("ggg", "longClick" + urlAddr);
                String result = connectDeleteDate();
                Log.v("ggg","Delete");

                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);

                Toast.makeText(mContext,"삭제되었습니다",Toast.LENGTH_SHORT).show();
            }
        };

        private String connectDeleteDate() {
            String result = null;
            try {
                NetworkTask networkTask = new NetworkTask(mContext, urlAddr,"insert");
                Object obj = networkTask.execute().get();
                result = (String) obj;
                Log.v("ggg","" + result);

            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

    }//Class



}//-----




