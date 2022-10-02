package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.core.ColorManager
import com.smparkworld.parkdatetimepicker.databinding.FragmentDatetimeBinding
import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.ToolbarStatus

internal class DateTimeFragment : BottomSheetDialogFragment() {

    private var listener: BaseListener? = null

    private val vm: DateTimeViewModel by lazy {
        ViewModelProvider(this)[DateTimeViewModel::class.java]
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
        vm.init(arguments?.getSerializable(ExtraKey.EXTRA_MODE) as? DateTimeMode, listener)

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
        binding.layoutDateNavigator.btnPrev.setOnClickListener {

        }
        binding.layoutDateNavigator.btnNext.setOnClickListener {

        }
        binding.layoutDateNavigator.title.setOnClickListener {

        }
        ColorManager.applyTextColor(binding.title)
        ColorManager.applyTextColor(binding.layoutDateNavigator.title)
        ColorManager.applyImageTint(binding.layoutDateNavigator.btnPrev)
        ColorManager.applyImageTint(binding.layoutDateNavigator.btnNext)
        ColorManager.applyTextColor(binding.layoutDateNavigator.sun)
        ColorManager.applyTextColor(binding.layoutDateNavigator.mon)
        ColorManager.applyTextColor(binding.layoutDateNavigator.tue)
        ColorManager.applyTextColor(binding.layoutDateNavigator.wed)
        ColorManager.applyTextColor(binding.layoutDateNavigator.thu)
        ColorManager.applyTextColor(binding.layoutDateNavigator.fri)
        ColorManager.applyTextColor(binding.layoutDateNavigator.sat)
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
}