package com.smparkworld.parkdatetimepicker.model.listener;

import com.smparkworld.parkdatetimepicker.model.DateTimeResult;

public interface DateTimeRangeListener extends BaseListener {

    void onSelectDateTimeRange(DateTimeResult startDateTime, DateTimeResult endDateTime);
}
