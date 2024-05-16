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
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.adding.BehaviourAddingFragment
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import ru.kpfu.itis.gureva.catcare.utils.observe
import java.text.SimpleDateFormat
import javax.inject.Inject

class UnusualBehaviourFragment : BaseFragment() {
    private var binding: FragmentNoteBinding? = null

    private var adapter: UnusualBehaviourRecyclerViewAdapter? = null

    override val viewModel: UnusualBehaviourViewModel by lazyViewModel {
        requireContext().appComponent.getUnusualBehaviourViewModel().create(petId ?: 1)
    }

    private val fragmentContainerId: Int = R.id.main_container

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteBinding.bind(view)

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

    override fun onItemDelete(position: Int) {
        super.onItemDelete(position)

        binding?.let {
            showItemRemovedSnackbar(it.root)
        }
    }

    private fun observerData() {
        viewModel.behaviours.observe(viewLifecycleOwner) { it ->
            adapter?.updateList(it)

            binding?.tvHint?.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    companion object {
        fun newInstance(id: Int) = UnusualBehaviourFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
