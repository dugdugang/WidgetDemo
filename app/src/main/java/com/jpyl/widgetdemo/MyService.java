package com.jpyl.widgetdemo;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import xml.NewAppWidget;

/**
 * Created by dg on 2017/2/20.
 */

public class MyService extends Service {

    private Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateViews();
            }


        }, 0, 1000);
    }

    private void updateViews() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        String time = format.format(new Date());
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{intent}, 0);
        views.setOnClickPendingIntent(R.id.button, pendingIntent);
        views.setTextViewText(R.id.button, time);
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName componentName = new ComponentName(getApplicationContext(), NewAppWidget.class);
        manager.updateAppWidget(componentName, views);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer = null;
    }
}
