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

class CatAdapter(val requiredContext : Context, private val catList:ArrayList<CategoriesModel>, private val onItemClick: (CategoriesModel) -> Unit): RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    class CatViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val catName : TextView = itemView.findViewById(R.id.cat_name)
        val catImg : ImageView = itemView.findViewById(R.id.cat_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.cat_item,parent,false)
        return  CatViewHolder(view)
    }

    override fun getItemCount(): Int {

        return catList.size
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.catName.text = catList[position].cat_name
        Glide.with(holder.itemView.context).load(catList[position].cat_img).placeholder(R.drawable.sample_img).into(holder.catImg)

        val currentItem = catList[position]
        holder.itemView.setOnClickListener {
            onItemClick(currentItem) // returns the clicked item
        }
    }

}
