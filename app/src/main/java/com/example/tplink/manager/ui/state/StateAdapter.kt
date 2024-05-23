package com.example.tplink.manager.ui.state

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tplink.manager.databinding.ItemStateBinding
import com.example.tplink.manager.logic.model.RequestModel
import com.example.tplink.manager.logic.model.RequestResponse
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.net.URLDecoder

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
class StateAdapter(
    private val block: (Int, RequestModel) -> Unit
) :
    ListAdapter<RequestResponse.HostInfoDetail, StateAdapter.ViewHolder>(StateDiffUtil()) {

    class ViewHolder(val binding: ItemStateBinding, block: (Int, RequestModel) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        var blocked = "0"

        init {
            itemView.setOnClickListener {
                MaterialAlertDialogBuilder(itemView.context).apply {
                    setTitle(binding.device.text.toString())
                    setMessage(binding.mac.text.toString())
                    setPositiveButton(if (blocked == "1") "UnBlock" else "Block") { _, _ ->
                        block(
                            bindingAdapterPosition,
                            RequestModel(
                                method = "do",
                                hostsInfo = RequestModel.HostsInfo(
                                    table = null,
                                    setBlockFlag = RequestModel.SetBlockFlag(
                                        mac = binding.mac.text.toString().lowercase(),
                                        isBlocked = if (blocked == "1") 0 else 1,
                                        name = binding.device.text.toString(),
                                        upLimit = 0,
                                        downLimit = 0
                                    )
                                )
                            )
                        )
                    }
                    show()
                }
            }
        }

        fun bind(data: RequestResponse.HostInfoDetail) {
            blocked = data.blocked ?: "0"
            binding.device.text = URLDecoder.decode(data.hostname, "UTF-8")
            binding.mac.text = data.mac?.uppercase()
            binding.wifiMode.text = if (data.wifiMode == "1") "5G Wi-Fi"
            else "2.4G Wi-Fi"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, block)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        setDeviceColor(
            holder.itemView.context,
            currentList[position].blocked ?: "0",
            holder.binding.device
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.blocked = currentList[position].blocked ?: "0"
            setDeviceColor(
                holder.itemView.context,
                holder.blocked,
                holder.binding.device
            )
        }
    }

    private fun setDeviceColor(context: Context, blocked: String, textView: TextView) {
        val color = MaterialColors.getColor(
            context,
            if (blocked == "1")
                com.google.android.material.R.attr.colorError
            else
                com.google.android.material.R.attr.colorControlNormal, 0
        )
        textView.setTextColor(color)
    }

}

class StateDiffUtil : DiffUtil.ItemCallback<RequestResponse.HostInfoDetail>() {
    override fun areItemsTheSame(
        oldItem: RequestResponse.HostInfoDetail,
        newItem: RequestResponse.HostInfoDetail
    ): Boolean {
        return oldItem.mac == newItem.mac
    }

    override fun areContentsTheSame(
        oldItem: RequestResponse.HostInfoDetail,
        newItem: RequestResponse.HostInfoDetail
    ): Boolean {
        return oldItem.blocked == newItem.blocked
    }

    override fun getChangePayload(
        oldItem: RequestResponse.HostInfoDetail,
        newItem: RequestResponse.HostInfoDetail
    ): Any? {
        return if (oldItem.blocked != newItem.blocked) true
        else null
    }
}