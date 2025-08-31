package com.ersurajrajput.wavesoffood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.LayoutFoodItemBinding
import com.ersurajrajput.wavesoffood.models.FoodItemModel

class PopularFoodAdapter(var foodList: ArrayList<FoodItemModel>,val context: Context): RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularFoodViewHolder {
        val binding = LayoutFoodItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PopularFoodViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PopularFoodViewHolder,
        position: Int
    ) {
        holder.foodName.text = foodList[position].foodName
        holder.foodPrice.text = foodList[position].foodPrice.toString()
        Glide.with(context).load(foodList[position].foodImg).placeholder(R.drawable.ic_launcher_background).into(holder.foodImg)


    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class PopularFoodViewHolder(val binding: LayoutFoodItemBinding): RecyclerView.ViewHolder(binding.root){
        val foodName = binding.tvFoodName
        val foodPrice = binding.tvFoodPrice
        val foodImg = binding.ivFoodImg

    }
}