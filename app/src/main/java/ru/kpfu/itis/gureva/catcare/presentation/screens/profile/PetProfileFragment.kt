package ru.kpfu.itis.gureva.catcare.presentation.screens.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentPetBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.screens.medicine.MedicineFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.treatment.TreatmentFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.UnusualBehaviourFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.vaccination.VaccinationFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.vet.VetFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.weight.WeightControlFragment
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

        binding?.run {
            cvWeight.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(fragmentContainerId, WeightControlFragment.newInstance(petId ?: 1))
                    .addToBackStack(null)
                    .commit()
            }

            cvBehaviour.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(fragmentContainerId, UnusualBehaviourFragment.newInstance(petId ?: 1))
                    .addToBackStack(null)
                    .commit()
            }

            cvVaccination.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(fragmentContainerId, VaccinationFragment.newInstance(petId ?: 1))
                    .addToBackStack(null)
                    .commit()
            }

            cvFlea.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(fragmentContainerId, TreatmentFragment.newInstance(petId ?: 1))
                    .addToBackStack(null)
                    .commit()
            }

            cvMedicine.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(fragmentContainerId, MedicineFragment.newInstance(petId ?: 1))
                    .addToBackStack(null)
                    .commit()
            }

            cvVet.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(fragmentContainerId, VetFragment.newInstance(petId ?: 1))
                    .addToBackStack(null)
                    .commit()
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
