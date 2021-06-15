package com.aoslec.contactproject.Fragment;

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
import com.aoslec.contactproject.Adapter.ContactAdapter;
import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.NetworkTask.NetworkTask;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        uEmail = share.sEmail;
        url = share.sUrl;

        recyclerView = view.findViewById(R.id.favorite_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        tvEmpty = view.findViewById(R.id.favorite_tvEmpty);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("즐겨찾기");
        }

        urlAddr = url +"favorite.jsp?email=" + uEmail;
        connectFavoriteData();

        if (people.get(0).getpName().equals("false")) {
            recyclerView.setVisibility(View.INVISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);

        }else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.INVISIBLE);
        }
    }

    private void connectFavoriteData() {
        try {
            Log.v("ggg","favorite");
            NetworkTask networkTask = new NetworkTask(getActivity(), urlAddr,"list");
            Object obj = networkTask.execute().get();
            people = (ArrayList<People>) obj;

            adapter = new ContactAdapter(getActivity(), R.layout.list_recycler, people);
            recyclerView.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}//==