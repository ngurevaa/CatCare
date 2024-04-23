package ru.kpfu.itis.gureva.catcare.presentation.screens.pets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentPetBinding

class PetFragment : Fragment(R.layout.fragment_pet) {
    private var binding: FragmentPetBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPetBinding.bind(view)

    }
}
