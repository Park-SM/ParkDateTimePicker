package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener;

import androidx.annotation.NonNull;

import com.smparkworld.parkdatetimepicker.model.BaseListener;
import com.smparkworld.parkdatetimepicker.model.SelectedDateTime;

public interface DateTimeListener extends BaseListener {

    void onSelectDateTime(@NonNull SelectedDateTime dateTime);
}
