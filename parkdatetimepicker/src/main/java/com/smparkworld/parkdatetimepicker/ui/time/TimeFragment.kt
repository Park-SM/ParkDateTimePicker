package com.smparkworld.parkdatetimepicker.ui.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.smparkworld.parkdatetimepicker.databinding.PdtpFragmentTimeBinding
import com.smparkworld.parkdatetimepicker.extension.parentViewModels
import com.smparkworld.parkdatetimepicker.ui.applier.ColorArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.time.model.TimeUiModel

internal class TimeFragment : Fragment() {

    private val vm: TimeViewModel by parentViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PdtpFragmentTimeBinding.inflate(inflater, container, false)

        initViews(binding)
        initObservers(binding)
        return binding.root
    }

    private fun initViews(binding: PdtpFragmentTimeBinding) {
        vm.onViewInitialized()

        binding.pickerAmPm.minValue = 0
        binding.pickerAmPm.maxValue = 1
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

        ColorArgumentApplier.applyPrimaryColor(binding.pickerAmPm)
        ColorArgumentApplier.applyPrimaryColor(binding.pickerHour)
        ColorArgumentApplier.applyPrimaryColor(binding.pickerMinute)
    }

    private fun initObservers(binding: PdtpFragmentTimeBinding) {
        vm.amPmTexts.observe(viewLifecycleOwner) { texts ->
            binding.pickerAmPm.displayedValues = texts.toTypedArray()
        }
        vm.selectedTimeUiModel.observe(viewLifecycleOwner) { uiModel ->
            applyTimeValues(binding, uiModel)
        }
    }

    private fun applyTimeValues(binding: PdtpFragmentTimeBinding, uiModel: TimeUiModel) {
        val newValueIndex = binding.pickerAmPm.displayedValues?.indexOf(uiModel.amPm) ?: return
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