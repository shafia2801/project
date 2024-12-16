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

class VegObeseActivity : AppCompatActivity() {


    private lateinit var storageReference: StorageReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veg_obese)
        val scrollView: LinearLayout = findViewById(R.id.scrollViewContent)

        // Initialize Firebase Storage reference
        storageReference = FirebaseStorage.getInstance().reference

        val foodItems = listOf(
            // Breakfast items
            FoodItem(
                "Chia Seed Pudding with Almonds",
                "gs://myfirebase-f9939.appspot.com/chia_seed_pudding.jpg",
                "Breakfast: 1/2 cup chia seed pudding with almond milk, topped with almonds"
            ),
            FoodItem(
                "Avocado Toast",
                "gs://myfirebase-f9939.appspot.com/Avocado Toast.jpg",
                "Breakfast: Whole grain toast topped with 1/2 mashed avocado, seasoned with salt and pepper"
            ),

            // Lunch items
            FoodItem(
                "Grilled Paneer with Vegetables",
                "gs://myfirebase-f9939.appspot.com/grilled_paneer.jpg",
                "Lunch: 100 grams of grilled paneer with mixed vegetables (zucchini, bell peppers, carrots)"
            ),
            FoodItem(
                "Roasted Sweet Potatoes",
                "gs://myfirebase-f9939.appspot.com/roasted_sweet_potatoes.jpg",
                "Lunch: 1 cup roasted sweet potatoes, seasoned with olive oil and herbs"
            ),
            FoodItem(
                "Lentil and Kale Salad",
                "gs://myfirebase-f9939.appspot.com/lentil_kale_salad.jpg",
                "Lunch: 1 cup cooked lentils with kale, dressed with lemon and olive oil"
            ),

            // Dinner items
            FoodItem(
                "Stuffed Bell Peppers",
                "gs://myfirebase-f9939.appspot.com/stuffed_bell_peppers.jpg",
                "Dinner: Bell peppers stuffed with quinoa, beans, and spices"
            ),
            FoodItem(
                "Baked Tofu with Sesame Seeds",
                "gs://myfirebase-f9939.appspot.com/baked_tofu.jpg",
                "Dinner: 1 cup baked tofu with sesame seeds, served with steamed broccoli"
            ),

            // Snacks
            FoodItem(
                "Cottage Cheese and Berries",
                "gs://myfirebase-f9939.appspot.com/cottage_cheese_berries.jpg",
                "Snack: 1/2 cup low-fat cottage cheese with mixed berries"
            ),
            FoodItem(
                "Green Smoothie",
                "gs://myfirebase-f9939.appspot.com/green_smoothie.jpg",
                "Snack: Smoothie made with spinach, kale, cucumber, and almond milk"
            ),
            FoodItem(
                "Roasted Chickpeas",
                "gs://myfirebase-f9939.appspot.com/roasted_chickpeas.jpg",
                "Snack: 1/2 cup roasted chickpeas with paprika"
            )
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
