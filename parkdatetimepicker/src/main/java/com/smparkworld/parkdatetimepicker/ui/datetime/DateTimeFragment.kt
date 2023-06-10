package com.smparkworld.parkdatetimepicker.ui.datetime

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.core.ExtraKey
import com.smparkworld.parkdatetimepicker.databinding.PdtpFragmentDatetimeBinding
import com.smparkworld.parkdatetimepicker.extension.viewModels
import com.smparkworld.parkdatetimepicker.model.listener.BaseListener
import com.smparkworld.parkdatetimepicker.ui.applier.ColorArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.applier.TextArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.date.DateViewModel
import com.smparkworld.parkdatetimepicker.ui.datetime.model.Phase
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

    override fun getTheme(): Int {
        return R.style.PDTP_BottomSheetDialogTheme
    }

    fun setListener(listener: BaseListener) {
        this.listener = listener
    }

    private fun initArguments(binding: PdtpFragmentDatetimeBinding) {
        vm.init(listener)

        arguments?.getString(ExtraKey.EXTRA_PRIMARY_COLOR_CODE)?.let {
            ColorArgumentApplier.setPrimaryColor(Color.parseColor(it))
        }
        arguments?.getInt(ExtraKey.EXTRA_PRIMARY_COLOR_RES_ID, -1)?.let {
            if (it > 0) ColorArgumentApplier.setPrimaryColor(ContextCompat.getColor(requireContext(), it))
        }
        arguments?.getString(ExtraKey.EXTRA_HIGHLIGHT_COLOR_CODE)?.let {
            ColorArgumentApplier.setHighLightColor(Color.parseColor(it))
        }
        arguments?.getInt(ExtraKey.EXTRA_HIGHLIGHT_COLOR_RES_ID, -1)?.let {
            if (it > 0) ColorArgumentApplier.setHighLightColor(ContextCompat.getColor(requireContext(), it))
        }
        arguments?.getString(ExtraKey.EXTRA_TITLE)?.let {
            TextArgumentApplier.setTitle(it)
        }
        arguments?.getStringArray(ExtraKey.EXTRA_DAY_OF_WEEK_TEXTS)?.let {
            TextArgumentApplier.setDayOfWeekTexts(it)
        }
        arguments?.getString(ExtraKey.EXTRA_TIME_DONE_TEXT)?.let {
            TextArgumentApplier.setTimeDoneText(it)
        }
        arguments?.getStringArray(ExtraKey.EXTRA_AM_PM_TEXTS)?.let {
            TextArgumentApplier.setAmPmTexts(it)
        }
        arguments?.getInt(ExtraKey.EXTRA_TITLE_RES_ID, -1)?.let {
            if (it > 0) binding.title.setText(it)
        }
    }
    
    private fun initViews(binding: PdtpFragmentDatetimeBinding) {
        navigator.clearFragments(childFragmentManager)
        binding.reset.setOnClickListener {
            vm.onResetClicked()
        }
        binding.done.setOnClickListener {
            vm.onDoneClicked()
        }

        ColorArgumentApplier.applyPrimaryColor(binding.title)
        ColorArgumentApplier.applyPrimaryColor(binding.reset)
        ColorArgumentApplier.applyPrimaryColor(binding.done)
        ColorArgumentApplier.applyHighLightColor(binding.result)

        TextArgumentApplier.applyTitle(binding.title)
    }

    private fun initObservers(binding: PdtpFragmentDatetimeBinding) {
        vm.phase.observe(viewLifecycleOwner) { phaseData ->
            navigateFragment(binding, phaseData.oldPhase, phaseData.newPhase)
        }
        vm.result.observe(viewLifecycleOwner) { result ->
            binding.result.text = result
        }
        dateVm.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            vm.onSelectDate(selectedDate)
        }
        timeVm.selectedTime.observe(viewLifecycleOwner) { selectedTime ->
            vm.onSelectTime(selectedTime)
        }
    }

    private fun navigateFragment(binding: PdtpFragmentDatetimeBinding, oldPhase: Phase, newPhase: Phase) {
        navigator.beginTransaction()
            .addOldPhase(oldPhase)
            .addNewPhase(newPhase)
            .addOldPhaseHeaderView(getHeaderViewByPhase(binding, oldPhase))
            .addNewPhaseHeaderView(getHeaderViewByPhase(binding, newPhase))
            .addOnDone(::dismiss)
            .commit(R.id.fragment_container, childFragmentManager)
    }

    private fun getHeaderViewByPhase(binding: PdtpFragmentDatetimeBinding, phase: Phase): View? {
        return when (phase) {
//            Phase.DATE -> binding.layoutDateHeader.root
//            Phase.TIME -> binding.layoutTimeHeader.root
            else -> null
        }
    }
}