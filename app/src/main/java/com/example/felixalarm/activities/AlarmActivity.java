package com.example.felixalarm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.felixalarm.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity {

//
//    boolean isOpened;

    String description;
    boolean isThemeChanged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Intent intent = getIntent();
        String descriptionT = intent.getStringExtra("description");
        description = descriptionT;
        boolean isThemeChangedT = intent.getBooleanExtra("theme", false);
        isThemeChanged = isThemeChangedT;

//
//        textMyNotes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

                //метод по срабатыванию будильника должен быть!
//                isOpened = true;

//                notesActivity.checkNotes(isOpened);
//
//                Intent i2 = new Intent(getApplicationContext(), NotesActivity.class);
//                i2.putExtra("flag", isOpened);
//                startActivity(i2);
//
//                Intent intent1 = new Intent(getApplicationContext(), AlarmOnActivity.class);
//                intent1.putExtra("description", descriptionT);
//                intent1.putExtra("theme", isThemeChanged);
//
//                startActivity(intent1);
//
//            }
//        });

//
//        currentTime = findViewById(R.id.textClock);
//        String currentTimeT = currentTime + " дп";
//
//        Timer t = new Timer();
//        t.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Intent intent = getIntent();
//                String date = intent.getStringExtra("date");
//
//                if (currentTimeT.equals(date)) {
//                    Intent intent1 = new Intent(getApplicationContext(), AlarmOnActivity.class);
//                    startActivity(intent1);
//
//
//                }
//            }
//        },0, 1000);

        SimpleDateFormat sdf =new SimpleDateFormat("HH:mm", Locale.getDefault());

        ImageView imageAddNewAlarm = findViewById(R.id.imageAddAlarm);
        imageAddNewAlarm.setOnClickListener( v -> {
            MaterialTimePicker materialTimePicker=new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .build();

            materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());

                AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);

                AlarmManager.AlarmClockInfo alarmClockInfo= new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(),getAlarmInfoPendingIntend());
                Toast.makeText(this,"alarm clock set on "+sdf.format(calendar.getTime()), Toast.LENGTH_LONG).show();

                alarmManager.setAlarmClock(alarmClockInfo,getAlarmActionPendingIntend());

            });

            materialTimePicker.show(getSupportFragmentManager(),"my_picker");


        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_alarm);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_alarm:
                        return true;
                    case R.id.nav_notes:
                        Intent intent1 = new Intent(getApplicationContext(), NotesActivity.class);
//                        intent1.putExtra("flag", isOpened);
//                        overridePendingTransition(0, 0);
                        startActivity(intent1);
                        return true;
                    case R.id.nav_weather:
                        Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
////                        intent.putExtra("flag", isOpened);
//                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getAlarmInfoPendingIntend(){
        Intent alarmInfoIntend = new Intent(this, AlarmActivity.class);
        alarmInfoIntend.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this,0,alarmInfoIntend, PendingIntent.FLAG_UPDATE_CURRENT);

    }
    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getAlarmActionPendingIntend(){
        Intent intent=new Intent(this,AlarmOnActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("description", description);
        intent.putExtra("theme", isThemeChanged);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,0);
        return  PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

}