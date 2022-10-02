package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smparkworld.parkdatetimepicker.databinding.FragmentDatetimeBinding
import com.smparkworld.parkdatetimepicker.model.BaseListener

internal class DateTimeFragment : BottomSheetDialogFragment() {

    private lateinit var listener: BaseListener

    private val vm: DateTimeViewModel by lazy {
        ViewModelProvider(this)[DateTimeViewModel::class.java]
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

    fun setListeners(listener: BaseListener) {
        this.listener = listener
    }
}