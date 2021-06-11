package com.aoslec.contactproject.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aoslec.contactproject.Activity.ExplainActivity;
import com.aoslec.contactproject.Activity.LoginActivity;
import com.aoslec.contactproject.Activity.MainActivity;
import com.aoslec.contactproject.R;

public class LoginFragment extends Fragment {

    Button login;
    EditText email,pw;
    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        login = view.findViewById(R.id.login_btnLogin);
        email = view.findViewById(R.id.login_etEmail);
        pw = view.findViewById(R.id.login_etPw);

        email.setAlpha(v);
        pw.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        pw.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();


        login.setOnClickListener(onClickListener);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    };



}//==