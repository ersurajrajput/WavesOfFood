package com.ersurajrajput.wavesoffood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.LayoutNotificationBinding
import com.ersurajrajput.wavesoffood.models.NotificationModel

class NotificationAdapter(var context: Context,var notificationList: ArrayList<NotificationModel>): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationViewHolder {
        val binding = LayoutNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NotificationViewHolder,
        position: Int
    ) {
       holder.notificationTitle.text = notificationList[position].notificationTitle
        Glide.with(context).load(notificationList[position].notificationImg).placeholder(R.drawable.ic_launcher_background).into(holder.notificationImg)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    class NotificationViewHolder( binding: LayoutNotificationBinding): RecyclerView.ViewHolder(binding.root){
        var notificationTitle = binding.tvNotificationTitle
        var notificationImg = binding.ivNotificationImg

    }
}