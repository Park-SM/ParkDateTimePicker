package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.parkdatetimepicker.databinding.ItemDateMonthBinding
import com.smparkworld.parkdatetimepicker.model.DayData
import com.smparkworld.parkdatetimepicker.model.MonthData

internal typealias MonthItemEventHandler = (MonthData, DayData) -> Unit

internal class DateMonthAdapter(
    private val itemEventHandler: MonthItemEventHandler
) : ListAdapter<MonthData, DateMonthAdapter.MonthViewHolder>(MonthData.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder =
        MonthViewHolder(
            ItemDateMonthBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            itemEventHandler
        )

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MonthViewHolder(
        private val binding: ItemDateMonthBinding,
        private val itemEventHandler: MonthItemEventHandler
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MonthData) {
            if (binding.container.adapter == null) {
                binding.container.adapter = DateDayAdapter { dayData ->
                    itemEventHandler.invoke(item, dayData)
                }
            }
            (binding.container.adapter as? DateDayAdapter)?.submitList(item.days)
        }
    }
}