package com.smparkworld.parkdatetimepicker.model.listener;

import androidx.annotation.NonNull;

import com.smparkworld.parkdatetimepicker.model.DateResult;

public interface DateListener extends BaseListener {

    void onSelectDate(@NonNull DateResult date);
}