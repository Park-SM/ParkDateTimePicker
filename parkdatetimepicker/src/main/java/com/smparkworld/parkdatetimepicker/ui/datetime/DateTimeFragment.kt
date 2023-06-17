package com.smparkworld.parkdatetimepicker.ui.datetime

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.core.ExtraKey
import com.smparkworld.parkdatetimepicker.databinding.PdtpFragmentDatetimeBinding
import com.smparkworld.parkdatetimepicker.extension.viewModels
import com.smparkworld.parkdatetimepicker.model.listener.BaseListener
import com.smparkworld.parkdatetimepicker.ui.applier.ColorArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.date.DateViewModel
import com.smparkworld.parkdatetimepicker.ui.datetime.model.Phase
import com.smparkworld.parkdatetimepicker.ui.datetime.navigator.DateTimeFragmentNavigator
import com.smparkworld.parkdatetimepicker.ui.datetime.navigator.DateTimeFragmentNavigatorImpl
import com.smparkworld.parkdatetimepicker.ui.time.TimeViewModel

internal class DateTimeFragment : BottomSheetDialogFragment() {

    private var listener: BaseListener? = null
    private val navigator: DateTimeFragmentNavigator = DateTimeFragmentNavigatorImpl()

    private val vm: DateTimeViewModel by viewModels()

    private val dateVm: DateViewModel by viewModels()

    private val timeVm: TimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PdtpFragmentDatetimeBinding.inflate(inflater, container, false)

        initArguments(binding)
        initViews(binding)
        initObservers(binding)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            behavior.skipCollapsed = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun getTheme(): Int {
        return R.style.PDTP_BottomSheetDialogTheme
    }

    fun setListener(listener: BaseListener) {
        this.listener = listener
    }

    private fun initArguments(binding: PdtpFragmentDatetimeBinding) {
        vm.init(listener)

        arguments?.getInt(ExtraKey.EXTRA_PRIMARY_COLOR_INT, -1)?.takeIf { it > -1 }?.let {
            ColorArgumentApplier.setPrimaryColorInt(it)
        }
        arguments?.getInt(ExtraKey.EXTRA_HIGHLIGHT_COLOR_INT, -1)?.takeIf { it > -1 }?.let {
            ColorArgumentApplier.setHighLightColorInt(it)
        }
    }
    
    private fun initViews(binding: PdtpFragmentDatetimeBinding) {
        navigator.clearFragments(childFragmentManager)

        binding.reset.setOnClickListener {
            navigator.clearFragments(childFragmentManager)
            vm.onResetClicked()
            timeVm.onResetClicked()
        }
        binding.done.setOnClickListener {
            vm.onDoneClicked()
        }

        ColorArgumentApplier.applyPrimaryColor(binding.title)
        ColorArgumentApplier.applyPrimaryColor(binding.reset)
        ColorArgumentApplier.applyPrimaryColor(binding.done)
        ColorArgumentApplier.applyHighLightColor(binding.result)
    }

    private fun initObservers(binding: PdtpFragmentDatetimeBinding) {
        vm.viewState.observe(viewLifecycleOwner) { state ->
            binding.state = state
        }
        vm.phase.observe(viewLifecycleOwner) { phaseData ->
            navigateFragment(phaseData.oldPhase, phaseData.newPhase)
        }
        dateVm.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            vm.onSelectDate(selectedDate)
        }
        timeVm.selectedTime.observe(viewLifecycleOwner) { selectedTime ->
            vm.onSelectTime(selectedTime)
        }
    }

    private fun navigateFragment(oldPhase: Phase, newPhase: Phase) {
        navigator.beginTransaction()
            .setArguments(arguments)
            .addOldPhase(oldPhase)
            .addNewPhase(newPhase)
            .addOnDone(::dismiss)
            .commit(R.id.fragment_container, childFragmentManager)
    }
}