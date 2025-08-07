import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.Helpers.SharedPrefHelper
import com.example.wavesoffood.Models.FoodItemModel
import com.example.wavesoffood.Models.OrderModel
import com.example.wavesoffood.Models.ResModel
import com.example.wavesoffood.R
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class RunningOrdersAdepter(var context: Context,var orderList: List<OrderModel>): RecyclerView.Adapter<RunningOrdersAdepter.RunningOrdersViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RunningOrdersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_layout, parent, false)
        return RunningOrdersViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RunningOrdersViewHolder,
        position: Int
    ) {
        var order = orderList[position]
        var db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        var foodItemRef = db.getReference("foodItem").child(order.foodId.toString())
        var dbOrderRef = db.getReference("orders")
        var dbResRef = db.getReference("res")

        var resTotalOrders : Int? = null

        var resTotalRunningOrders : Int? = null
        var sharedPrefHelper = SharedPrefHelper(context)
        var resId = sharedPrefHelper.getResId()


        holder.tvOrderId.text = order.id

        foodItemRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("mytest","t2")

                if (snapshot.exists()){
                    Log.d("mytest","t3")

                    var foodItemModel  = snapshot.getValue(FoodItemModel::class.java)
                    holder.tvFoodLable.text = foodItemModel?.foodCat
                   holder.tvFoodName.text = foodItemModel?.foodName
                   holder.tvFoodPrice.text = foodItemModel?.foodPrice.toString()
                   Glide.with(context).load(foodItemModel?.foodImg).placeholder(R.drawable.img_sample).into(holder.ivFoodImg)
               }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        //populate order numbers
        dbResRef.child(resId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var resMode = snapshot.getValue(ResModel::class.java)

                    resTotalRunningOrders = resMode?.resTotalRunningOrders
                     resTotalOrders = resMode?.resTotalOrders
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //click listners
        holder.btnDone.setOnClickListener {
            var id = holder.tvOrderId.text.toString()

            resTotalRunningOrders = resTotalRunningOrders!! - 1



            dbResRef.child(resId).child("resTotalRunningOrders").setValue(resTotalRunningOrders)
            dbOrderRef.child(id).child("orderStatus").setValue("finished")

        }
        holder.btnCancel.setOnClickListener {
            var id = holder.tvOrderId.text.toString()
            resTotalRunningOrders = resTotalRunningOrders!! - 1

            dbResRef.child(resId).child("resTotalRunningOrders").setValue(resTotalRunningOrders)
            dbOrderRef.child(id).child("orderStatus").setValue("Cancel")


        }



    }

    override fun getItemCount(): Int {
       return orderList.size
    }

    class RunningOrdersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tvFoodLable : TextView = itemView.findViewById(R.id.tvFoodLable)
        var tvFoodName : TextView = itemView.findViewById(R.id.tvFoodName)
        var ivFoodImg : ImageView = itemView.findViewById(R.id.ivFoodImgPreview)
        var tvOrderId: TextView = itemView.findViewById(R.id.tvOrderId)
        var tvFoodPrice: TextView = itemView.findViewById(R.id.tvFoodPrice)
        var btnDone: Button = itemView.findViewById(R.id.btnDone)
        var btnCancel : Button = itemView.findViewById(R.id.btnCancel)


    }
}