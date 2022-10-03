package com.smparkworld.parkdatetimepicker.model

import androidx.recyclerview.widget.DiffUtil

internal data class DayData(
    val id: Int,
    val monthId: Int,
    val day: Int,
    var isSelected: Boolean,
    val label: String?
) {

    companion object {

        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<DayData>() {
            override fun areItemsTheSame(oldItem: DayData, newItem: DayData): Boolean {
                return (oldItem.id == newItem.id)
            }
            override fun areContentsTheSame(oldItem: DayData, newItem: DayData): Boolean {
                return (oldItem == newItem)
            }
        }

        val EMPTY_DAY = DayData(-1, -1, -1, false, null)

        @JvmStatic
        fun isEmptyDay(dayData: DayData): Boolean = dayData == EMPTY_DAY
    }
}
