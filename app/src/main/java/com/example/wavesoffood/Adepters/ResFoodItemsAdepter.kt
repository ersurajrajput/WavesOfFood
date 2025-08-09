package com.example.wavesoffood.Adepters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.Models.FoodItemModel
import com.example.wavesoffood.R

class ResFoodItemsAdepter(
    private val context: Context,
    private val foodList: MutableList<FoodItemModel>,
    private val onItemClick: (FoodItemModel,s: Int) -> Unit

): RecyclerView.Adapter<ResFoodItemsAdepter.FoodItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item_layout_big, parent, false)
        return FoodItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FoodItemViewHolder,
        position: Int
    ) {
        var id  = foodList[position].id
        holder.tvFoodname.text = foodList[position].foodName
        holder.tvFoodLable.text = foodList[position].foodCat
        holder.tvFoodPrice.text = foodList[position].foodPrice.toString()
        holder.tvFoodRating.text = foodList[position].foodRating.toString()
        holder.tvTotalNoOfReview.text = foodList[position].totalNoOfReviews.toString()
        Glide.with(context).load(foodList[position].foodImg).placeholder(R.drawable.img_sample).into(holder.ivFoodImg)

        val currentItem = foodList[position]
        holder.btnEdit.setOnClickListener {
            onItemClick(currentItem,0)
        }
        holder.btnDelete.setOnClickListener {
            onItemClick(currentItem,1)
        }


    }

    override fun getItemCount(): Int {
        return foodList.size
    }


    class FoodItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       var tvFoodname = itemView.findViewById<TextView>(R.id.tvFoodName)
        var tvFoodLable = itemView.findViewById<TextView>(R.id.tvFoodLabel)
        var tvFoodPrice = itemView.findViewById<TextView>(R.id.tvFoodPrice)
        var tvFoodRating = itemView.findViewById<TextView>(R.id.tvFoodRating)
        var tvTotalNoOfReview = itemView.findViewById<TextView>(R.id.tvTotalNoOfReview)
        var ivFoodImg = itemView.findViewById<ImageView>(R.id.ivFoodImgPreview)
        var btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
        var btnEdit = itemView.findViewById<ImageView>(R.id.btnEdit)
    }
}