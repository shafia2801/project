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

class VegOverweightActivity : AppCompatActivity() {


    private lateinit var storageReference: StorageReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veg_overweight)

        val scrollView: LinearLayout = findViewById(R.id.scrollViewContent)

        // Initialize Firebase Storage reference
        storageReference = FirebaseStorage.getInstance().reference

        val foodItems = listOf(
            FoodItem(
                "Avocado Toast",
                "gs://myfirebase-f9939.appspot.com/Avocado Toast.jpg",
                "Breakfast: 1 slice whole grain toast topped with mashed avocado"
            ),
            FoodItem(
                "Green Smoothie",
                "gs://myfirebase-f9939.appspot.com/green_smoothie.jpg",
                "Breakfast: 1 cup spinach, 1/2 banana, 1/2 cup almond milk"
            ),
            FoodItem(
                "Chia Seed Pudding",
                "gs://myfirebase-f9939.appspot.com/chia_seed_pudding.jpg",
                "Breakfast: 1/2 cup chia seeds soaked in almond milk, topped with berries"
            ),
            FoodItem(
                "Grilled Vegetable Wrap",
                "gs://myfirebase-f9939.appspot.com/grilled_vegetable_wrap.jpg",
                "Lunch: 1 whole grain wrap filled with grilled vegetables (zucchini, bell peppers, and onions)"
            ),
            FoodItem(
                "Lentil Soup",
                "gs://myfirebase-f9939.appspot.com/lentil_soup.jpg",
                "Lunch: 1 cup lentil soup with vegetables (carrots, celery, onions)"
            ),
            FoodItem(
                "Baked Sweet Potato",
                "gs://myfirebase-f9939.appspot.com/baked_sweet_potato.jpg",
                "Snack: 1 medium sweet potato with a sprinkle of cinnamon"
            ),
            FoodItem(
                "Grilled Tofu",
                "gs://myfirebase-f9939.appspot.com/grilled_tofu.jpg",
                "Dinner: 4-6 ounces of grilled tofu with a side of steamed broccoli"
            ),
            FoodItem(
                "Stuffed Bell Peppers",
                "gs://myfirebase-f9939.appspot.com/stuffed_bell_peppers.jpg",
                "Dinner: Bell peppers stuffed with quinoa, black beans, and diced tomatoes"
            ),
            FoodItem(
                "Roasted Brussels Sprouts",
                "gs://myfirebase-f9939.appspot.com/roasted_brussels_sprouts.jpg",
                "Dinner: 1 cup roasted Brussels sprouts with olive oil and herbs"
            ),
            FoodItem(
                "Greek Yogurt with Flaxseeds",
                "gs://myfirebase-f9939.appspot.com/greek_yogurt.jpg",
                "Snack: 1/2 cup Greek yogurt topped with flaxseeds"
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

// data class FoodItem(val name: String, val imageUrl: String, val quantity: String)
