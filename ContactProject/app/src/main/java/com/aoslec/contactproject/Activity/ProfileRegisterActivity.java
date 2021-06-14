package com.aoslec.contactproject.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aoslec.contactproject.NetworkTask.NetworkTask;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;

public class ProfileRegisterActivity extends AppCompatActivity {

    Share share = new Share();
    String url,urlAddr;

    String uEmail, pName, pTel, pFavorite, pGroup;
    String pImg = "0";

    EditText etName,etTel;
    CheckBox cbFavorite;
    ImageView img;
    Button btnRegister;
    TextView tvGroup;

    //Dialog
    int mSelect=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_register);

        etName = findViewById(R.id.register_name);
        etTel = findViewById(R.id.register_tel);
        cbFavorite = findViewById(R.id.register_favorite);
        img = findViewById(R.id.register_image);
        btnRegister = findViewById(R.id.register_btnRegister);
        tvGroup = findViewById(R.id.register_group);

        btnRegister.setOnClickListener(onClickListener);
        tvGroup.setOnClickListener(groupClick);

    }//c

    //등록시작
    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            uEmail = share.sEmail;
            url = share.sUrl;

            pName = etName.getText().toString();
            pTel = etTel.getText().toString();
            pGroup = tvGroup.getText().toString();
//            pImg = ;

            if(cbFavorite.isChecked()){
                pFavorite = "true";
            }else {
                pFavorite = "false";
            }

            urlAddr = url + "profileRegister.jsp?email=" + uEmail + "&name=" + pName + "&tel=" + pTel + "&img=" + pImg + "&group=" + pGroup + "&favorite=" + pFavorite;

            Log.v("ggg","register url" + urlAddr);
            String result = connectRegisterDate();
            Log.v("ggg","register last");

            if (result.equals("1")){

                new AlertDialog.Builder(ProfileRegisterActivity.this)
                        .setTitle("등록")
                        .setMessage(pName + "님이 주소록에 등록되었습니다.")
                        .setIcon(R.drawable.face)
                        .setCancelable(false)
                        .setPositiveButton("추가등록", onClick)
                        .setNegativeButton("닫기", onClick)
                        .show();
                Log.v("ggg","register good");
            }else {
                new AlertDialog.Builder(ProfileRegisterActivity.this)
                        .setTitle("등록실패")
                        .setMessage("주소록등록을 실패하였습니다")
                        .setIcon(R.drawable.face)
                        .setCancelable(false)
                        .setPositiveButton("다시등록", onClick)
                        .setNegativeButton("닫기", onClick)
                        .show();
                Log.v("ggg","register bad");
            }
        }
    };

    private String connectRegisterDate() {
        String result = null;
        try {
            NetworkTask networkTask = new NetworkTask(ProfileRegisterActivity.this, urlAddr,"insert");
            Object obj = networkTask.execute().get();
            result = (String) obj;
            Log.v("ggg","register" + result);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Log.v("ggg","onClick");
            if(which==DialogInterface.BUTTON_POSITIVE){
               etName.setText("");
               etTel.setText("");
                tvGroup.setText("없음");
               urlAddr="";
               cbFavorite.setChecked(false);

            }else{
                Intent intent = new Intent(ProfileRegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }

        }
    };

    //등록끝
    // 그룹다이얼로그
    View.OnClickListener groupClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v("ggg","groupClick");

            new AlertDialog.Builder(ProfileRegisterActivity.this)
                    .setTitle("설정할 그룹을 지정해주세요.")
                    .setIcon(R.drawable.group)
                    .setSingleChoiceItems(R.array.group, mSelect, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSelect = which;
                        }
                    })
                    .setCancelable(false)
                    .setPositiveButton("확인", groupDialogClick)
                    .setNegativeButton("닫기", null)
                    .setNeutralButton("추가", null)
                    .show();

        }
    };

    //확인 눌렀을 경우
    DialogInterface.OnClickListener groupDialogClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            String[] group = getResources().getStringArray(R.array.group); //리스트 불러오고
            tvGroup.setText(group[mSelect]);

        }
    };


    DialogInterface.OnClickListener groupAdd = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            final LinearLayout linear = (LinearLayout) View.inflate(ProfileRegisterActivity.this, R.layout.dialog_group_add, null);
            new AlertDialog.Builder(ProfileRegisterActivity.this)
                    .setTitle("그룹의 이름을 적어주세요.")
                    .setIcon(R.drawable.group)
                    .setView(linear)
                    .setPositiveButton("추가", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String[] group = getResources().getStringArray(R.array.group);
                            EditText name = linear.findViewById(R.id.dialog_group_etName);

                        }
                    })
                    .setNegativeButton("닫기",null);

            }
        };

}//--