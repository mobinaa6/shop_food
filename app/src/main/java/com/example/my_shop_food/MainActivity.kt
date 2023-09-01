package com.example.my_shop_food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_shop_food.databinding.ActivityMainBinding
import com.example.my_shop_food.databinding.DialogAddNewItemBinding
import com.example.my_shop_food.databinding.DialogUpdateItemBinding
import com.example.my_shop_food.databinding.DialogeDeleteFoodBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(),FoodAdapter.FoodEvent {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter:FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodList = arrayListOf(
            Food( "Hamburger" , "15" , "3" , "Isfahan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg" ,  20 , 4.5f ) ,
            Food( "Grilled fish" , "20" , "2.1" , "Tehran, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg" ,  10 , 4f ) ,
            Food( "Lasania" , "40" , "1.4" , "Isfahan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg" ,  30 , 2f ) ,
            Food( "pizza" , "10" , "2.5" , "Zahedan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg" ,  80 , 1.5f ) ,
            Food( "Sushi" , "20" , "3.2" , "Mashhad, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg" ,  200 , 3f ) ,
            Food( "Roasted Fish" , "40" , "3.7" , "Jolfa, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg" ,  50 , 3.5f ) ,
            Food( "Fried chicken" , "70" , "3.5" , "NewYork, USA" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg" ,  70 , 2.5f ) ,
            Food( "Vegetable salad" , "12" , "3.6" , "Berlin, Germany" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg" ,  40 , 4.5f ) ,
            Food( "Grilled chicken" , "10" , "3.7" , "Beijing, China" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg" ,  15 , 5f ) ,
            Food( "Baryooni" , "16" , "10" , "Ilam, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg" ,  28 , 4.5f ) ,
            Food( "Ghorme Sabzi" , "11.5" , "7.5" , "Karaj, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg" ,  27 , 5f ) ,
            Food( "Rice" , "12.5" , "2.4" , "Shiraz, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg" ,  35 , 2.5f ) ,
        )
       myAdapter=FoodAdapter(foodList.clone() as ArrayList<Food>,this)
        binding.recyclerMain.adapter=myAdapter

        binding.recyclerMain.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        binding.edtSearxhMain.addTextChangedListener { value->
            if(binding.edtSearxhMain.text.isNotEmpty()){
              val foodClone= foodList.clone() as ArrayList<Food>
             var searchResultFood=   foodClone.filter {
                    it.txtsubject.lowercase().contains(value.toString().lowercase())
                }
                myAdapter.setData(searchResultFood as ArrayList)


            }else{
                myAdapter.setData(foodList.clone() as ArrayList<Food>)
            }


        }

        binding.btnAddNewFood.setOnClickListener {
            var dialogbinding=DialogAddNewItemBinding.inflate(layoutInflater)
            var dialog=AlertDialog.Builder(this).create()

            dialog.setView(dialogbinding.root)


            dialog.show()
            dialogbinding.dialogBtnDone.setOnClickListener {
                if (dialogbinding.dialogEdtFoodName.editText!!.length()>0 &&
                    dialogbinding.dialogEdtFoodCity.editText!!.length()>0 &&
                    dialogbinding.dialogEdtFoodPrice.editText!!.length()>0 &&
                    dialogbinding.dialogEdtFoodDistance.editText!!.length()>0
                        ){
                    var txtNameFood=dialogbinding.dialogEdtFoodName.editText?.text.toString()
                    var txtnameCity=dialogbinding.dialogEdtFoodCity.editText?.text .toString()
                    var txtPrice=dialogbinding.dialogEdtFoodPrice.editText?.text .toString()
                    var txtDistance=dialogbinding.dialogEdtFoodDistance.editText?.text .toString()
                    var RatingNumber:Int=(0..150).random()
                    var min=0f
                    var max=5f
                    var RatingBarStarFlote=min+Random.nextFloat()*(max-min)
                    var RatingBarStarDouble=Random.nextDouble(0.0,5.1)
                    var randomForUlr=(0..foodList.size).random()
                    var UrlImage=foodList[randomForUlr].urlImgae
                    var newFood=Food(txtNameFood,txtPrice,txtDistance,txtnameCity,UrlImage,RatingNumber,RatingBarStarDouble.toFloat())
                    myAdapter.addFood(newFood)
                    dialog.dismiss()
                    binding.recyclerMain.scrollToPosition(0)


                }else{

                    Toast.makeText(this, "لطفا همه مقادیر را وارد کنید", Toast.LENGTH_SHORT).show()
                }

            }

        }





    }

    override fun onFoodClick(food:Food,position: Int) {
        var dialog=AlertDialog.Builder(this).create()
        var dialogUpdate=DialogUpdateItemBinding.inflate(layoutInflater)

        dialog.setView(dialogUpdate.root)
        dialog.show()
        dialogUpdate.dialogUpdateEdtFoodCity.editText!!.setText(food.txtCity)
        dialogUpdate.dialogUpdateEdtFoodPrice.editText!!.setText(food.txtPrice)
        dialogUpdate.dialogUpdateEdtFoodName.editText!!.setText(food.txtsubject)
        dialogUpdate.dialogUpdateEdtFoodDistance.editText!!.setText(food.txtDistance)

        dialogUpdate.dialogUpdateBtnCancle.setOnClickListener {
            dialog.dismiss()
        }
        dialogUpdate.dialogUpdadeBtnDone.setOnClickListener {
            //udate item:

            if (dialogUpdate.dialogUpdateEdtFoodName.editText!!.length()>0 &&
                dialogUpdate.dialogUpdateEdtFoodCity.editText!!.length()>0 &&
                dialogUpdate.dialogUpdateEdtFoodPrice.editText!!.length()>0 &&
                dialogUpdate.dialogUpdateEdtFoodDistance.editText!!.length()>0
            ){
                var txtNameFood=dialogUpdate.dialogUpdateEdtFoodName.editText?.text.toString()
                var txtnameCity=dialogUpdate.dialogUpdateEdtFoodCity.editText?.text .toString()
                var txtPrice=dialogUpdate.dialogUpdateEdtFoodPrice.editText?.text .toString()
                var txtDistance=dialogUpdate.dialogUpdateEdtFoodDistance.editText?.text .toString()
                var newFood=Food(
                    txtNameFood,txtPrice,txtDistance,txtnameCity,food.urlImgae.toString(),food.numOfRating,food.rating
                )
                myAdapter.upddateFood(newFood,position)
                dialog.dismiss()
            }else{
                Toast.makeText(this, "لطفا همه مقادیر را پر کنید :)", Toast.LENGTH_SHORT).show()
            }

        }




    }

    override fun onFoodLongClick(oldFood: Food, position: Int) {
        //delete Item:
        var dialog=AlertDialog.Builder(this).create()
        var dialogDeleteBinding = DialogeDeleteFoodBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.show()
        dialogDeleteBinding.dialogBtnDeleteCancle.setOnClickListener {
            dialog.dismiss()
        }
        dialogDeleteBinding.dialogBtnDone.setOnClickListener {
            myAdapter.RemoveFood(oldFood,position)
            dialog.dismiss()
        }


    }



}





