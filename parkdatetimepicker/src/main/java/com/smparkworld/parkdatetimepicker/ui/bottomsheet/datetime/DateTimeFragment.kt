package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.core.ColorManager
import com.smparkworld.parkdatetimepicker.databinding.FragmentDatetimeBinding
import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.DateFragment
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.DateViewModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.CalendarControlEvent
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.ToolbarStatus

internal class DateTimeFragment : BottomSheetDialogFragment() {

    private var listener: BaseListener? = null

    private val vm: DateTimeViewModel by lazy {
        ViewModelProvider(this)[DateTimeViewModel::class.java]
    }

    private val dateVm: DateViewModel by lazy {
        ViewModelProvider(requireActivity())[DateViewModel::class.java]
    }

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
        vm.toolbarStatus.observe(viewLifecycleOwner) { status ->
            when(status) {
                ToolbarStatus.DATE -> {
                    binding.layoutDateHeader.container.isVisible = true
                    childFragmentManager.beginTransaction().add(R.id.fragment_container, DateFragment(), DateFragment::class.simpleName).commit()
                }
                ToolbarStatus.TIME -> {
                    binding.layoutDateHeader.container.isVisible = false
                }
            }
        }
        dateVm.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            Log.d("Test!!", "Selected date is $selectedDate")
        }
        dateVm.selectedDateTitle.observe(viewLifecycleOwner) { title ->
            binding.layoutDateHeader.title.text = title
        }
    }
}