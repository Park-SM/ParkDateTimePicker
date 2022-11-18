package com.smparkworld.parkdatetimepicker.model.listener;

import androidx.annotation.NonNull;

import com.smparkworld.parkdatetimepicker.model.DateResult;

public interface DateRangeListener extends BaseListener {

    void onSelectDateRange(@NonNull DateResult startDate, @NonNull DateResult endDate);
}
