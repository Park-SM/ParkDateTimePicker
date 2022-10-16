package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.core.ColorManager
import com.smparkworld.parkdatetimepicker.databinding.FragmentDatetimeBinding
import com.smparkworld.parkdatetimepicker.extension.viewModels
import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.DateViewModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.CalendarControlEvent
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.Phase

internal class DateTimeFragment : BottomSheetDialogFragment() {

    private var listener: BaseListener? = null
    private val navigator: DateTimeFragmentNavigator = DateTimeFragmentNavigatorImpl()

    private val vm: DateTimeViewModel by viewModels()

    private val dateVm: DateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDatetimeBinding.inflate(inflater, container, false)

        initViews(binding)
        initObservers(binding)
        initArguments(binding)
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

        arguments?.getString(ExtraKey.EXTRA_TEXT_COLOR_CODE)?.let {
            ColorManager.setTextColor(Color.parseColor(it))
        }
        arguments?.getInt(ExtraKey.EXTRA_TEXT_COLOR_RES_ID, -1)?.let {
            if (it > 0) ColorManager.setTextColor(ContextCompat.getColor(requireContext(), it))
        }
        arguments?.getString(ExtraKey.EXTRA_TITLE)?.let {
            binding.title.text = it
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
        ColorManager.applyTextColor(binding.title)
        ColorManager.applyTextColor(binding.layoutDateHeader.title)
        ColorManager.applyImageTint(binding.layoutDateHeader.btnPrev)
        ColorManager.applyImageTint(binding.layoutDateHeader.btnNext)
        ColorManager.applyTextColor(binding.layoutDateHeader.sun)
        ColorManager.applyTextColor(binding.layoutDateHeader.mon)
        ColorManager.applyTextColor(binding.layoutDateHeader.tue)
        ColorManager.applyTextColor(binding.layoutDateHeader.wed)
        ColorManager.applyTextColor(binding.layoutDateHeader.thu)
        ColorManager.applyTextColor(binding.layoutDateHeader.fri)
        ColorManager.applyTextColor(binding.layoutDateHeader.sat)
    }

    private fun initObservers(binding: FragmentDatetimeBinding) {
        vm.phase.observe(viewLifecycleOwner) { phaseData ->
            navigateFragment(binding, phaseData.first, phaseData.second)
        }
        dateVm.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            vm.onSelectDate(selectedDate)
        }
        dateVm.selectedDateTitle.observe(viewLifecycleOwner) { title ->
            binding.layoutDateHeader.title.text = title
        }
    }

    private fun navigateFragment(binding: FragmentDatetimeBinding, oldPhase: Phase?, newPhase: Phase) {
        navigator.beginTransaction()
            .addOldPhase(oldPhase)
            .addNewPhase(newPhase)
            .addOldPhaseHeaderView(getHeaderViewByPhase(binding, oldPhase))
            .addNewPhaseHeaderView(getHeaderViewByPhase(binding, newPhase))
            .addOnDone(::dismiss)
            .commit(R.id.fragment_container, childFragmentManager)
    }

    private fun getHeaderViewByPhase(binding: FragmentDatetimeBinding, phase: Phase?): View? {
        return when (phase) {
            Phase.DATE -> binding.layoutDateHeader.root
            else -> null
        }
    }
}