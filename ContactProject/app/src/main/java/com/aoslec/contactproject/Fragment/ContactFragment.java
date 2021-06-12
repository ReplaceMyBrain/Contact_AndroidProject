package com.aoslec.contactproject.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aoslec.contactproject.Adapter.ContactAdapter;
import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.NetworkTask.NetworkTask;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<People> people;

    String urlAddr;

    Share share = new Share();
    String uEmail;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        uEmail = share.sEmail;
        url = share.sUrl;

        recyclerView = view.findViewById(R.id.contact_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        urlAddr = url +"List.jsp?email=" + uEmail;
        connectListData();
    }

    private void connectListData() {
        try {
            Log.v("ggg","ListData");
            NetworkTask networkTask = new NetworkTask(getActivity(), urlAddr,"list");
            Object obj = networkTask.execute().get();
             people = (ArrayList<People>) obj;

            adapter = new ContactAdapter(getActivity(), R.layout.contact_recycler, people);
            recyclerView.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}//==


