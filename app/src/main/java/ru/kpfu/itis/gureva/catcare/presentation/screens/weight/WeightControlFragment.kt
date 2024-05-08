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
import ru.kpfu.itis.gureva.catcare.presentation.screens.weight.adding.WeightAddingBottomSheetFragment
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import javax.inject.Inject

class WeightControlFragment : Fragment(R.layout.fragment_note) {
    private var binding: FragmentNoteBinding? = null

    private var petId: Int? = null

    private val viewModel: WeightControlViewModel by lazyViewModel {
        requireContext().appComponent.getWeightControlViewModel().create(petId ?: 1)
    }

    @Inject
    lateinit var resourceManager: ResourceManager

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteBinding.bind(view)

        petId = arguments?.getInt(ARG_ID)

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

    private fun onItemDelete(position: Int) {
        viewModel.removeItem(position)

        binding?.let { Snackbar.make(it.root, getString(R.string.note_deleted), Snackbar.LENGTH_LONG)
                            .setAction(R.string.cancel) {
                                viewModel.returnItem()
                            }.show()
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
