package com.aoslec.contactproject.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aoslec.contactproject.Activity.MainActivity;
import com.aoslec.contactproject.Activity.ProfileEditActivity;
import com.aoslec.contactproject.Adapter.ContactAdapter;
import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.NetworkTask.NetworkTask;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;

import java.util.ArrayList;

public class ContactFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<People> people;
    private TextView tvEmpty;

    Share share = new Share();
    private String uEmail,url,urlAddr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        uEmail = share.sEmail;
        url = share.sUrl;

        recyclerView = view.findViewById(R.id.contact_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        tvEmpty = view.findViewById(R.id.contact_tvEmpty);

        ContactAdapter contactAdapter = new ContactAdapter();

        contactAdapter.setOnItemClickListener(onItemClickListener);
        contactAdapter.setOnItemLongClickListener(onItemLongClickListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        urlAddr = url +"List.jsp?email=" + uEmail;
        connectListData();

        if (people.get(0).getpName().equals("false")) {
            recyclerView.setVisibility(View.INVISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);

        }else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.INVISIBLE);
        }
    }

    private void connectListData() {
        try {
            Log.v("ggg","ListData");
            NetworkTask networkTask = new NetworkTask(getActivity(), urlAddr,"list");
            Object obj = networkTask.execute().get();
             people = (ArrayList<People>) obj;

            adapter = new ContactAdapter(getActivity(), R.layout.list_recycler, people);
            recyclerView.setAdapter(adapter);




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    ContactAdapter.OnItemClickListener onItemClickListener = new ContactAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Intent intent = null;
            intent = new Intent(getActivity(), ProfileEditActivity.class);
            intent.putExtra("name",people.get(position).getpName());
            intent.putExtra("tel",people.get(position).getpTel());
            intent.putExtra("img",people.get(position).getpImg());
            intent.putExtra("group",people.get(position).getpGroup());
            intent.putExtra("favorite",people.get(position).getpFavorite());
            startActivity(intent);

        }
    };

    ContactAdapter.OnItemLongClickListener onItemLongClickListener = new ContactAdapter.OnItemLongClickListener() {
        @Override
        public void onItemLongClick(View v, int pos) {

        }
    };

}//==


