package com.example.wavesoffood.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.Adapters.CatAdapter.CatViewHolder
import com.example.wavesoffood.R
import com.example.wavesoffood.model.CategoriesModel
import com.example.wavesoffood.model.RecentKeywordsModel

class RecentKeywordsAdepter(val requiredContext : Context, private val keywordList:ArrayList<RecentKeywordsModel>, private val onItemClick: (RecentKeywordsModel) -> Unit): RecyclerView.Adapter<RecentKeywordsAdepter.recentKeywordsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): recentKeywordsViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.recentkeywords_card,parent,false)

        return recentKeywordsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: recentKeywordsViewHolder,
        position: Int
    ) {
        holder.name.text = keywordList[position].name
        val currentItem = keywordList[position]
        holder.itemView.setOnClickListener {

            onItemClick(currentItem) // returns the clicked item

        }
    }

    override fun getItemCount(): Int {
       return keywordList.size
    }

    class recentKeywordsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.keyword_name)
    }

}