package ru.kpfu.itis.gureva.catcare.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.ActivityMainBinding
import ru.kpfu.itis.gureva.catcare.presentation.repository.WelcomeSpeechRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, HomeFragment())
                .commit()
        }
    }
}
