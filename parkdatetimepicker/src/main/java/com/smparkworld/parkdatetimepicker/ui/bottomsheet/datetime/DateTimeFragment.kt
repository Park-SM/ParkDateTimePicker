package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.databinding.FragmentDatetimeBinding
import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.ToolbarStatus

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
        parseArgument()
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    private fun parseArgument() {
        vm.setMode(arguments?.getSerializable(ExtraKey.EXTRA_MODE) as? DateTimeMode)
    }
    
    private fun initViews(binding: FragmentDatetimeBinding) {
        
    }

    private fun initObservers(binding: FragmentDatetimeBinding) {
        vm.toolbarStatus.observe(viewLifecycleOwner) { status ->
            when(status) {
                ToolbarStatus.DATE -> {
                    binding.layoutDateNavigator.container.isVisible = true
                }
                ToolbarStatus.TIME -> {
                    binding.layoutDateNavigator.container.isVisible = false
                }
            }
        }
    }

    fun setListeners(listener: BaseListener) {
        this.listener = listener
    }
}