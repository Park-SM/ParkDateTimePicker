package com.smparkworld.parkdatetimepicker.ui.bottomsheet.time;

import com.smparkworld.parkdatetimepicker.model.BaseListener;
import com.smparkworld.parkdatetimepicker.model.SelectedTime;

public interface TimeListener extends BaseListener {

    void onSelectTime(SelectedTime time);
}
