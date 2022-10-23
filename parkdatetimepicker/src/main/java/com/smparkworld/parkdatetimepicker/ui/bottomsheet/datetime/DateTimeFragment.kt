package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.core.CharacterManager
import com.smparkworld.parkdatetimepicker.core.ColorManager
import com.smparkworld.parkdatetimepicker.databinding.FragmentDatetimeBinding
import com.smparkworld.parkdatetimepicker.extension.viewModels
import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.DateViewModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.CalendarControlEvent
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.Phase
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.TimeViewModel

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
        val binding = FragmentDatetimeBinding.inflate(inflater, container, false)

        initArguments(binding)
        initViews(binding)
        initObservers(binding)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    fun setListener(listener: BaseListener) {
        this.listener = listener
    }

    private fun initArguments(binding: FragmentDatetimeBinding) {
        vm.init(listener)

        arguments?.getString(ExtraKey.EXTRA_PRIMARY_COLOR_CODE)?.let {
            ColorManager.setPrimaryColor(Color.parseColor(it))
        }
        arguments?.getInt(ExtraKey.EXTRA_PRIMARY_COLOR_RES_ID, -1)?.let {
            if (it > 0) ColorManager.setPrimaryColor(ContextCompat.getColor(requireContext(), it))
        }
        arguments?.getString(ExtraKey.EXTRA_TITLE)?.let {
            binding.title.text = it
        }
        arguments?.getStringArray(ExtraKey.EXTRA_DAY_OF_WEEK_TEXTS)?.let {
            CharacterManager.setDayOfWeekTexts(it)
        }
        arguments?.getString(ExtraKey.EXTRA_TIME_DONE_TEXT)?.let {
            CharacterManager.setTimeDoneText(it)
        }
        arguments?.getInt(ExtraKey.EXTRA_TITLE_RES_ID, -1)?.let {
            if (it > 0) binding.title.setText(it)
        }
    }
    
    private fun initViews(binding: FragmentDatetimeBinding) {
        navigator.clearFragments(childFragmentManager)

        binding.layoutDateHeader.btnPrev.setOnClickListener {
            dateVm.onClickCalendarControl(CalendarControlEvent.PrevPage)
        }
        binding.layoutDateHeader.btnNext.setOnClickListener {
            dateVm.onClickCalendarControl(CalendarControlEvent.NextPage)
        }
        binding.layoutDateHeader.title.setOnClickListener {
            dateVm.onClickCalendarControl(CalendarControlEvent.JumpPage(2022, 1))
        }
        binding.layoutTimeHeader.done.setOnClickListener {
            timeVm.onClickDone()
        }

        ColorManager.applyPrimaryColor(binding.title)
        ColorManager.applyPrimaryColor(binding.layoutDateHeader.title)
        ColorManager.applyPrimaryColor(binding.layoutDateHeader.btnPrev)
        ColorManager.applyPrimaryColor(binding.layoutDateHeader.btnNext)
        ColorManager.applyPrimaryColor(binding.layoutDateHeader.sun)
        ColorManager.applyPrimaryColor(binding.layoutDateHeader.mon)
        ColorManager.applyPrimaryColor(binding.layoutDateHeader.tue)
        ColorManager.applyPrimaryColor(binding.layoutDateHeader.wed)
        ColorManager.applyPrimaryColor(binding.layoutDateHeader.thu)
        ColorManager.applyPrimaryColor(binding.layoutDateHeader.fri)
        ColorManager.applyPrimaryColor(binding.layoutDateHeader.sat)
        ColorManager.applyPrimaryColor(binding.layoutTimeHeader.done)

        CharacterManager.applyDayOfWeekTexts(binding.layoutDateHeader)
        CharacterManager.applyTimeDoneText(binding.layoutTimeHeader)
    }

    private fun initObservers(binding: FragmentDatetimeBinding) {
        vm.phase.observe(viewLifecycleOwner) { phaseData ->
            navigateFragment(binding, phaseData.oldPhase, phaseData.newPhase)
        }
        dateVm.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            vm.onSelectDate(selectedDate)
        }
        dateVm.selectedDateTitle.observe(viewLifecycleOwner) { title ->
            binding.layoutDateHeader.title.text = title
        }
        timeVm.selectedTime.observe(viewLifecycleOwner) { selectedTime ->
            vm.onSelectTime(selectedTime)
        }
        timeVm.selectedTimeTitle.observe(viewLifecycleOwner) { title ->
            binding.layoutTimeHeader.title.text = title
        }
    }

    private fun navigateFragment(binding: FragmentDatetimeBinding, oldPhase: Phase, newPhase: Phase) {
        navigator.beginTransaction()
            .addOldPhase(oldPhase)
            .addNewPhase(newPhase)
            .addOldPhaseHeaderView(getHeaderViewByPhase(binding, oldPhase))
            .addNewPhaseHeaderView(getHeaderViewByPhase(binding, newPhase))
            .addOnDone(::dismiss)
            .commit(R.id.fragment_container, childFragmentManager)
    }

    private fun getHeaderViewByPhase(binding: FragmentDatetimeBinding, phase: Phase): View? {
        return when (phase) {
            Phase.DATE -> binding.layoutDateHeader.root
            Phase.TIME -> binding.layoutTimeHeader.root
            else -> null
        }
    }
}