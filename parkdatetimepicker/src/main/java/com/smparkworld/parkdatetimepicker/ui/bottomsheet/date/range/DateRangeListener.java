package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.range;

import com.smparkworld.parkdatetimepicker.model.BaseListener;
import com.smparkworld.parkdatetimepicker.model.SelectedDate;

public interface DateRangeListener extends BaseListener {

    void onSelectDateRange(SelectedDate startDate, SelectedDate endDate);
}
