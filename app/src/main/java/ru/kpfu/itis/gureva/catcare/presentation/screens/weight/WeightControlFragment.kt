package ru.kpfu.itis.gureva.catcare.presentation.screens.weight

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentNoteBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.adapter.WeightControlRecyclerViewAdapter
import ru.kpfu.itis.gureva.catcare.presentation.helper.ItemTouchSwipeLeft
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.weight.adding.WeightAddingBottomSheetFragment
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import javax.inject.Inject

class WeightControlFragment : BaseFragment() {
    private var binding: FragmentNoteBinding? = null

    override val viewModel: WeightControlViewModel by lazyViewModel {
        requireContext().appComponent.getWeightControlViewModel().create(petId ?: 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteBinding.bind(view)

        val adapter = WeightControlRecyclerViewAdapter(listOf())
        binding?.run {
            tvTitle.text = getString(R.string.weight_control)

            rv.addItemDecoration(SimpleVerticalDecorator(20))
            rv.adapter = adapter

            val itemTouchHelper = ItemTouchHelper(ItemTouchSwipeLeft(::onItemDelete, resourceManager))
            itemTouchHelper.attachToRecyclerView(rv)

            btnAdd.setOnClickListener {
                WeightAddingBottomSheetFragment.newInstance(petId ?: 1).show(parentFragmentManager, null)
            }
        }

        viewModel.weights.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }

    override fun onItemDelete(position: Int) {
        super.onItemDelete(position)

        binding?.let {
            showItemRemovedSnackbar(it.root)
        }
    }

    companion object {
        fun newInstance(id: Int) = WeightControlFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
