package com.ersurajrajput.wavesoffood.adapters.res

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.LayoutOrderBinding
import com.ersurajrajput.wavesoffood.models.OrderModel

class PendingOrderAdapter(var context: Context,var orderList: ArrayList<OrderModel>): RecyclerView.Adapter<PendingOrderAdapter.PendingOrdersViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingOrdersViewHolder {
        val binding = LayoutOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrdersViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PendingOrdersViewHolder,
        position: Int
    ) {
        holder.btnAction.setText("Dispach")
        holder.tvFoodName.text = orderList[position].foodName
        holder.tvOrderQuantity.text = orderList[position].orderQuantity.toString()
        holder.tvTotalPrice.text = orderList[position].totalPrice.toString()
        Glide.with(context).load(orderList[position].foodImg).placeholder(R.drawable.ic_launcher_background).into(holder.ivFoodImg)


        holder.btnAction.setOnClickListener {
            orderList.removeAt(holder.bindingAdapterPosition)
            notifyItemRemoved(holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class PendingOrdersViewHolder(binding: LayoutOrderBinding): RecyclerView.ViewHolder(binding.root){
        var ivFoodImg = binding.ivFoodImg
        var tvFoodName = binding.tvFoodName
        var tvOrderQuantity = binding.tvOrderQuantity
        var tvTotalPrice = binding.tvTotalPrice
        var btnAction = binding.btnAction

    }
}