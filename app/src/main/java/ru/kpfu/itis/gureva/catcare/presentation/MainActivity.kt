package ru.kpfu.itis.gureva.catcare.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.ActivityMainBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.ui.diary.DiaryFragment
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.HelpfulFragment
import ru.kpfu.itis.gureva.catcare.presentation.ui.pets.MyPetsFragment
import ru.kpfu.itis.gureva.catcare.presentation.ui.profile.PetProfileEditingFragment
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragmentContainerId: Int = R.id.main_container

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(fragmentContainerId, PetProfileEditingFragment())
            .commit()
        setBottomNavigationItemSelectedListener()
        registerFragmentLifecycleCallbacksForBottomNavigation()
//
//        if (!sharedPreferences.getBoolean(Keys.REGISTRATION_KEY, false)) {
//            binding.run {
//                bottomNavigation.visibility = View.INVISIBLE
//                viewPager.visibility = View.VISIBLE
//
//                val list: ArrayList<RegistrationViewPagerFragment> = ArrayList()
//                for (index in RegistrationViewPagerModelRepository.list.indices) {
//                    val model = RegistrationViewPagerModelRepository.list[index]
//
//                    list.add(RegistrationViewPagerFragment.getInstance(
//                        model.title, model.description, model.image,
//                        index == RegistrationViewPagerModelRepository.list.size - 1)
//                    )
//                }
//                viewPager.adapter = RegistrationViewPagerAdapter(list, supportFragmentManager, lifecycle)
//                dotsIndicator.attachTo(viewPager)
//            }
//        }
//        else {
//            if (savedInstanceState == null) {
//                supportFragmentManager.beginTransaction()
//                    .add(fragmentContainerId, MyPetsFragment())
//                    .commit()
//            }
//        }
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

    private fun registerFragmentLifecycleCallbacksForBottomNavigation() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
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
}
