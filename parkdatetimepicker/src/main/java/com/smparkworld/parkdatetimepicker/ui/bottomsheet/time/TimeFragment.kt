package com.smparkworld.parkdatetimepicker.ui.bottomsheet.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.smparkworld.parkdatetimepicker.databinding.FragmentTimeBinding
import com.smparkworld.parkdatetimepicker.extension.parentViewModels
import com.smparkworld.parkdatetimepicker.ui.applier.TextArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.model.TimeUiModel

internal class TimeFragment : Fragment() {

    private val vm: TimeViewModel by parentViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTimeBinding.inflate(inflater, container, false)

        initViews(binding)
        initObservers(binding)
        return binding.root
    }

    private fun initViews(binding: FragmentTimeBinding) {
        binding.pickerAmPm.minValue = 0
        binding.pickerAmPm.maxValue = 1
        binding.pickerAmPm.let {
            TextArgumentApplier.applyAmPmTexts(it)
        }
        binding.pickerAmPm.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.pickerAmPm.setOnValueChangedListener { picker, _, value ->
            vm.onChangeValue(amPm = picker.displayedValues.getOrNull(value))
        }

        binding.pickerHour.minValue = 1
        binding.pickerHour.maxValue = 12
        binding.pickerHour.wrapSelectorWheel = true
        binding.pickerHour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.pickerHour.setOnValueChangedListener { _, _, value ->
            vm.onChangeValue(hour = value)
        }

        binding.pickerMinute.minValue = 0
        binding.pickerMinute.maxValue = 59
        binding.pickerMinute.setFormatter { String.format("%02d", it) }
        binding.pickerMinute.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.pickerMinute.setOnValueChangedListener { _, _, value ->
            vm.onChangeValue(minute = value)
        }
    }

    private fun initObservers(binding: FragmentTimeBinding) {
        vm.selectedTimeUiModel.observe(viewLifecycleOwner) { uiModel ->
            applyTimeValues(binding, uiModel)
        }
    }

    private fun applyTimeValues(binding: FragmentTimeBinding, uiModel: TimeUiModel) {
        val newValueIndex = binding.pickerAmPm.displayedValues.indexOf(uiModel.amPm)
        if (binding.pickerAmPm.value != newValueIndex) {
            binding.pickerAmPm.value = newValueIndex
        }
        if (binding.pickerHour.value != uiModel.hour) {
            binding.pickerHour.value = uiModel.hour
        }
        if (binding.pickerMinute.value != uiModel.minute) {
            binding.pickerMinute.value = uiModel.minute
        }
    }
}