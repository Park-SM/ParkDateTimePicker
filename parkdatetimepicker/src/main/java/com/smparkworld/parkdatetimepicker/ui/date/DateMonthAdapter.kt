package com.smparkworld.parkdatetimepicker.ui.date

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.parkdatetimepicker.databinding.PdtpItemDateMonthBinding
import com.smparkworld.parkdatetimepicker.ui.date.model.DayUiModel
import com.smparkworld.parkdatetimepicker.ui.date.model.MonthUiModel

internal typealias MonthItemEventHandler = (MonthUiModel, DayUiModel) -> Unit

internal class DateMonthAdapter(
    private val itemEventHandler: MonthItemEventHandler
) : RecyclerView.Adapter<DateMonthAdapter.MonthViewHolder>() {

    private val items = mutableListOf<MonthUiModel>()
    private val cachedDayAdapterMap = mutableMapOf<Int, DateDayAdapter>()
    private var selectedDayUiModel: DayUiModel? = null
    private var isScrollMode: Boolean = false

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder =
        MonthViewHolder(
            PdtpItemDateMonthBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            cachedDayAdapterMap, { isScrollMode }, ::onClickDayItem
        )

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        items.getOrNull(position)?.let { item ->
            holder.bind(item)
        }
    }

    fun submitList(items: List<MonthUiModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setScrollMode(isScrollMode: Boolean) {
        this.isScrollMode = isScrollMode
        notifyDataSetChanged()
    }

    private fun onClickDayItem(dayUiModel: DayUiModel) {
        selectedDayUiModel?.let { oldDayUiModel ->
            oldDayUiModel.isSelected = false
            cachedDayAdapterMap[oldDayUiModel.monthId]?.notifyItemChanged(oldDayUiModel.position)
        }
        selectedDayUiModel = dayUiModel

        dayUiModel.isSelected = true
        cachedDayAdapterMap[dayUiModel.monthId]?.notifyItemChanged(dayUiModel.position)

        items.find { it.id == dayUiModel.monthId }?.let { monthUiModel ->
            itemEventHandler.invoke(monthUiModel, dayUiModel)
        }
    }

    class MonthViewHolder(
        private val binding: PdtpItemDateMonthBinding,
        private val cachedDayAdapterMap: MutableMap<Int, DateDayAdapter>,
        private val isScrollMode: () -> Boolean,
        private val itemEventHandler: (DayUiModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiModel: MonthUiModel) {
            binding.container.itemAnimator = null
            setScrollMode(isScrollMode())
            getAdapter(uiModel.id).submitList(uiModel.dayUiModels)
        }

        private fun getAdapter(monthId: Int): DateDayAdapter {
            val adapter = (binding.container.adapter as? DateDayAdapter) ?: DateDayAdapter { dayUiModel ->
                itemEventHandler.invoke(dayUiModel)
            }.also { newDayAdapter ->
                binding.container.adapter = newDayAdapter
            }

            cachedDayAdapterMap[monthId] = adapter
            return adapter
        }

        private fun setScrollMode(enableScrollMode: Boolean) {
            if (enableScrollMode) {
                binding.container.overScrollMode = View.OVER_SCROLL_ALWAYS
            } else {
                binding.container.overScrollMode = View.OVER_SCROLL_NEVER
            }
            binding.container.isVerticalScrollBarEnabled = enableScrollMode
            binding.container.isScrollbarFadingEnabled = !enableScrollMode
        }
    }
}