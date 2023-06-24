package com.smparkworld.parkdatetimepicker.ui.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.smparkworld.parkdatetimepicker.databinding.PdtpFragmentDateBinding
import com.smparkworld.parkdatetimepicker.extension.parentViewModels
import com.smparkworld.parkdatetimepicker.ui.applier.ColorArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.date.model.CalendarControlEvent

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
        binding.btnPrev.setOnClickListener {
            vm.onClickCalendarControl(CalendarControlEvent.PrevPage)
        }
        binding.btnNext.setOnClickListener {
            vm.onClickCalendarControl(CalendarControlEvent.NextPage)
        }

        binding.container.adapter = DateMonthAdapter(vm::onSelectDate)
        binding.container.registerOnPageChangeCallback(viewPagerCallback)

        ColorArgumentApplier.applyPrimaryColor(binding.title)
        ColorArgumentApplier.applyPrimaryColor(binding.btnPrev)
        ColorArgumentApplier.applyPrimaryColor(binding.btnNext)
        ColorArgumentApplier.applyPrimaryColor(binding.sun)
        ColorArgumentApplier.applyPrimaryColor(binding.mon)
        ColorArgumentApplier.applyPrimaryColor(binding.tue)
        ColorArgumentApplier.applyPrimaryColor(binding.wed)
        ColorArgumentApplier.applyPrimaryColor(binding.thu)
        ColorArgumentApplier.applyPrimaryColor(binding.fri)
        ColorArgumentApplier.applyPrimaryColor(binding.sat)
    }

    private fun initObservers(binding: PdtpFragmentDateBinding) {
        vm.selectedDateTitle.observe(viewLifecycleOwner) { title ->
            binding.title.text = title
        }
        vm.weeks.observe(viewLifecycleOwner) { weeks ->
            binding.sun.text = weeks.getOrNull(0)
            binding.mon.text = weeks.getOrNull(1)
            binding.tue.text = weeks.getOrNull(2)
            binding.wed.text = weeks.getOrNull(3)
            binding.thu.text = weeks.getOrNull(4)
            binding.fri.text = weeks.getOrNull(5)
            binding.sat.text = weeks.getOrNull(6)
        }
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