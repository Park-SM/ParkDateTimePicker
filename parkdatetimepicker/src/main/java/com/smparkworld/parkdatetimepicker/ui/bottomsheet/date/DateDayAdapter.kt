package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.databinding.ItemDateDayBinding
import com.smparkworld.parkdatetimepicker.ui.applier.ColorArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.DayUiModel

internal typealias DayItemEventHandler = (DayUiModel) -> Unit

internal class DateDayAdapter(
    private val itemEventHandler: DayItemEventHandler
) : RecyclerView.Adapter<DateDayAdapter.DayViewHolder>() {

    private val items = mutableListOf<DayUiModel>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder =
        DayViewHolder(
            ItemDateDayBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            itemEventHandler
        )

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        items.getOrNull(position)?.let { item ->
            item.position = position
            holder.bind(item)
        }
    }

    fun submitList(items: List<DayUiModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class DayViewHolder(
        private val binding: ItemDateDayBinding,
        private val itemEventHandler: DayItemEventHandler
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiModel: DayUiModel) {
            if (!uiModel.isEmptyDay) {
                binding.day.text = uiModel.day
                binding.day.isSelected = uiModel.isSelected
                binding.container.setOnClickListener {
                    itemEventHandler.invoke(uiModel)
                }
            }
            binding.root.isInvisible = uiModel.isEmptyDay
            setSelection(uiModel.isSelected)
        }

        private fun setSelection(isSelected: Boolean) {
            if (isSelected) {
                binding.day.background = ContextCompat.getDrawable(binding.root.context, R.drawable.shape_date_day_selected)
                binding.day.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                ColorArgumentApplier.applyPrimaryColor(binding.day, ColorArgumentApplier.Options.BACKGROUND_TINT)
            } else {
                binding.day.background = null
                binding.day.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                ColorArgumentApplier.applyPrimaryColor(binding.day)
            }
        }
    }
}