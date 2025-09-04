package com.ersurajrajput.wavesoffood.adapters

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.LayoutOrder2Binding
import com.ersurajrajput.wavesoffood.models.OrderModel


class OrdersAdapter(var context: Context,var orderList: ArrayList<OrderModel>): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersViewHolder {
        val binding = LayoutOrder2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrdersViewHolder,
        position: Int
    ) {
        holder.foodName.text = orderList[position].foodName
        holder.foodQuantity.text = orderList[position].orderQuantity.toString()
        holder.totalPrice.text = orderList[position].totalPrice.toString()
        holder.status.text = orderList[position].orderStatus
        if (orderList[position].orderStatus.equals("req")){
            holder.statusLayout.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context,R.color.lightRed)
        }
        if (orderList[position].orderStatus.equals("running")){
            holder.statusLayout.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context,R.color.yello)
        }
        if (orderList[position].orderStatus.equals("delivered")){
            holder.statusLayout.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context,R.color.green)
        }
        if (orderList[position].orderStatus.equals("cancel")){
            holder.statusLayout.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context,R.color.red)
        }

        Glide.with(context).load(orderList[position].foodImg).placeholder(R.drawable.ic_launcher_background).into(holder.foodImg)

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrdersViewHolder(binding: LayoutOrder2Binding):RecyclerView.ViewHolder(binding.root){
        var foodName = binding.tvFoodName
        var foodImg = binding.ivFoodImg
        var foodQuantity = binding.tvOrderQuantity
        var totalPrice = binding.tvTotalPrice
        var status = binding.tvStatus
        var statusLayout = binding.statusLayout


    }
}