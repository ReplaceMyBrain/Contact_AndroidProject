package com.aoslec.contactproject.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoslec.contactproject.Adapter.ContactAdapter;
import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.NetworkTask.ImageNetworkTask;
import com.aoslec.contactproject.NetworkTask.NetworkTask;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileEditActivity extends AppCompatActivity {

    ImageView call,message;

    Share share = new Share();
    String url,urlAddr;

    String pName, pTel, pFavorite, pGroup, imageName, pImg;
    int pNo;

    EditText etName,etTel;
    CheckBox cbFavorite;
    ImageView img;
    Button btnEdit;
    TextView tvGroup;

    ArrayList<People> people;

    //Dialog
    int mSelect=0;

    //이미지 업로드에 쓰일 것
    String devicePath = Environment.getDataDirectory().getAbsolutePath() + "/data/com.aoslec.contactproject/";

    private final int REQ_CODE_SELECT_IMAGE = 300; // Gallery Return Code
    private String img_path = null; // 최종 file name
    private String f_ext = null;    // 최종 file extension
    File tempSelectFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        //          사용자에게 사진(Media) 사용 권한 받기
        ActivityCompat.requestPermissions(ProfileEditActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        setTitle("상세페이지");

        etName = findViewById(R.id.edit_name);
        etTel = findViewById(R.id.edit_tel);
        cbFavorite = findViewById(R.id.edit_favorite);
        img = findViewById(R.id.edit_image);
        tvGroup = findViewById(R.id.edit_group);
        btnEdit= findViewById(R.id.edit_btnEdit);

        call = findViewById(R.id.edit_Call);
        message = findViewById(R.id.edit_Message);


        btnEdit.setOnClickListener(btnEditClick);
        call.setOnClickListener(Click);
        message.setOnClickListener(Click);
        tvGroup.setOnClickListener(groupClick);
        img.setOnClickListener(imgLoad);

        //        tvGroup.setOnClickListener(groupClick);

    }//c

    @Override
    protected void onResume() {
        super.onResume();

        Log.v("ggg","onResume?" + imageName);

        url = share.sUrl;

        Intent intent = getIntent();

        if(imageName == null){

            pImg = intent.getStringExtra("img");
            Glide.with(ProfileEditActivity.this)
                    .load(url+"img/"+pImg)
                    .circleCrop()
                    .error(R.drawable.face)
                    .into(img);
        }else{
            pImg=imageName;
            urlAddr = url + "imgUpload.jsp";
            imageUpload();

            Glide.with(ProfileEditActivity.this)
                    .load(url+"img/"+pImg)
                    .circleCrop()
                    .error(R.drawable.face)
                    .into(img);
        }


        pNo = Integer.parseInt(intent.getStringExtra("no"));
        pName = intent.getStringExtra("name");
        pTel = intent.getStringExtra("tel");
        pGroup = intent.getStringExtra("group");
        pFavorite = intent.getStringExtra("favorite");
        etName.setText(pName);
        etTel.setText(pTel);

       tvGroup.setText(pGroup);

       if(pFavorite.equals("true")){
           cbFavorite.setChecked(true);
    }else {
           cbFavorite.setChecked(false);
       }


    }



    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edit_Call:
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+etTel.getText())));
                    break;
                case R.id.edit_Message:
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:"+etTel.getText())));
                    break;
            }

        }
    };

    View.OnClickListener btnEditClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            pName = etName.getText().toString();
            pTel = etTel.getText().toString();
            pGroup = tvGroup.getText().toString();

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


//그룹 다이얼로그
    View.OnClickListener groupClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v("ggg","groupClick");

            new AlertDialog.Builder(ProfileEditActivity.this)
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


    //이미지업로드 시작!!

    View.OnClickListener imgLoad = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Photo App.으로 이동
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
        }
    };

    private void imageUpload(){
        ImageNetworkTask networkTask = new ImageNetworkTask(ProfileEditActivity.this, img, img_path, urlAddr);

        //              NetworkTask Class의 doInBackground Method의 결과값을 가져온다.

        try {
            Integer result = networkTask.execute(100).get();

            //              doInBackground의 결과값으로 Toast생성

            switch (result){
                case 1:
                    //              Device에 생성한 임시 파일 삭제
                    File file = new File(img_path);
                    file.delete();

                    break;

                case 0:
                    break;
            }
            //////////////////////////////////////////////////////////////////////////////////////////////
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //Photo App.에서 Image 선택후 작업내용
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("ggg", "Data :" + String.valueOf(data));

        if (requestCode == REQ_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                //이미지의 URI를 얻어 경로값으로 반환.
                img_path = getImagePathToUri(data.getData());
                Log.v("ggg", "image path :" + img_path);
                Log.v("ggg", "Data :" +String.valueOf(data.getData()));

                //이미지를 비트맵형식으로 반환
                Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                Bitmap image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);

                Log.v("ggg", "img glide 1");
                Glide.with(ProfileEditActivity.this)
                        .load(image_bitmap_copy)
                        .circleCrop()
                        .error(R.drawable.face)
                        .into(img);
                Log.v("ggg", "img glide 2");

                // 파일 이름 및 경로 바꾸기(임시 저장, 경로는 임의로 지정 가능)
                String date = new SimpleDateFormat("yyyyMMddHm").format(new Date());
                imageName = date + "." + f_ext;
                tempSelectFile = new File(devicePath , imageName);
                OutputStream out = new FileOutputStream(tempSelectFile);
                image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                // 임시 파일 경로로 위의 img_path 재정의
                img_path = devicePath + imageName;


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //사용자가 선택한 이미지의 정보를 받아옴
    private String getImagePathToUri(Uri data) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        // 확장자 명 저장
        f_ext = imgPath.substring(imgPath.length()-3, imgPath.length());

        return imgPath;
    }



}//--