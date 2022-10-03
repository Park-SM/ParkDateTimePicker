package com.smparkworld.parkdatetimepicker.model

import androidx.recyclerview.widget.DiffUtil

internal data class MonthData(
    val id: Int,
    val year: Int,
    val month: Int,
    val days: List<DayData>
) {

    companion object {

        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<MonthData>() {
            override fun areItemsTheSame(oldItem: MonthData, newItem: MonthData): Boolean {
                return (oldItem.id == newItem.id)
            }
            override fun areContentsTheSame(oldItem: MonthData, newItem: MonthData): Boolean {
                return (oldItem == newItem)
            }
        }
    }
}