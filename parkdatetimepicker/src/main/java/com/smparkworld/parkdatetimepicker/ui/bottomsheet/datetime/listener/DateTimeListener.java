package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener;

import com.smparkworld.parkdatetimepicker.model.BaseListener;
import com.smparkworld.parkdatetimepicker.model.SelectedDateTime;

public interface DateTimeListener extends BaseListener {

    void onSelectDateTime(SelectedDateTime dateTime);
}
