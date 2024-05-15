package ru.hse.smart_control.ui.fragments.control

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.hse.smart_control.R
import ru.hse.smart_control.databinding.WidgetItemBinding

interface WidgetActionListener {


    fun deleteWidgetById(id: String)
    fun getWidgetId(id: String)
}

class WidgetListAdapter(
    private val actionListener: WidgetActionListener,
):
    ListAdapter<WidgetModel, WidgetListAdapter.WidgetListViewHolder>(callback),
    View.OnClickListener
{

    class WidgetListViewHolder(val binding: WidgetItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetListViewHolder {
        val binding = WidgetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)
        return WidgetListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WidgetListViewHolder, position: Int) {
        val widgetItem = getItem(position)
        holder.binding.tvWidgetType.text = widgetItem.widgetType
        holder.binding.tvWidgetId.text = widgetItem.id.toString()
        holder.itemView.tag = widgetItem
        holder.binding.btnDelete.tag = widgetItem
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("key" to widgetItem.id)
            actionListener.getWidgetId(widgetItem.id.toString())
        }
    }

    override fun onClick(v: View) {
        val widget = v.tag as WidgetModel
        when (v.id) {
            R.id.btnDelete -> {
                actionListener.deleteWidgetById(widget.id.toString())
            }
        }
    }
}

val callback = object: DiffUtil.ItemCallback<WidgetModel>() {
    override fun areItemsTheSame(oldItem: WidgetModel, newItem: WidgetModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WidgetModel, newItem: WidgetModel): Boolean {
        return oldItem == newItem
    }
}