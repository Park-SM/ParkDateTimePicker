package com.smparkworld.parkdatetimepicker.ui.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.smparkworld.parkdatetimepicker.databinding.PdtpFragmentDateBinding
import com.smparkworld.parkdatetimepicker.extension.parentViewModels

internal class DateFragment : Fragment() {

    private val vm: DateViewModel by parentViewModels()

    private val viewPagerCallback by lazy {
        object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                vm.onScrollMonth(position)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PdtpFragmentDateBinding.inflate(inflater, container, false)

        initViews(binding)
        initObservers(binding)
        return binding.root
    }

    private fun initViews(binding: PdtpFragmentDateBinding) {
        binding.container.adapter = DateMonthAdapter(vm::onSelectDate)
        binding.container.registerOnPageChangeCallback(viewPagerCallback)
    }

    private fun initObservers(binding: PdtpFragmentDateBinding) {
        vm.months.observe(viewLifecycleOwner) { monthsData ->
            (binding.container.adapter as? DateMonthAdapter)?.submitList(monthsData.first)
            binding.container.setCurrentItem(monthsData.second, false)
        }
        vm.monthPosition.observe(viewLifecycleOwner) { position ->
            if (binding.container.currentItem != position) {
                binding.container.currentItem = position
            }
        }
    }
}