package ru.kpfu.itis.gureva.catcare.presentation.screens.unusual

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity
import ru.kpfu.itis.gureva.catcare.databinding.FragmentNoteBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.adapter.UnusualBehaviourRecyclerViewAdapter
import ru.kpfu.itis.gureva.catcare.presentation.helper.ItemTouchSwipeLeft
import ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.adding.BehaviourAddingFragment
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import ru.kpfu.itis.gureva.catcare.utils.observe
import java.text.SimpleDateFormat
import javax.inject.Inject

class UnusualBehaviourFragment : Fragment(R.layout.fragment_note) {
    private var binding: FragmentNoteBinding? = null

    private var petId: Int? = null

    private var adapter: UnusualBehaviourRecyclerViewAdapter? = null

    private val viewModel: UnusualBehaviourViewModel by lazyViewModel {
        requireContext().appComponent.getUnusualBehaviourViewModel().create(petId ?: 1)
    }

    private val fragmentContainerId: Int = R.id.main_container

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

        binding?.run {
            tvTitle.text = getString(R.string.unusual_behaviour)

            adapter = UnusualBehaviourRecyclerViewAdapter(
                listOf(),
                resourceManager,
                Glide.with(requireContext())
            )
            rv.addItemDecoration(SimpleVerticalDecorator(20))
            rv.adapter = adapter

            val itemTouchHelper = ItemTouchHelper(ItemTouchSwipeLeft(::onItemDelete, resourceManager))
            itemTouchHelper.attachToRecyclerView(rv)

            btnAdd.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(fragmentContainerId, BehaviourAddingFragment.newInstance(petId ?: 1))
                    .addToBackStack(null)
                    .commit()
            }
        }

        observerData()
    }

    private fun onItemDelete(position: Int) {
        viewModel.removeItem(position)

        binding?.let { Snackbar.make(it.root, getString(R.string.note_deleted), Snackbar.LENGTH_LONG)
                            .setAction(R.string.cancel) {
                                viewModel.returnItem()
                            }.show()
        }
    }

    private fun observerData() {
        viewModel.behaviours.observe(viewLifecycleOwner) { it ->
            adapter?.updateList(it)
        }
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(id: Int) = UnusualBehaviourFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
