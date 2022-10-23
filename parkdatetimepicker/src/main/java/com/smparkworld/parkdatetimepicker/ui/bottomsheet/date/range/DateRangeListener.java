package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.range;

import androidx.annotation.NonNull;

import com.smparkworld.parkdatetimepicker.model.BaseListener;
import com.smparkworld.parkdatetimepicker.model.SelectedDate;

public interface DateRangeListener extends BaseListener {

    void onSelectDateRange(@NonNull SelectedDate startDate, @NonNull SelectedDate endDate);
}
