package com.smparkworld.parkdatetimepicker.ui.time;

import com.smparkworld.parkdatetimepicker.model.listener.BaseListener;
import com.smparkworld.parkdatetimepicker.model.TimeResult;

public interface TimeListener extends BaseListener {

    void onSelectTime(TimeResult time);
}
