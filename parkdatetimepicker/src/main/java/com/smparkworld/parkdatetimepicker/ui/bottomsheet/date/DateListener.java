package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date;

import com.smparkworld.parkdatetimepicker.model.BaseListener;
import com.smparkworld.parkdatetimepicker.model.SelectedDate;

public interface DateListener extends BaseListener {

    void onSelectDate(SelectedDate date);
}
