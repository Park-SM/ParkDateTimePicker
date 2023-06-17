package com.smparkworld.parkdatetimepicker.model.listener;

import com.smparkworld.parkdatetimepicker.model.TimeResult;

public interface TimeListener extends BaseListener {

    void onSelectTime(TimeResult time);
}