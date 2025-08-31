package com.ersurajrajput.wavesoffood.adapters.res

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.LayoutOrderBinding
import com.ersurajrajput.wavesoffood.models.FoodItemModel
import com.ersurajrajput.wavesoffood.models.OrderModel


class ReqOrdersAdapter(var context: Context,var orderList: ArrayList<OrderModel>):
    RecyclerView.Adapter<ReqOrdersAdapter.ReqOrdersViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReqOrdersViewHolder {
        val binding = LayoutOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReqOrdersViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReqOrdersViewHolder,
        position: Int
    ) {
        holder.btnAction.setText("Accept")
        holder.foodName.text = orderList[position].foodName
        holder.totalPrice.text = orderList[position].totalPrice.toString()
        holder.orderQuantity.text = orderList[position].orderQuantity.toString()
        Glide.with(context).load(orderList[position].foodImg).placeholder(R.drawable.ic_launcher_background).into(holder.foodImg)


        holder.btnAction.setOnClickListener {

            orderList.removeAt(holder.bindingAdapterPosition)
            notifyItemRemoved(holder.bindingAdapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class ReqOrdersViewHolder(binding: LayoutOrderBinding): RecyclerView.ViewHolder(binding.root){
        var foodName = binding.tvFoodName
        var foodImg = binding.ivFoodImg
        var totalPrice = binding.tvTotalPrice
        var orderQuantity = binding.tvOrderQuantity
        var btnAction = binding.btnAction

    }
}