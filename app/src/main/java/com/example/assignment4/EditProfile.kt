package com.example.assignment4

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment4.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private var selectedImageId: Int = R.drawable.select_image
    private var activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                selectedImageId = result.data!!.getIntExtra("image_id", R.drawable.select_image)
                binding.editProfileImageView.setImageResource(selectedImageId)
            }
        })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Update Profile"

        var oldName: String = "null"
        var oldEmail: String = "null"
        var oldSuid: String = "null"
        var oldDepartment: String = "null"
        var oldImageId = R.drawable.select_image

        if (intent != null && intent.extras != null) {
            selectedImageId = intent.getIntExtra("image_id", R.drawable.select_image)
            oldImageId = selectedImageId
            binding.editProfileImageView.setImageResource(selectedImageId)
            val name = intent.getStringExtra("name").toString()
            oldName = name
            binding.editNameTextInputEditView.setText(name)
            val email = intent.getStringExtra("email").toString()
            oldEmail = email
            binding.editEmailTextInputEditView.setText(email)
            val suid = intent.getStringExtra("suid").toString()
            oldSuid = suid
            binding.editSuidTextInputEditText.setText(suid)
            val role = intent.getStringExtra("role").toString()
            oldDepartment = role
            binding.editRadioGroup.clearCheck()
            when (role) {
                "CS" -> binding.editCSRadioButton.isChecked = true
                "SIS" -> binding.editSisRadioButton.isChecked = true
                "BIO" -> binding.editBioRadioButton.isChecked = true
                "Other" -> binding.editOtherRadioButton.isChecked = true
                else -> binding.editRadioGroup.clearCheck()
            }
        }

        binding.editProfileImageView.setOnClickListener {
            val sa = Intent(this, AvatarSelect::class.java)
            activityResultLauncher.launch(sa)
        }

        binding.editSaveButton.setOnClickListener {

            val name = binding.editNameTextInputEditView.text.toString()
            if (name == "null" || name.trim() == "") {
                Toast.makeText(this, "Name is blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val email = binding.editEmailTextInputEditView.text.toString()
            if (email == "null" || email.trim() == "") {
                Toast.makeText(this, "Email is blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val suid = binding.editSuidTextInputEditText.text.toString()
            if (suid ==  "null" || suid.trim() == "") {
                Toast.makeText(this, "Student ID is blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.editRadioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please select a Department for the profile", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val dep = findViewById<RadioButton>(binding.editRadioGroup.checkedRadioButtonId).text.toString()
            if (dep == "null" || email.trim() == "") {
                Toast.makeText(this, "Please select a Department for the profile", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (selectedImageId == R.drawable.select_image) {
                Toast.makeText(this, "Please select a profile image", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val profileIntent = Intent(this, ProfileDisplay::class.java)
            profileIntent.putExtra("name", name)
                .putExtra("email", email)
                .putExtra("suid", suid)
                .putExtra("role", dep)
                .putExtra("image_id", selectedImageId)
            startActivity(profileIntent)
        }

        binding.editCancelButton.setOnClickListener {
            val cancelIntent = Intent(this, ProfileDisplay::class.java)
            cancelIntent.putExtra("name", oldName)
                .putExtra("email", oldEmail)
                .putExtra("suid", oldSuid)
                .putExtra("role", oldDepartment)
                .putExtra("image_id", oldImageId)
            startActivity(cancelIntent)
        }
    }
}