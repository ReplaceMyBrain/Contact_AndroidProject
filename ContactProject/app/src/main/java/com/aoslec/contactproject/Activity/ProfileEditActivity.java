package com.aoslec.contactproject.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoslec.contactproject.Adapter.ContactAdapter;
import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.NetworkTask.NetworkTask;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProfileEditActivity extends AppCompatActivity {

    ImageView call,message;

    Share share = new Share();
    String url,urlAddr;

    String pName, pTel, pFavorite, pGroup, pImg;
    int pNo;

    EditText etName,etTel;
    CheckBox cbFavorite;
    ImageView img;
    Button btnEdit;
    TextView tvGroup;

    ArrayList<People> people;

    //Dialog
    int mSelect=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        etName = findViewById(R.id.edit_name);
        etTel = findViewById(R.id.edit_tel);
        cbFavorite = findViewById(R.id.edit_favorite);
        img = findViewById(R.id.edit_image);
        tvGroup = findViewById(R.id.edit_group);
        btnEdit= findViewById(R.id.edit_btnEdit);

        call = findViewById(R.id.edit_Call);
        message = findViewById(R.id.edit_Message);

    }//c

    @Override
    protected void onResume() {
        super.onResume();

        url = share.sUrl;

        Intent intent = getIntent();
        pNo = Integer.parseInt(intent.getStringExtra("no"));
        pName = intent.getStringExtra("name");
        pTel = intent.getStringExtra("tel");
        pImg = intent.getStringExtra("img");
        pGroup = intent.getStringExtra("group");
        pFavorite = intent.getStringExtra("favorite");

       etName.setText(pName);
       etTel.setText(pTel);
       Glide.with(ProfileEditActivity.this)
                .load(url+"img/"+pImg)
                .circleCrop()
                .error(R.drawable.face)
                .into(img);
       tvGroup.setText(pGroup);

       if(pFavorite.equals("true")){
           cbFavorite.setChecked(true);
    }else {
           cbFavorite.setChecked(false);
       }

        //클릭이벤트트
        btnEdit.setOnClickListener(btnEditClick);
//        tvGroup.setOnClickListener(groupClick);
        call.setOnClickListener(Click);
        message.setOnClickListener(Click);
    }

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edit_Call:
                    startActivity(new Intent(Intent.ACTION_DIAL));
                case R.id.edit_Message:
                    startActivity(new Intent(Intent.ACTION_SEND));
            }

        }
    };

    View.OnClickListener btnEditClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            pName = etName.getText().toString();
            pTel = etTel.getText().toString();
            pGroup = tvGroup.getText().toString();
   //         pImg = ;

            if(cbFavorite.isChecked()){
                pFavorite = "true";
            }else {
                pFavorite = "false";
            }

            urlAddr = url + "profileUpdate.jsp?no=" + pNo + "&name=" + pName + "&tel=" + pTel + "&img=" + pImg + "&group=" + pGroup + "&favorite=" + pFavorite ;

            Log.v("ggg","Update url : " + urlAddr);
            String result = connectUpdateDate();
            Log.v("ggg","Update last");

            if (result.equals("1")){

                new AlertDialog.Builder(ProfileEditActivity.this)
                        .setTitle("수정")
                        .setMessage(pName + "님의 정보가 수정되었습니다.")
                        .setIcon(R.drawable.face)
                        .setCancelable(false)
                        .setPositiveButton("확인", null)
                        .show();
                Log.v("ggg","Edit good");

                urlAddr = url + "profileUpdateList.jsp?no=" + pNo;

                //새로 받기시작
                connectListDate();
                Log.v("ggg","connectListDate url : " + urlAddr);

                etName.setText(people.get(0).getpName());
                etTel.setText(people.get(0).getpTel());
                Glide.with(ProfileEditActivity.this)
                        .load(url+"img/"+pImg)
                        .circleCrop()
                        .error(R.drawable.face)
                        .into(img);
                tvGroup.setText(people.get(0).getpGroup());

                if(people.get(0).getpFavorite().equals("true")){
                    cbFavorite.setChecked(true);
                }else {
                    cbFavorite.setChecked(false);
                }

            }else {
                new AlertDialog.Builder(ProfileEditActivity.this)
                        .setTitle("정보수정 실패")
                        .setMessage("정보수정을 실패하였습니다")
                        .setIcon(R.drawable.face)
                        .setCancelable(false)
                        .setPositiveButton("닫기", null)
                        .show();
                Log.v("ggg","Edit bad");
            }
        }
    };

    private String connectUpdateDate() {
        String result = null;
        try {
            NetworkTask networkTask = new NetworkTask(ProfileEditActivity.this, urlAddr,"insert");
            Object obj = networkTask.execute().get();
            result = (String) obj;
            Log.v("ggg","" + result);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    private void connectListDate() {
        try {
            Log.v("ggg","ListData");
            NetworkTask networkTask = new NetworkTask(ProfileEditActivity.this, urlAddr,"list");
            Object obj = networkTask.execute().get();
            people = (ArrayList<People>) obj;

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}//--