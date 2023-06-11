package com.smparkworld.parkdatetimepicker.ui.datetime.navigator

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.smparkworld.parkdatetimepicker.ui.datetime.model.Phase

internal interface DateTimeFragmentNavigator {

    fun beginTransaction(): PhaseTransaction

    fun clearFragments(manager: FragmentManager)
}

internal interface PhaseTransaction {

    fun setArguments(args: Bundle?): PhaseTransaction

    fun addOldPhase(oldPhase: Phase): PhaseTransaction

    fun addNewPhase(newPhase: Phase): PhaseTransaction

    fun addOldPhaseHeaderView(oldHeaderView: View?): PhaseTransaction

    fun addNewPhaseHeaderView(newHeaderView: View?): PhaseTransaction

    fun addOnDone(callback: () -> Unit): PhaseTransaction

    fun commit(@IdRes containerId: Int, manager: FragmentManager)
}