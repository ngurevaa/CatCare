package ru.kpfu.itis.gureva.catcare.presentation.screens.weight

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentWeightControlBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.adapter.WeightControlRecyclerViewAdapter
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileViewModel
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import ru.kpfu.itis.gureva.catcare.utils.observe

class WeightControlFragment : Fragment(R.layout.fragment_weight_control) {
    private var binding: FragmentWeightControlBinding? = null

    private var petId: Int? = null

    private val viewModel: WeightControlViewModel by lazyViewModel {
        requireContext().appComponent.getWeightControlViewModel().create(petId ?: 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeightControlBinding.bind(view)

        petId = arguments?.getInt(ARG_ID)

        val adapter = WeightControlRecyclerViewAdapter(listOf())
        binding?.run {
            rvWeight.addItemDecoration(SimpleVerticalDecorator(20))
            rvWeight.adapter = adapter

            btnAdd.setOnClickListener {
                WeightAddingBottomSheetFragment.newInstance(petId ?: 1).show(parentFragmentManager, null)
            }
        }

        viewModel.weights.observe(this@WeightControlFragment) {
            adapter.updateList(it)
        }
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(id: Int) = WeightControlFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
