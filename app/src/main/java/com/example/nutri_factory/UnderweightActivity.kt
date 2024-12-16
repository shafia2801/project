package com.example.nutri_factory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UnderweightActivity : AppCompatActivity() {

        private lateinit var storageReference: StorageReference

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_underweight)

            val scrollView: LinearLayout = findViewById(R.id.scrollViewContent)

            // Initialize Firebase Storage reference
            storageReference = FirebaseStorage.getInstance().reference

            val foodItems = listOf(
                FoodItem("Whole grain toast", "gs://myfirebase-f9939.appspot.com/f2.jpg", "Breakfast : 2-slices"),
                FoodItem("Scrambled eggs with spinach, tomatoes", "gs://myfirebase-f9939.appspot.com/f1.jpg", "Breakfast : 2 eggs, 1 cup spinach, 1/2 cup diced tomatoes"),
                FoodItem("Yogurt topped with berries and nuts", "gs://myfirebase-f9939.appspot.com/f3.jpg", "Breakfast : 1 cup yogurt, 1/2 cup mixed berries, 1/4 cup nuts"),
                FoodItem("Grilled chicken", "gs://myfirebase-f9939.appspot.com/f4.jpg", "Lunch : 4 ounces grilled chicken"),
                FoodItem("Whole grain wrap", "gs://myfirebase-f9939.appspot.com/f5.jpg", "Lunch : 1 wrap"),
                FoodItem("Apple", "gs://myfirebase-f9939.appspot.com/f6.jpg", "Lunch : 1 apple"),
                FoodItem("Pasta Primavera with Whole Wheat Pasta", "gs://myfirebase-f9939.appspot.com/f7.jpg", "Dinner : 1 cup whole wheat pasta, 1 cup vegetables, 1/4 cup marinara sauce"),
                FoodItem("Bean and Vegetable Chili with Cornbread", "gs://myfirebase-f9939.appspot.com/f8.jpg", "Dinner : 1 cup bean and vegetable chili, 1 slice cornbread"),
                FoodItem("Quinoa Salad with Avocado and Chickpeas", "gs://myfirebase-f9939.appspot.com/f9.jpg", "Dinner : 1 cup quinoa salad, 1/2 avocado, 1/4 cup chickpeas")
            )

            for (foodItem in foodItems) {
                val itemView = layoutInflater.inflate(R.layout.item_food, scrollView, false)
                val imageViewFood: ImageView = itemView.findViewById(R.id.imageViewFood)
                val textViewFoodName: TextView = itemView.findViewById(R.id.textViewFoodName)
                val textViewFoodQuantity: TextView = itemView.findViewById(R.id.textViewFoodQuantity)

                textViewFoodName.text = foodItem.name
                textViewFoodQuantity.text = foodItem.quantity

                // Load image using Glide
                loadImageFromStorage(foodItem.imageUrl, imageViewFood)

                scrollView.addView(itemView)
            }
        }

        // Method to load image from Firebase Storage and display in ImageView
        private fun loadImageFromStorage(imagePath: String, imageView: ImageView) {
            val storageRef =
                storageReference.child(imagePath.replace("gs://myfirebase-f9939.appspot.com/", ""))

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(imageView) // Load image into ImageView
            }.addOnFailureListener { exception ->
                Log.e("Firebase", "Failed to retrieve image URL", exception)
            }
        }
    }

    // Data class updated to use String for image URL
    //  data class FoodItem(val name: String, val imageUrl: String, val quantity: String)
