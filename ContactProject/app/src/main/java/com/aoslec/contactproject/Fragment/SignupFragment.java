package com.aoslec.contactproject.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aoslec.contactproject.NetworkTask.NetworkTask;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;

public class SignupFragment extends Fragment {

    Share share = new Share();
    String url,urlAddr;

    String uEmail, uPw, uTel;

    EditText etEmail, etPw, etTel;
    Button btnSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        etEmail = view.findViewById(R.id.singup_etEmail);
        etPw = view.findViewById(R.id.singup_etPw);
        etTel = view.findViewById(R.id.singup_etTel);

        etEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        etPw.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        etTel.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});

        btnSignUp = view.findViewById(R.id.singup_btnSingup);
        btnSignUp.setOnClickListener(onClickListener);

        return view;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            url=share.sUrl;

            uEmail = etEmail.getText().toString();
            uPw = etPw.getText().toString();
            uTel = etTel.getText().toString();

            Log.v("ggg","onClick");

            urlAddr = url + "singUp.jsp?email=" + uEmail + "&pw=" + uPw + "&tel=" + uTel;

            Log.v("ggg","url" + urlAddr);

            String result = connectInsertData();

            if (result.equals("1")){
                new AlertDialog.Builder(getActivity()) // ;지워야함!
                        .setTitle("회원가입")
                        .setMessage(uEmail+"님 회원가입 되었습니다")
                        .setIcon(R.drawable.face)
                        .setCancelable(false)
                        .setPositiveButton("닫기", null)
                        .show();
                etEmail.setText("");
                etPw.setText("");
                etTel.setText("");
                urlAddr="";

            }else {
                new AlertDialog.Builder(getActivity()) // ;지워야함!
                        .setTitle("가입실패")
                        .setMessage("회원가입을 실패하였습니다")
                        .setIcon(R.drawable.face)
                        .setCancelable(false)
                        .setPositiveButton("닫기", null)
                        .show();
                etEmail.setText("");
                etPw.setText("");
                etTel.setText("");
                urlAddr="";
            }
        }
    };

    private  String connectInsertData(){
        String result = null;
        try {
            NetworkTask networkTask = new NetworkTask(getActivity(), urlAddr,"signUp");
            Object obj = networkTask.execute().get();
            result = (String) obj;
            Log.v("ggg","signUp" + result);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

} // ==