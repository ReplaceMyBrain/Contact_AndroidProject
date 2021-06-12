package com.aoslec.contactproject.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aoslec.contactproject.Activity.ExplainActivity;
import com.aoslec.contactproject.Activity.LoginActivity;
import com.aoslec.contactproject.Activity.MainActivity;
import com.aoslec.contactproject.Adapter.ContactAdapter;
import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.Bean.User;
import com.aoslec.contactproject.NetworkTask.NetworkTask;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    Share share = new Share();
    String url;
    String urlAddr;

    String uEmail, uPw;
    EditText etEmail,etPw;
    TextView tvFail;
    Button login;
    ArrayList<User> user;

    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        login = view.findViewById(R.id.login_btnLogin);

        etEmail = view.findViewById(R.id.login_etEmail);
        etPw = view.findViewById(R.id.login_etPw);
        tvFail = view.findViewById(R.id.login_tvFail);

        etEmail.setAlpha(v);
        etPw.setAlpha(v);
        login.setAlpha(v);

        etEmail.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        etPw.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();


        login.setOnClickListener(onClickListener);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            url = share.sUrl;
            uEmail = etEmail.getText().toString();
            uPw = etPw.getText().toString();
            Log.v("ggg","onClick");

            urlAddr = url + "login.jsp?email=" + uEmail + "&pw=" + uPw;

            Log.v("ggg","url" + urlAddr);

            connectLoginData();

            if (uEmail.equals(user.get(0).getuEmail())){

                share.sEmail = uEmail;

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("uEmail", uEmail);
                startActivity(intent);
                getActivity().finish();

            }else {
                tvFail.setText("로그인실패");
                etEmail.setText("");
                etPw.setText("");
                urlAddr="http://172.30.1.11:8080/contact/login.jsp?";
            }
        }
    };

    private void connectLoginData() {
        try {
            Log.v("ggg","getData");
            NetworkTask networkTask = new NetworkTask(getActivity(), urlAddr,"login");
            Object obj = networkTask.execute().get();
            user = (ArrayList<User>) obj;

            Log.v("ggg","userEmail" +user.get(0).getuEmail());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

} // ==