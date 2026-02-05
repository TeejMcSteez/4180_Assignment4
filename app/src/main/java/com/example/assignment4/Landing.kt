package com.example.assignment4

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.databinding.ActivityMainBinding

class Landing : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedImageId: Int = R.drawable.select_image
    private var activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                selectedImageId = result.data!!.getIntExtra("image_id", R.drawable.select_image)
                binding.landingProfileImage.setImageResource(selectedImageId)
            }
        })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Create Profile"

        if (intent != null && intent.extras != null) {
            val user = intent.getParcelableExtra<Student>("user")
            if (user != null) {
                selectedImageId = user.imageId
                binding.nameInput.setText(user.name)
                binding.emailInput.setText(user.email)
                binding.suidInput.setText(user.suid)
                when (user.role) {
                    "CS" -> binding.CSRadioButton.isChecked = true
                    "SIS" -> binding.SISRadioButton.isChecked = true
                    "BIO" -> binding.BioRadioButton.isChecked = true
                    "Other" -> binding.OtherRadioButton.isChecked = true
                    else -> binding.depSelectRadioGroup.clearCheck()
                }
            }
        }

        binding.landingProfileImage.setOnClickListener {
            val sa = Intent(this, AvatarSelect::class.java)
            if (selectedImageId != R.drawable.select_image) {
                sa.putExtra("image_id", selectedImageId)
            }
            activityResultLauncher.launch(sa)
        }

        binding.landingSaveButton.setOnClickListener {

            val name = binding.nameInput.text.toString()
            if (name == "null" || name.trim() == "") {
                Toast.makeText(this, "Name is blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val email = binding.emailInput.text.toString()
            if (email == "null" || email.trim() == "") {
                Toast.makeText(this, "Email is blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val suid = binding.suidInput.text.toString()
            if (suid ==  "null" || suid.trim() == "") {
                Toast.makeText(this, "Student ID is blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.depSelectRadioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please select a Department for the profile", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val dep = findViewById<RadioButton>(binding.depSelectRadioGroup.checkedRadioButtonId).text.toString()
            if (dep == "null" || dep.trim() == "") {
                Toast.makeText(this, "Please select a Department for the profile", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (selectedImageId == R.drawable.select_image) {
                Toast.makeText(this, "Please select a profile image", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val user = Student(name, email, suid, dep, selectedImageId)
            val profileIntent = Intent(this, ProfileDisplay::class.java)
            profileIntent.putExtra("user", user)
            startActivity(profileIntent)
            finish()
        }
    }
}