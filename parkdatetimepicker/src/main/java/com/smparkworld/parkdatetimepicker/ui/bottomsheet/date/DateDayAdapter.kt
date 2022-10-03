package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.smparkworld.parkdatetimepicker.databinding.ItemDateDayBinding
import com.smparkworld.parkdatetimepicker.model.DayData

internal typealias DayItemEventHandler = (DayData) -> Unit

internal class DateDayAdapter(
    private val itemEventHandler: DayItemEventHandler
) : ListAdapter<DayData, DateDayAdapter.DayViewHolder>(DayData.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder =
        DayViewHolder(
            ItemDateDayBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            itemEventHandler
        )

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DayViewHolder(
        private val binding: ItemDateDayBinding,
        private val itemEventHandler: DayItemEventHandler
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DayData) {
            val isEmptyDay = DayData.isEmptyDay(item)
            if (!isEmptyDay) {
                binding.day.text = item.day.toString()
                binding.day.isSelected = item.isSelected
                binding.day.setOnClickListener {
                    adapterPosition.takeIf { it != NO_POSITION }?.let {
                        itemEventHandler.invoke(item)
                    }
                }
            }
            binding.root.isInvisible = isEmptyDay
        }
    }
}