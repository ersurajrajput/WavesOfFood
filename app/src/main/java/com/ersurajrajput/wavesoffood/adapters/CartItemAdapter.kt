package com.ersurajrajput.wavesoffood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.LayoutCartItemBinding
import com.ersurajrajput.wavesoffood.models.CartItemModel

class CartItemAdapter(var cartItemList: ArrayList<CartItemModel>,var context: Context): RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartItemViewHolder {
       val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartItemViewHolder,
        position: Int
    ) {
        holder.foodName.text = cartItemList[position].foodName
        holder.foodPrice.text = cartItemList[position].foodPrice.toString()
        holder.foodQuantity.text = cartItemList[position].foodQuantity.toString()
        Glide.with(context).load(cartItemList[position].foodImg).placeholder(R.drawable.ic_launcher_background).into(holder.foodImg)
        var tprice = holder.foodPrice.text.toString().toInt()


        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(context).setTitle("Do You Want to delete this item ?")
                .setPositiveButton("Yes"){ dialog, which ->
                    cartItemList.removeAt(holder.bindingAdapterPosition)
                    notifyItemRemoved(holder.bindingAdapterPosition)
                }
                .setNegativeButton("No"){dialog, which ->
                    dialog.dismiss()
                }.show()

        }

        holder.btnMinus.setOnClickListener {
            var c = holder.foodQuantity.text.toString().toInt()
            var price = holder.foodPrice.text.toString().toInt()
            if (c>1){
                c--
                price -= tprice
                holder.foodPrice.text = price.toString()
                holder.foodQuantity.text = c.toString()
            }

        }
        holder.btnPlus.setOnClickListener {
            var c = holder.foodQuantity.text.toString().toInt()
            var price = holder.foodPrice.text.toString().toInt()


            if (c<11){
                c++
                price += tprice
                holder.foodPrice.text = price.toString()
                holder.foodQuantity.text = c.toString()
            }

        }
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }

    class CartItemViewHolder(val binding: LayoutCartItemBinding): RecyclerView.ViewHolder(binding.root){
        var foodName = binding.tvFoodName
        var foodImg = binding.ivFoodImg
        var foodPrice = binding.tvFoodPrice
        var foodQuantity = binding.tvQuantity
        var btnMinus = binding.ivMinus
        var btnPlus = binding.ivPlus
        var btnDelete = binding.ivDelete

    }
}