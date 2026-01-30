package com.example.assignment4

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.databinding.ActivityAvatarSelectBinding

class AvatarSelect : AppCompatActivity() {
    private lateinit var binding: ActivityAvatarSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Select Avatar"

        val l = View.OnClickListener { v ->
            val did = when (v.id) {
                R.id.av1 -> R.drawable.avatar_m_1
                R.id.av2 -> R.drawable.avatar_f_1
                R.id.av3 -> R.drawable.avatar_m_2
                R.id.av4 -> R.drawable.avatar_f_2
                R.id.av5 -> R.drawable.avatar_m_3
                R.id.av6 -> R.drawable.avatar_f_3
                else -> R.drawable.select_image
            }
            val resIntent = Intent()
            resIntent.putExtra("image_id", did)
            setResult(RESULT_OK, resIntent)
            finish()
        }
        binding.av1.setOnClickListener(l)
        binding.av2.setOnClickListener(l)
        binding.av3.setOnClickListener(l)
        binding.av4.setOnClickListener(l)
        binding.av5.setOnClickListener(l)
        binding.av6.setOnClickListener(l)

        binding.cancelButton.setOnClickListener {
            val profileIntent = Intent(this, Landing::class.java)
            startActivity(profileIntent)
        }

    }
}