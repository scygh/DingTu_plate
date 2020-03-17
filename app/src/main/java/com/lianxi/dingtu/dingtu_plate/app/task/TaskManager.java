package com.lianxi.dingtu.dingtu_plate.app.task;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;


public class TaskManager extends Observable {
    private static final String TAG = "TaskManager";

    public static final Integer CANCEL_ALL = 1;

    public void cancelAll() {
        Log.d(TAG, "All task Cancelled.");
        setChanged();
        notifyObservers(CANCEL_ALL);
    }

    public void addTask(Observer task) {
        super.addObserver(task);
    }


}
