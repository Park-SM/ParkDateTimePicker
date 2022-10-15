package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.view.View
import androidx.fragment.app.FragmentManager
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.Phase

internal interface DateTimeFragmentNavigator {

    fun beginTransaction(): PhaseTransaction
}

internal interface PhaseTransaction {

    fun addOldPhase(oldPhase: Phase?): PhaseTransaction

    fun addNewPhase(newPhase: Phase): PhaseTransaction

    fun addOldPhaseHeaderView(oldHeaderView: View?): PhaseTransaction

    fun addNewPhaseHeaderView(newHeaderView: View?): PhaseTransaction

    fun commit(manager: FragmentManager)
}