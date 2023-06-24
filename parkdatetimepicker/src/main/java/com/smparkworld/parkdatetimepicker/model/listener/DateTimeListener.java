package com.smparkworld.parkdatetimepicker.model.listener;

import androidx.annotation.NonNull;

import com.smparkworld.parkdatetimepicker.model.DateTimeResult;

public interface DateTimeListener extends BaseListener {

    void onSelectDateTime(@NonNull DateTimeResult dateTime);
}