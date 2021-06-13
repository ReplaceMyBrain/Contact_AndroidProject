package com.aoslec.contactproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;

public class ProfileEditActivity extends AppCompatActivity {

    ImageView call,message;

    Share share = new Share();
    String url,urlAddr;

    String uEmail, pName, pTel, pFavorite, pGroup;
    String pImg = "0";

    EditText etName,etTel;
    CheckBox cbFavorite;
    ImageView img;
    Button btnEdit;
    TextView tv_group;

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
        tv_group = findViewById(R.id.edit_group);
        btnEdit= findViewById(R.id.edit_btnEdit);

        call = findViewById(R.id.edit_Call);
        message = findViewById(R.id.edit_Message);


        //클릭이벤트트
        btnEdi.setOnClickListener(onClickListener);
        tv_group.setOnClickListener(groupClick);
        call.setOnClickListener(Click);
        message.setOnClickListener(Click);

    }//c

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

}//--