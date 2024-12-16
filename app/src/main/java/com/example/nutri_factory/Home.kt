package com.example.nutri_factory


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.hm->{
                Toast.makeText(this," Home option is selected", Toast.LENGTH_SHORT).show()
                val i= Intent(this,Mhome::class.java)
                startActivity(i)
            }
            R.id.cnt->{
                Toast.makeText(this,"contact option is selected", Toast.LENGTH_SHORT).show()
                val i= Intent(this,Contact::class.java)
                startActivity(i)
            }
            R.id.share->{
                Toast.makeText(this,"share option is selected", Toast.LENGTH_SHORT).show()
                val i= Intent(this,Share::class.java)
                startActivity(i)
            }
            R.id.set->{
                Toast.makeText(this,"settings option is selected", Toast.LENGTH_SHORT).show()
                val i= Intent(this,Setting::class.java)
                startActivity(i)
            }

            R.id.lgt -> {
                showConfirmationDialog(
                    "Navigate Confirmation",
                    "Are you sure you want to go back to the Login page?",
                    "Go to Login"
                ) {
                    val i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }
            R.id.profile->{
                Toast.makeText(this,"profile option is selected", Toast.LENGTH_SHORT).show()
                val i= Intent(this,Profie::class.java)
                startActivity(i)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showConfirmationDialog(title: String, message: String, positiveButtonText: String, onPositiveClick: () -> Unit) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(title)
        dialogBuilder.setMessage(message)
        dialogBuilder.setPositiveButton(positiveButtonText) { dialog, _ ->
            onPositiveClick() // Execute the specified action
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss() // Just dismiss the dialog
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

}





/* private late init var drawerLayout: DrawerLayout
private late init var navigationView: NavigationView
late init var drawerToggle: ActionBarDrawerToggle
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    drawerLayout = findViewById(R.id.drawer_layout)
    navigationView = findViewById(R.id.nav_view)
drawerToggle =  ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
drawerLayout.addDrawerListener(drawerToggle)
    drawerToggle.syncState()
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
navigationView.setNavigationItemSelectedListener{
when(MenuItem.SHOW_AS_ACTION_NEVER) {


    R.id.hm->{
        Toast.makeText(this,"settings option selected", Toast.LENGTH_SHORT).show()
        val i= Intent(this,Mhome::class.java)
        startActivity(i)
    }
    R.id.share->{
        Toast.makeText(this,"about us option selected", Toast.LENGTH_SHORT).show()
        val i= Intent(this,Share::class.java)
        startActivity(i)
    }
    R.id.set->{
        Toast.makeText(this,"about us option selected", Toast.LENGTH_SHORT).show()
        val i= Intent(this,Setting::class.java)
        startActivity(i)
    }

    R.id.lgt->{
        Toast.makeText(this,"logout is successful", Toast.LENGTH_SHORT).show()
        val i= Intent(this,Logout::class.java)
        startActivity(i)
    }
}
true
}
}





} */
