package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.range;

import com.smparkworld.parkdatetimepicker.model.SelectedDateTime;

public interface DateTimeRangeListener {

    void onSelectDateTimeRange(SelectedDateTime startDateTime, SelectedDateTime endDateTime);
}
