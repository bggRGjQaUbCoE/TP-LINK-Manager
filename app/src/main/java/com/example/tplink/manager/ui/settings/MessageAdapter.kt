package com.example.tplink.manager.ui.settings

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tplink.manager.logic.model.RequestResponse
import com.example.tplink.manager.util.dp
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
class MessageAdapter :
    ListAdapter<RequestResponse.AllPushMsg, MessageAdapter.ViewHolder>(MessageDiffUtil()) {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = TextView(parent.context).apply {
            setPadding(10.dp, 5.dp, 10.dp, 0)
        }
        return ViewHolder(textView)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = currentList[position].attribute
        val date = Date((data?.time ?: 0) * 1000)
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
        val content = URLDecoder.decode(data?.content, data?.encodeType ?: "UTF-8")
        holder.textView.text = "$time $content"
    }
}

class MessageDiffUtil : DiffUtil.ItemCallback<RequestResponse.AllPushMsg>() {
    override fun areItemsTheSame(
        oldItem: RequestResponse.AllPushMsg,
        newItem: RequestResponse.AllPushMsg
    ): Boolean {
        return oldItem.attribute?.msgId == newItem.attribute?.msgId
    }

    override fun areContentsTheSame(
        oldItem: RequestResponse.AllPushMsg,
        newItem: RequestResponse.AllPushMsg
    ): Boolean {
        return oldItem.attribute?.msgId == newItem.attribute?.msgId
    }
}