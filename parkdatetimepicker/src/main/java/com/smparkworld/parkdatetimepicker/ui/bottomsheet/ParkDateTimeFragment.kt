package com.smparkworld.parkdatetimepicker.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smparkworld.parkdatetimepicker.databinding.FragmentDatetimeBinding

internal class ParkDateTimeFragment : BottomSheetDialogFragment() {

    private val vm: ParkDateTimeViewModel by lazy {
        ViewModelProvider(this)[ParkDateTimeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDatetimeBinding.inflate(inflater, container, false)

        initViews(binding)
        initObservers(binding)
        return binding.root
    }

    private fun initViews(binding: FragmentDatetimeBinding) {

    }

    private fun initObservers(binding: FragmentDatetimeBinding) {

    }
}