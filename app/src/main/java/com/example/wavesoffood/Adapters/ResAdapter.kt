package com.example.wavesoffood.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.wavesoffood.R
import com.example.wavesoffood.model.CategoriesModel
import com.example.wavesoffood.model.RestaurantModel
import org.w3c.dom.Text

class ResAdapter(requiredContext:Context,private val resList: ArrayList<RestaurantModel>,private val onItemClick: (RestaurantModel) -> Unit): RecyclerView.Adapter<ResAdapter.ResViewHolder>() {
    class ResViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val resName :TextView = itemView.findViewById(R.id.re_name)
        val resImg : ImageView = itemView.findViewById(R.id.res_image)
        val resMenu : TextView = itemView.findViewById(R.id.res_menu)
        val resRating : TextView = itemView.findViewById(R.id.res_rating)
        val resDelivery : TextView = itemView.findViewById(R.id.res_delivery_charges)
        val resTime : TextView = itemView.findViewById(R.id.res_time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.restorent_card,parent,false)
        return  ResViewHolder(view)
    }

    override fun getItemCount(): Int {
       return resList.size
    }

    override fun onBindViewHolder(holder: ResViewHolder, position: Int) {
        holder.resName.text = resList[position].res_name
        Glide.with(holder.itemView.context).load(resList[position].res_img).placeholder(R.drawable.sample_img).into(holder.resImg)
        holder.resMenu.text = resList[position].res_menu
        holder.resRating.text = resList[position].res_rating
        holder.resDelivery.text = resList[position].res_delivery_charges
        holder.resTime.text = resList[position].res_time

        val currentItem = resList[position]
        holder.itemView.setOnClickListener { onItemClick(currentItem)  }


    }
}