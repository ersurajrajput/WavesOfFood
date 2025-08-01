package com.example.wavesoffood.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.Adapters.ResAdapter.ResViewHolder
import com.example.wavesoffood.R
import com.example.wavesoffood.model.RestaurantModel

class SuggestedRestaurantsAdapter(requiredContext: Context,private val resList: ArrayList<RestaurantModel>,private val onItemClick: (RestaurantModel) -> Unit): RecyclerView.Adapter<SuggestedRestaurantsAdapter.SuggestedRestaurantsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SuggestedRestaurantsViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.suggested_restaurants,parent,false)
        return  SuggestedRestaurantsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SuggestedRestaurantsViewHolder,
        position: Int
    ) {
        holder.resName.text = resList[position].res_name
        holder.resRating.text = resList[position].res_rating
        Glide.with(holder.itemView.context).load(resList[position].res_img).placeholder(R.drawable.sample_img).into(holder.resImg)
        val currentItem = resList[position]
        holder.itemView.setOnClickListener { onItemClick(currentItem)  }
    }

    override fun getItemCount(): Int {
        return resList.size
    }

    class SuggestedRestaurantsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val resName :TextView = itemView.findViewById(R.id.resName)
        val resImg : ImageView = itemView.findViewById(R.id.resImg)
        val resRating : TextView = itemView.findViewById(R.id.resRating)


    }
}