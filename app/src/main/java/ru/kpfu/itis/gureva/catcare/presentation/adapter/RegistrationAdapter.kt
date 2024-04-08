package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kpfu.itis.gureva.catcare.presentation.model.RegistrationViewPagerModel
import ru.kpfu.itis.gureva.catcare.presentation.ui.registration.RegistrationViewPagerFragment

class RegistrationAdapter(
    private val list: List<RegistrationViewPagerFragment>,
    manager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(manager, lifecycle) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}
