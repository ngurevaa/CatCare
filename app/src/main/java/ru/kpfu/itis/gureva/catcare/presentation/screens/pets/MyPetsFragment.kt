package ru.kpfu.itis.gureva.catcare.presentation.screens.pets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentMyPetsBinding

class MyPetsFragment : Fragment(R.layout.fragment_my_pets) {
    private var binding: FragmentMyPetsBinding? = null

    private val fragmentContainerId = R.id.main_container

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPetsBinding.bind(view)

        parentFragmentManager.beginTransaction()
            .replace(fragmentContainerId, PetFragment())
            .commit()
    }
}
