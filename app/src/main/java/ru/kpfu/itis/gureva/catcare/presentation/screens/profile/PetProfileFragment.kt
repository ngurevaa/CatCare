package ru.kpfu.itis.gureva.catcare.presentation.screens.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentPetBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import java.util.Date

class PetProfileFragment : Fragment(R.layout.fragment_pet) {
    private var binding: FragmentPetBinding? = null

    private val fragmentContainerId = R.id.main_container

    private var petId: Int? = null

    private val viewModel: PetProfileViewModel by lazyViewModel {
        requireContext().appComponent.getPetProfileViewModel().create(petId ?: 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPetBinding.bind(view)

        petId = arguments?.getInt(ARG_ID)
        observerData()

        binding?.appBar?.run {
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit -> {
                        parentFragmentManager.beginTransaction()
                            .replace(fragmentContainerId, PetProfileEditingFragment.newInstance(petId ?: 1))
                            .addToBackStack(null)
                            .commit()
                        true
                    }
                    else -> { false}
                }
            }

            setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun observerData() {
        viewModel.pet.observe(viewLifecycleOwner) {
            it?.let { pet ->
                binding?.run {
                    tvName.text = pet.name
                    tvBreed.text = pet.breed
                    tvGender.text = pet.gender
                    tvBirthDay.text = pet.birthDay

                    Glide.with(requireContext())
                        .load(pet.image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivCat)
                }
            }
        }

        viewModel.age.observe(viewLifecycleOwner) {
            binding?.tvAge?.text = it
        }
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(id: Int) = PetProfileFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
