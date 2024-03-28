package ru.kpfu.itis.gureva.catcare.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.ActivityMainBinding
import ru.kpfu.itis.gureva.catcare.presentation.ui.diary.DiaryFragment
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.HelpfulFragment
import ru.kpfu.itis.gureva.catcare.presentation.ui.pets.MyPetsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragmentContainerId: Int = R.id.main_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigationItemSelectedListener()
//        binding.bottomNavigation.selectedItemId = R.id.my_pets
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(fragmentContainerId, MyPetsFragment())
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                when (f) {
                    is DiaryFragment -> {
                        binding.bottomNavigation.visibility = View.VISIBLE
                    }
                    is HelpfulFragment -> {
                        binding.bottomNavigation.visibility = View.VISIBLE
                    }
                    is MyPetsFragment -> {
                        binding.bottomNavigation.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.bottomNavigation.visibility = View.GONE
                    }
                }
            }
        }, true)
    }

    private fun setBottomNavigationItemSelectedListener() {
        binding.bottomNavigation.setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.my_pets -> {
                    supportFragmentManager.beginTransaction()
                        .replace(fragmentContainerId, MyPetsFragment())
                        .commit()
                    true
                }

                R.id.diary -> {
                    supportFragmentManager.beginTransaction()
                        .replace(fragmentContainerId, DiaryFragment())
                        .commit()
                    true
                }

                R.id.helpful -> {
                    supportFragmentManager.beginTransaction()
                        .replace(fragmentContainerId, HelpfulFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }
}
