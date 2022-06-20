package com.example.felixalarm.activities;

import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.felixalarm.R;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class AlarmOnActivity extends AppCompatActivity {
    Ringtone ringtone;
    GifImageView gifBack;
    private ImageView buttonAlarmOff;
    TextView textTime;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_on);
        buttonAlarmOff=findViewById(R.id.button_alarm_off);
        textTime = findViewById(R.id.textView2);
////        gifBack = findViewById(R.id.gifBackOnAlarm);


//   //     Intent i = getIntent();
//        int notesAmount = i.getIntExtra("size",0);
//
//        Intent intent = getIntent();
//        String description = intent.getStringExtra("description");
//        boolean isThemeChanged = intent.getBooleanExtra("theme", false);
//
//        if (description != null) {
//
//            if (isThemeChanged == false) {
//                gifBack.setAlpha(0.35F);
//                switch (description) {
//                    case "clear sky":
//                        gifBack.setImageResource(R.drawable.sun_clouds);
//                        buttonAlarmOff.setImageResource(R.drawable.button_sun);
//                        break;
//                    case "few clouds": //серые тучки, но с солнышком кек
//                        gifBack.setImageResource(R.drawable.sky_clouds);
//                        buttonAlarmOff.setImageResource(R.drawable.button_clouds);
//                        break;
//                    case "scattered clouds":  //серые тучки без грозы(без черых тучек)
//                    case "broken clouds":
//                        gifBack.setImageResource(R.drawable.half_gray_clouds);
//                        buttonAlarmOff.setImageResource(R.drawable.button_clouds);
//                        //текстура half gray clouds кривая
//                        break;
//                    case "overcast clouds": //с черными тучками!
//                        gifBack.setImageResource(R.drawable.gray_clouds);//overcast n broker have same icon
//                        buttonAlarmOff.setImageResource(R.drawable.button_clouds);
//                        break;
//                    case "light rain": //слабенький дождь
//                        gifBack.setImageResource(R.drawable.raindrops);
//                        buttonAlarmOff.setImageResource(R.drawable.button_rain);
//                        break;
//                    case "moderate rain": //умеренный дождь
//                        gifBack.setImageResource(R.drawable.hard_rain);
//                        buttonAlarmOff.setImageResource(R.drawable.button_rain);
//                        break;
//                }
//            } else if (isThemeChanged == true){
//                gifBack.setAlpha(0.9F);
//                switch (description) {
//                    case "clear sky":
//                        gifBack.setImageResource(R.drawable.sunny_w);
//                        buttonAlarmOff.setImageResource(R.drawable.button_sun);
//                        break;
//                    case "few clouds": //серые тучки, но с солнышком кек
//                    case "scattered clouds":  //серые тучки без грозы(без черых тучек)
//                    case "broken clouds":
//                    case "overcast clouds": //с черными тучками!
//                        gifBack.setImageResource(R.drawable.cloudy_w);//overcast n broker have same icon
//                        buttonAlarmOff.setImageResource(R.drawable.button_clouds);
//                        break;
//                    case "light rain": //слабенький дождь
//                    case "moderate rain": //умеренный дождь
//                        gifBack.setImageResource(R.drawable.snowy_w); //потом изменим на rainy(когда Аня нарисует кек)
//                        buttonAlarmOff.setImageResource(R.drawable.button_rain);
//                        break;
//                }
//
//            }
//        } else {
//            gifBack.setImageResource(R.drawable.background_create_alarm);
//            textTime.setTextColor(Color.BLACK);
//
//    //    }



        //устанавливаем базовый рингтон
        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone =RingtoneManager.getRingtone(this,notificationUri);
        //если базовый рингтон не установлен
        if (ringtone==null){
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone =RingtoneManager.getRingtone(this,notificationUri);
        }
        if (ringtone!= null){
            ringtone.play();
        }

//      //      buttonAlarmOff.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    count += 1;
//
//                    if (count < 10) {
//
//                        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_translate);
//                        buttonAlarmOff.startAnimation(anim);
//
//                        Random random = new Random();
//                        int num = 10;
//
//                        switch(random.nextInt(num)) {
//                            case 0:
//                                float x = 700;
//                                float y = 400;
//                                buttonAlarmOff.setX((float) x);
//                                buttonAlarmOff.setY((float) y);
//                                break;
//                            case 1:
//                                float x2 = 500;
//                                float y2 = 700;
//                                buttonAlarmOff.setX((float) x2);
//                                buttonAlarmOff.setY((float) y2);
//                                break;
//                            case 2:
//                                float x3 = 300;
//                                float y3 = 1000;
//                                buttonAlarmOff.setX((float) x3);
//                                buttonAlarmOff.setY((float) y3);
//                                break;
//                            case 3:
//                                float x4 = 400;
//                                float y4 = 900;
//                                buttonAlarmOff.setX((float) x4);
//                                buttonAlarmOff.setY((float) y4);
//                                break;
//                            case 4:
//                                float x5 = 100;
//                                float y5 = 500;
//                                buttonAlarmOff.setX((float) x5);
//                                buttonAlarmOff.setY((float) y5);
//                                break;
//                            case 5:
//                                float x6 = 300;
//                                float y6 = 500;
//                                buttonAlarmOff.setX((float) x6);
//                                buttonAlarmOff.setY((float) y6);
//                                break;
//                            case 6:
//                                float x7 = 800;
//                                float y7 = 1100;
//                                buttonAlarmOff.setX((float) x7);
//                                buttonAlarmOff.setY((float) y7);
//                                break;
//                            case 7:
//                                float x8 = 600;
//                                float y8 = 600;
//                                buttonAlarmOff.setX((float) x8);
//                                buttonAlarmOff.setY((float) y8);
//                                break;
//                            case 8:
//                                float x9 = 400;
//                                float y9 = 800;
//                                buttonAlarmOff.setX((float) x9);
//                                buttonAlarmOff.setY((float) y9);
//                                break;
//                            case 9:
//                                float x10 = 100;
//                                float y10 = 1100;
//                                buttonAlarmOff.setX((float) x10);
//                                buttonAlarmOff.setY((float) y10);
//                                break;
//
//                        }
//
//                        Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_translate2);
//      //                  buttonAlarmOff.startAnimation(anim2);


//       //             } else {
//                        ringtone.stop();
//
//
//                        Intent i = new Intent(AlarmOnActivity.this, AlarmActivity.class);
//                        setContentView(R.layout.activity_notes);

//                        String text = "It's your notes! Don't forget to read them!";
//                        Toast.makeText(AlarmOnActivity.this, text, Toast.LENGTH_LONG).show();
//                        startActivity(i);


//                        Intent i;
//                        if (notesAmount != 0) {
//                            i = new Intent(AlarmOnActivity.this, NotesActivity.class);
//                            String text = "Now you have " + notesAmount + " notes! Dont forget to read them!";
//                            Toast.makeText(AlarmOnActivity.this, text, Toast.LENGTH_LONG).show();
//                        } else {
//                            i = new Intent(AlarmOnActivity.this, AlarmActivity.class);
//                        }
//      //                  startActivity(i);

                    }

    @Override
    protected void onDestroy() {
        if(ringtone!=null && ringtone.isPlaying())
        {
            ringtone.stop();
        }
        super.onDestroy();
    }
}
//            });
//
//    }
//}
