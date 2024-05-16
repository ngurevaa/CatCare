package ru.kpfu.itis.gureva.catcare.presentation.screens.treatment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentNoteBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.adapter.TreatmentRecyclerViewAdapter
import ru.kpfu.itis.gureva.catcare.presentation.helper.ItemTouchSwipeLeft
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.treatment.adding.TreatmentAddingBottomSheetFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.vaccination.adding.VaccinationAddingBottomSheetFragment
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import ru.kpfu.itis.gureva.catcare.utils.observe
import java.text.SimpleDateFormat
import javax.inject.Inject

class TreatmentFragment : BaseFragment() {
    private var binding: FragmentNoteBinding? = null

    override val viewModel: TreatmentViewModel by lazyViewModel {
        requireContext().appComponent.getTreatmentViewModel().create(petId ?: 1)
    }

    private var adapter: TreatmentRecyclerViewAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteBinding.bind(view)

        binding?.run {
            tvTitle.text = getString(R.string.flea_treatment)

            adapter = TreatmentRecyclerViewAdapter()
            rv.addItemDecoration(SimpleVerticalDecorator(20))
            rv.adapter = adapter

            val itemTouchHelper = ItemTouchHelper(ItemTouchSwipeLeft(::onItemDelete, resourceManager))
            itemTouchHelper.attachToRecyclerView(rv)

            btnAdd.setOnClickListener {
                TreatmentAddingBottomSheetFragment.newInstance(petId ?: 1).show(parentFragmentManager, null)
            }
        }

        observerData()
    }

    private fun observerData() {
        viewModel.treatments.observe(viewLifecycleOwner) {
            adapter?.submitList(it)

            binding?.tvHint?.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onItemDelete(position: Int) {
        super.onItemDelete(position)

        binding?.let {
            showItemRemovedSnackbar(it.root)
        }
    }

    companion object {
        fun newInstance(id: Int) = TreatmentFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
