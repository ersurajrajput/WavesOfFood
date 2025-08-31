package com.ersurajrajput.wavesoffood.adapters.res

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.LayoutResFoodItemBinding
import com.ersurajrajput.wavesoffood.models.FoodItemModel
import com.ersurajrajput.wavesoffood.ui.res.ResAddFoodItemActivity

class ResAllFoodItemAdapter(var resFoodItemList: ArrayList<FoodItemModel>,var context: Context): RecyclerView.Adapter<ResAllFoodItemAdapter.ResAllFoodItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResAllFoodItemViewHolder {
        val binding = LayoutResFoodItemBinding.inflate(android.view.LayoutInflater.from(parent.context),parent,false)
        return ResAllFoodItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ResAllFoodItemViewHolder,
        position: Int
    ) {
       holder.foodName.text = resFoodItemList[position].foodName
        holder.resName.text = resFoodItemList[position].resName
        holder.foodPrice.text = resFoodItemList[position].foodPrice.toString()

        Glide.with(context).load(resFoodItemList[position].foodImg).placeholder(R.drawable.ic_launcher_background).into(holder.foodImg)

        holder.delete.setOnClickListener {

            AlertDialog.Builder(context).setTitle("Delete Item").setMessage("Are you sure to delete this item?").setPositiveButton("Yes"){dialog, which ->
                resFoodItemList.removeAt(holder.bindingAdapterPosition)
                notifyItemRemoved(holder.bindingAdapterPosition)
            }.setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }.show()

        }




        holder.edit.setOnClickListener {
            AlertDialog.Builder(context).setTitle("Edit Item").setMessage("Are you sure to Edit this item?").setPositiveButton("Yes"){dialog, which ->
                context.startActivity(Intent(context, ResAddFoodItemActivity::class.java))
            }.setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }.show()
        }


    }

    override fun getItemCount(): Int {
       return resFoodItemList.size
    }

    class ResAllFoodItemViewHolder( binding: LayoutResFoodItemBinding): RecyclerView.ViewHolder(binding.root){
        var foodName = binding.tvFoodName
        var foodImg = binding.ivFoodImg
        var foodPrice = binding.tvFoodPrice

        var resName = binding.tvResName
        var delete = binding.ivDelete
        var edit = binding.ivEdit

    }
}