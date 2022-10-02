package com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.range;

import com.smparkworld.parkdatetimepicker.model.SelectedTime;

public interface TimeRangeListener {

    void onSelectTimeRange(SelectedTime startTime, SelectedTime endTime);
}
