package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.smparkworld.parkdatetimepicker.databinding.FragmentDateBinding
import com.smparkworld.parkdatetimepicker.extension.getExtra
import com.smparkworld.parkdatetimepicker.model.DefaultOption
import com.smparkworld.parkdatetimepicker.model.ExtraKey

internal class DateFragment : Fragment() {

    private val vm: DateViewModel by lazy {
        ViewModelProvider(requireActivity())[DateViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDateBinding.inflate(inflater, container, false)

        initViews(binding)
        initObservers(binding)
        initArguments()
        return binding.root
    }

    private fun initArguments() {
        vm.init(
            minYearDiff = getExtra(ExtraKey.EXTRA_MIN_YEAR_DIFF, DefaultOption.DEFAULT_MIN_YEAR_DIFF),
            maxYearDiff = getExtra(ExtraKey.EXTRA_MAX_YEAR_DIFF, DefaultOption.DEFAULT_MAX_YEAR_DIFF)
        )
    }

    private fun initViews(binding: FragmentDateBinding) {
        binding.container.adapter = DateMonthAdapter(vm::onSelectDate)
    }

    private fun initObservers(binding: FragmentDateBinding) {
        vm.months.observe(viewLifecycleOwner) { months ->
            (binding.container.adapter as? DateMonthAdapter)?.submitList(months)
        }
    }
}