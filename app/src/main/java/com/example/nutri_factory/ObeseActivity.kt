package com.example.nutri_factory

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ObeseActivity : AppCompatActivity() {


        private lateinit var storageReference: StorageReference

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_obese)
            val scrollView: LinearLayout = findViewById(R.id.scrollViewContent)

            // Initialize Firebase Storage reference
            storageReference = FirebaseStorage.getInstance().reference

            val foodItems = listOf(
                FoodItem("Scrambled egg white with spinach", "gs://myfirebase-f9939.appspot.com/oo.jpg", "Breakfast: 2 egg with 1 cup of spinach"),
                FoodItem("Whole grain toast", "gs://myfirebase-f9939.appspot.com/f2.jpg", "Breakfast: 1 slice"),
                FoodItem("Almond milk", "gs://myfirebase-f9939.appspot.com/a.jpg", "Breakfast: 1 cup of unsweetened almond milk"),
                FoodItem("Tofu salad", "gs://myfirebase-f9939.appspot.com/ooo.jpg", "Dinner: 2 cups mixed greens, 2 tablespoons vinaigrette dressing"),
                FoodItem("Grilled chicken breast", "gs://myfirebase-f9939.appspot.com/g.jpg", "Lunch: 113 grams of cooked chicken breast, seasoned with herbs and spices."),
                FoodItem("Water with lemon", "gs://myfirebase-f9939.appspot.com/o2.jpg", "Lunch: 1 glass of water with 1 lemon"),
                FoodItem("Grilled fish fillet", "gs://myfirebase-f9939.appspot.com/f.jpg", "Dinner: 4-6 ounces of grilled fish seasoned with herbs and lemon juice."),
                FoodItem("Quinoa pilaf", "gs://myfirebase-f9939.appspot.com/p.jpg", "Dinner: 1/2 cup of cooked quinoa mixed with diced vegetables"),
                FoodItem("Citrus fruits", "gs://myfirebase-f9939.appspot.com/ff.jpg", "Dinner: 2 fruits. Avoid adding sugar or sweeteners.")
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

  //  data class FoodItem(val name: String, val imageUrl: String, val quantity: String)
