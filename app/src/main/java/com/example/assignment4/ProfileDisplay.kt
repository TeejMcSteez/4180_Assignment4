package com.example.assignment4

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.databinding.ActivityProfileDisplayBinding

class ProfileDisplay : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDisplayBinding
    private var imageId: Int = R.drawable.select_image
    private var currentStudent: Student? = null

    private var editProfileLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val updatedUser = result.data!!.getParcelableExtra<Student>("user")
            if (updatedUser != null) {
                updateUI(updatedUser)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Profile"

        if (intent != null && intent.extras != null) {
            val user = intent.getParcelableExtra<Student>("user")
            if (user != null) {
                updateUI(user)
            }
        }

        binding.updateButton.setOnClickListener {
            currentStudent?.let { student ->
                val intent = Intent(this, EditProfile::class.java)
                intent.putExtra("user", student)
                editProfileLauncher.launch(intent)
            }
        }
    }

    private fun updateUI(user: Student) {
        currentStudent = user
        binding.nameVal.text = user.name
        binding.emailVal.text = user.email
        binding.suidVal.text = user.suid
        binding.departmentVal.text = user.role
        imageId = user.imageId
        binding.profileImageView.setImageResource(imageId)
    }
}