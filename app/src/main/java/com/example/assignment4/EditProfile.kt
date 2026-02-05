package com.example.assignment4

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private var selectedImageId: Int = R.drawable.select_image
    private var activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            selectedImageId = result.data!!.getIntExtra("image_id", R.drawable.select_image)
            binding.editProfileImageView.setImageResource(selectedImageId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Update Profile"

        if (intent != null && intent.extras != null) {
            val user = intent.getParcelableExtra<Student>("user")
            if (user != null) {
                selectedImageId = user.imageId
                binding.editProfileImageView.setImageResource(selectedImageId)
                binding.editNameTextInputEditView.setText(user.name)
                binding.editEmailTextInputEditView.setText(user.email)
                binding.editSuidTextInputEditText.setText(user.suid)
                when (user.role) {
                    "CS" -> binding.editCSRadioButton.isChecked = true
                    "SIS" -> binding.editSisRadioButton.isChecked = true
                    "BIO" -> binding.editBioRadioButton.isChecked = true
                    "Other" -> binding.editOtherRadioButton.isChecked = true
                }
            }
        }

        binding.editProfileImageView.setOnClickListener {
            val sa = Intent(this, AvatarSelect::class.java)
            sa.putExtra("image_id", selectedImageId)
            activityResultLauncher.launch(sa)
        }

        binding.editSaveButton.setOnClickListener {
            val name = binding.editNameTextInputEditView.text.toString()
            val email = binding.editEmailTextInputEditView.text.toString()
            val suid = binding.editSuidTextInputEditText.text.toString()
            
            if (name.isBlank() || email.isBlank() || suid.isBlank() || 
                binding.editRadioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dep = findViewById<RadioButton>(binding.editRadioGroup.checkedRadioButtonId).text.toString()
            val user = Student(name, email, suid, dep, selectedImageId)
            
            val resultIntent = Intent()
            resultIntent.putExtra("user", user)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.editCancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}