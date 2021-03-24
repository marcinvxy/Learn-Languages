package com.example.learnlanguages;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        notification(9, 0, 0);
    }

    public void startQuizActivity(View view) {
        Intent intent = new Intent(this, ChoseQuizActivity.class);
        startActivity(intent);
    }

    public void addWordsActivity(View view){
        Intent intent = new Intent(this, AddLanguagesPairsActivity.class);
        startActivity(intent);
    }

    public void exit(View view){
        System.exit(0);

    }

    private void notification(int hour, int minute, int second){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Intent intent = new Intent(getApplicationContext(), Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}





