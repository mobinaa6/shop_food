package com.example.my_shop_food

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FoodAdapter(private val  data:ArrayList<Food>,private val foodEvent: FoodEvent) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View,private val context:Context) : RecyclerView.ViewHolder(itemView) {
       val imgMain = itemView.findViewById<ImageView>(R.id.item_image_main)
       val txtSubject = itemView.findViewById<TextView>(R.id.item_txt_subject)
       val txtCity = itemView.findViewById<TextView>(R.id.item_txt_city)
       val txtPrice = itemView.findViewById<TextView>(R.id.item_txt_price)
       val txtDistance = itemView.findViewById<TextView>(R.id.item_txt_distance)
       val ratingBar = itemView.findViewById<RatingBar>(R.id.item_rating_main)
        val numOfRating = itemView.findViewById<TextView>(R.id.item_txt_numOfRating)

        fun bindData(index:Int){
            txtSubject.text=data[index].txtsubject
            txtCity.text=data[index].txtCity
            txtPrice.text="$ "+data[index].txtPrice+" vip"
            txtDistance.text=data[index].txtDistance+" miles from you"
            ratingBar.rating=data[index].rating
            numOfRating.text="("+ data[index].numOfRating.toString()+"Ratings)"
            Glide.with(context)
                .load(data[index].urlImgae)
                .into(imgMain)




            itemView.setOnClickListener {
                foodEvent.onFoodClick(data[adapterPosition],adapterPosition)
            }

        itemView.setOnLongClickListener {
            foodEvent.onFoodLongClick(data[adapterPosition],adapterPosition)
            true
        }





        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int,): FoodViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.item_food,parent,false)
        return  FoodViewHolder(view,parent.context)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {


        holder.bindData(position)



    }

    fun addFood(newfood:Food){

        data.add(0,newfood)
        notifyItemInserted(0)
    }

    fun RemoveFood(oldFood:Food,oldposition:Int){

        data.remove(oldFood)
        notifyItemRemoved(oldposition)
    }


    fun upddateFood(food: Food,position: Int){
        //update item from list:
        data[position] = food
        notifyItemChanged(position)

    }

    fun setData(newList:ArrayList<Food>){
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    interface FoodEvent{
        fun onFoodClick(food:Food,position: Int)
        fun onFoodLongClick(data:Food,position: Int)

    }


}
