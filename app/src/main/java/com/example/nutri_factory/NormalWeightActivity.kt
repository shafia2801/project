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

class NormalWeightActivity : AppCompatActivity() {


        private lateinit var storageReference: StorageReference

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_normal_weight)

            val scrollView: LinearLayout = findViewById(R.id.scrollViewContent)

            // Initialize Firebase Storage reference
            storageReference = FirebaseStorage.getInstance().reference

            val foodItems = listOf(
                FoodItem("orange juice", "gs://myfirebase-f9939.appspot.com/n.jpg", "Breakfast: 1-glass"),
                FoodItem("Scrambled eggs with spinach, tomatoes", "gs://myfirebase-f9939.appspot.com/f1.jpg", "Breakfast: 2 eggs, 1 cup spinach, 1/2 cup diced tomatoes"),
                FoodItem("yogurt topped with berries and nuts", "gs://myfirebase-f9939.appspot.com/f3.jpg", "Breakfast: 1 cup yogurt, 1/2 cup mixed berries, 1/4 cup nuts"),
                FoodItem("Grilled chicken", "gs://myfirebase-f9939.appspot.com/f4.jpg", "Lunch: 4 ounces grilled chicken"),
                FoodItem("Whole grain wrap", "gs://myfirebase-f9939.appspot.com/f5.jpg", "Lunch: 1 wrap"),
                FoodItem("banana", "gs://myfirebase-f9939.appspot.com/n1.jpg", "Lunch: 1 banana"),
                FoodItem("lentil stew served with brown rice", "gs://myfirebase-f9939.appspot.com/n2.jpg", "Dinner: lentil stew served with brown rice, 1/2 cup cooked brown rice"),
                FoodItem("Side salad with vinaigrette dressing", "gs://myfirebase-f9939.appspot.com/n4.jpg", "Dinner: 2 cups mixed greens, 2 tablespoons vinaigrette dressing"),
                FoodItem("milk", "gs://myfirebase-f9939.appspot.com/n3.jpg", "Dinner: 1 cup")
            )

            // Populate ScrollView with food items
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
            val storageRef = storageReference.child(imagePath.replace("gs://myfirebase-f9939.appspot.com/", ""))

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(imageView) // Load image into ImageView
            }.addOnFailureListener { exception ->
                Log.e("Firebase", "Failed to retrieve image URL", exception)
            }
        }
    }

    data class FoodItem(val name: String, val imageUrl: String, val quantity: String) {

    }
