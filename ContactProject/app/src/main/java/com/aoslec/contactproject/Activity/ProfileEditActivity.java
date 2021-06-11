package com.aoslec.contactproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aoslec.contactproject.R;

public class ProfileEditActivity extends AppCompatActivity {

    ImageView call,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        call = findViewById(R.id.profileCall);
        message = findViewById(R.id.profileMessage);
        //       favorite = findViewById(R.id.profileFavorite);

        call.setOnClickListener(Click);
        message.setOnClickListener(Click);
        //       favorite.setOnClickListener(Click);
    }//c

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.profileCall:
                    startActivity(new Intent(Intent.ACTION_DIAL));
                case R.id.profileMessage:
                    startActivity(new Intent(Intent.ACTION_SEND));
            }

        }
    };

}//--