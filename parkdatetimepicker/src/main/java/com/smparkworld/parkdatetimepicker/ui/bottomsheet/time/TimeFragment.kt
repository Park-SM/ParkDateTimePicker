package com.smparkworld.parkdatetimepicker.ui.bottomsheet.time

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.smparkworld.parkdatetimepicker.databinding.FragmentTimeBinding

internal class TimeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTimeBinding.inflate(inflater, container, false)

        initViews(binding)
        initObservers(binding)
        initArguments(binding)
        return binding.root
    }

    private fun initViews(binding: FragmentTimeBinding) {
        binding.pickerAmPm.minValue = 0
        binding.pickerAmPm.maxValue = 1
        binding.pickerAmPm.displayedValues = arrayOf("AM", "PM")
        binding.pickerAmPm.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        binding.pickerHour.minValue = 1
        binding.pickerHour.maxValue = 12
        binding.pickerHour.wrapSelectorWheel = true

        binding.pickerMinute.minValue = 0
        binding.pickerMinute.maxValue = 59
        binding.pickerMinute.setFormatter { String.format("%02d", it) }
    }

    private fun initObservers(binding: FragmentTimeBinding) {

    }

    private fun initArguments(binding: FragmentTimeBinding) {

    }
}