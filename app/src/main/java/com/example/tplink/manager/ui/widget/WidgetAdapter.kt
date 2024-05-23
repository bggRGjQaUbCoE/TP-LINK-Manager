package com.example.tplink.manager.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tplink.manager.databinding.ItemWidgetBinding
import com.example.tplink.manager.logic.model.RequestResponse
import java.net.URLDecoder

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
class WidgetAdapter(
    private val onClick: () -> Unit
) :
    ListAdapter<RequestResponse.MarketPlugin, WidgetAdapter.ViewHolder>(WidgetDiffUtil()) {

    class ViewHolder(val binding: ItemWidgetBinding, onClick: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClick()
            }
        }

        fun bind(data: RequestResponse.MarketPlugin) {
            binding.title.text = URLDecoder.decode(data.name, "UTF-8")
            binding.logo.load(URLDecoder.decode(data.appIconUrl, "UTF-8")) {
                crossfade(200)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWidgetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}

class WidgetDiffUtil : DiffUtil.ItemCallback<RequestResponse.MarketPlugin>() {
    override fun areItemsTheSame(
        oldItem: RequestResponse.MarketPlugin,
        newItem: RequestResponse.MarketPlugin
    ): Boolean {
        return oldItem.pluginId == newItem.pluginId
    }

    override fun areContentsTheSame(
        oldItem: RequestResponse.MarketPlugin,
        newItem: RequestResponse.MarketPlugin
    ): Boolean {
        return oldItem.pluginId == newItem.pluginId
    }
}