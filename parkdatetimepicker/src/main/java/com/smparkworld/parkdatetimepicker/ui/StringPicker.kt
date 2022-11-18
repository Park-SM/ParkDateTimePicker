package com.smparkworld.parkdatetimepicker.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.parkdatetimepicker.databinding.PdtpItemStringPickerBinding
import com.smparkworld.parkdatetimepicker.databinding.PdtpViewStringPickerBinding

typealias OnItemSelectedListener = (String) -> Unit

@Deprecated("not yet fully developed.")
internal class StringPicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var isCycleScroll: Boolean = false
    private var textSize: Float? = null
    private var textColor: Int? = null
    private var onItemSelectedListener: OnItemSelectedListener? = null

    private val snapHelper = LinearSnapHelper()
    private val binding = PdtpViewStringPickerBinding.inflate(
        LayoutInflater.from(context), this, true
    )
    private var currentPosition = RecyclerView.NO_POSITION

    init {
        initLayoutParams()
        initRecyclerView()
    }

    private fun initLayoutParams() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    private fun initRecyclerView() {
        snapHelper.attachToRecyclerView(binding.container)
        binding.container.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val view = snapHelper.findSnapView(recyclerView.layoutManager) ?: return
                val position = recyclerView.layoutManager?.getPosition(view) ?: return

                if (currentPosition != position) {
                    currentPosition = position
                    getAdapter().getItemByPosition(position)?.let { item ->
                        onItemSelectedListener?.invoke(item)
                    }
                }
            }
        })
    }

    private fun getAdapter(): StringPickerAdapter {
        var adapter = binding.container.adapter as? StringPickerAdapter
        if (adapter == null) {
            adapter = if (isCycleScroll) {
                StringPickerCycleAdapterImpl()
            } else {
                StringPickerAdapterImpl()
            }
            adapter.setItemTextSize(textSize)
            adapter.setItemTextColor(textColor)

            binding.container.adapter = adapter
        }
        return adapter
    }

    fun setCycleScrolling(isCycleScroll: Boolean) {
        this.isCycleScroll = isCycleScroll
    }

    fun setOnSelectListener(listener: OnItemSelectedListener) {
        onItemSelectedListener = listener
    }

    fun submitList(items: List<String>) {
        getAdapter().submitList(items)
        if (isCycleScroll) {
            binding.container.scrollToPosition((Int.MAX_VALUE / 2) - ((Int.MAX_VALUE / 2) % items.size))
        }
    }

    private abstract class StringPickerAdapter : RecyclerView.Adapter<StringPickerViewHolder>() {

        abstract fun setItemTextSize(size: Float?)

        abstract fun setItemTextColor(@ColorInt color: Int?)

        abstract fun getItemByPosition(position: Int): String?

        abstract fun submitList(items: List<String>)
    }

    private class StringPickerViewHolder(
        private val binding: PdtpItemStringPickerBinding,
        private val itemSize: Float?,
        @ColorInt private val itemColor: Int?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.itemText.text = item

            itemSize?.let { size ->
                binding.itemText.textSize = size
            }
            itemColor?.let { color ->
                binding.itemText.setTextColor(color)
            }
        }
    }

    private open class StringPickerAdapterImpl : StringPickerAdapter() {

        private val items = mutableListOf<String>()
        private var textSize: Float? = null
        private var textColor: Int? = null

        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringPickerViewHolder {
            return StringPickerViewHolder(
                PdtpItemStringPickerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                textSize, textColor
            )
        }

        override fun onBindViewHolder(holder: StringPickerViewHolder, position: Int) {
            items.getOrNull(position)?.let {
                holder.bind(it)
            }
        }

        override fun setItemTextSize(size: Float?) {
            textSize = size
        }

        override fun setItemTextColor(color: Int?) {
            textColor = color
        }

        override fun getItemByPosition(position: Int): String? = items.getOrNull(position)

        override fun submitList(items: List<String>) {
            this.items.clear()
            this.items.addAll(items)
            notifyDataSetChanged()
        }
    }

    private class StringPickerCycleAdapterImpl : StringPickerAdapterImpl() {

        private var originalItemCount = 0

        override fun submitList(items: List<String>) {
            super.submitList(items)
            originalItemCount = items.size
        }

        override fun getItemCount(): Int = Int.MAX_VALUE

        override fun onBindViewHolder(holder: StringPickerViewHolder, position: Int) {
            super.onBindViewHolder(holder, position % originalItemCount)
        }

        override fun getItemByPosition(position: Int): String? {
            return super.getItemByPosition(position % originalItemCount)
        }
    }
}