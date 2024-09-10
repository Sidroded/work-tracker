package com.sidroded.worktracker.service;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService {
    private final Timer timer = new Timer();
    private TimerTask timerTask;
    private Double time = 0.0;
    private String timerText = "00:00:00";
    private boolean timerStarted = false;
    private AppCompatActivity activity;
    private TextView textView;

    public TimerService() {
    }

    public TimerService(AppCompatActivity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public Timer getTimer() {
        return timer;
    }

    public Double getTime() {
        return time;
    }

    public boolean isTimerStarted() {
        return timerStarted;
    }

    public void setTimerStarted(boolean timerStarted) {
        this.timerStarted = timerStarted;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public void setTimerText(String timerText) {
        this.timerText = timerText;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    public void startTimer() {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setTime(getTime() + 1);
                        setTimerText(getTimerText());
                        textView.setText(getTimerText());
                    }
                });
            }

        };
        getTimer().scheduleAtFixedRate(timerTask, 0 ,1000);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    }
}
