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
import android.widget.Toast;

import com.aoslec.contactproject.NetworkTask.ImageNetworkTask;
import com.aoslec.contactproject.NetworkTask.NetworkTask;
import com.aoslec.contactproject.R;
import com.aoslec.contactproject.Utill.Share;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileRegisterActivity extends AppCompatActivity {

    Share share = new Share();
    String url,urlAddr;

    String uEmail, pName, pTel, pFavorite, pGroup, imageName,pImg;

    EditText etName,etTel;
    CheckBox cbFavorite;
    ImageView img;
    Button btnRegister;
    TextView tvGroup;

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
        setContentView(R.layout.activity_profile_register);


        //          사용자에게 사진(Media) 사용 권한 받기
        ActivityCompat.requestPermissions(ProfileRegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);


        setTitle("주소록 등록");

        etName = findViewById(R.id.register_name);
        etTel = findViewById(R.id.register_tel);
        cbFavorite = findViewById(R.id.register_favorite);
        img = findViewById(R.id.register_image);
        btnRegister = findViewById(R.id.register_btnRegister);
        tvGroup = findViewById(R.id.register_group);

        btnRegister.setOnClickListener(onClickListener);
        tvGroup.setOnClickListener(groupClick);
        img.setOnClickListener(imgLoad);


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

            if(cbFavorite.isChecked()){
                pFavorite = "true";
            }else {
                pFavorite = "false";
            }

            //이미지업로드 메소스 실행

            pImg = imageName;

                if(pImg == null){

                }else {
                    urlAddr = url + "imgUpload.jsp";
                    imageUpload();
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
                Intent intent = new Intent(ProfileRegisterActivity.this, ProfileRegisterActivity.class);
                startActivity(intent);
                finish();

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
        ImageNetworkTask networkTask = new ImageNetworkTask(ProfileRegisterActivity.this, img, img_path, urlAddr);

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

                Glide.with(ProfileRegisterActivity.this)
                        .load(image_bitmap_copy)
                        .circleCrop()
                        .error(R.drawable.face)
                        .into(img);

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



    //리사이클러뷰 클릭리스터 액티비티에서 처리방법
//    DialogInterface.OnClickListener groupAdd = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            final LinearLayout linear = (LinearLayout) View.inflate(ProfileRegisterActivity.this, R.layout.dialog_group_add, null);
//            new AlertDialog.Builder(ProfileRegisterActivity.this)
//                    .setTitle("그룹의 이름을 적어주세요.")
//                    .setIcon(R.drawable.group)
//                    .setView(linear)
//                    .setPositiveButton("추가", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            String[] group = getResources().getStringArray(R.array.group);
//                            EditText name = linear.findViewById(R.id.dialog_group_etName);
//
//                        }
//                    })
//                    .setNegativeButton("닫기",null);
//
//            }
//        };

}//--