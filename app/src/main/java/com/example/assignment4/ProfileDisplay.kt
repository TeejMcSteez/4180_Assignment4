package com.example.assignment4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.databinding.ActivityProfileDisplayBinding

class ProfileDisplay : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDisplayBinding
    private var imageId: Int = R.drawable.select_image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Profile"

        if (intent != null && intent.extras != null) {
            val name = intent.getStringExtra("name").toString()
            val email = intent.getStringExtra("email").toString()
            val suid = intent.getStringExtra("suid").toString()
            val role = intent.getStringExtra("role").toString()
            imageId = intent.getIntExtra("image_id", R.drawable.select_image)
            binding.nameVal.text = name
            binding.emailVal.text = email
            binding.suidVal.text = suid
            binding.departmentVal.text = role
            binding.profileImageView.setImageResource(imageId)

        }

        binding.updateButton.setOnClickListener {
            val landingIntent = Intent(this, EditProfile::class.java)
            landingIntent.putExtra("name", binding.nameVal.text.toString())
            landingIntent.putExtra("email", binding.emailVal.text.toString())
            landingIntent.putExtra("suid", binding.suidVal.text.toString())
            landingIntent.putExtra("role", binding.departmentVal.text.toString())
            landingIntent.putExtra("image_id", imageId)
            startActivity(landingIntent)
        }
    }
}