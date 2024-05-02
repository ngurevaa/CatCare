package ru.kpfu.itis.gureva.catcare.presentation.screens.unusual

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity
import ru.kpfu.itis.gureva.catcare.databinding.FragmentUnusualBehaviourBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.adapter.UnusualBehaviourRecyclerViewAdapter
import ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.adding.BehaviourAddingFragment
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import ru.kpfu.itis.gureva.catcare.utils.observe
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.function.Function
import java.util.stream.Collectors
import javax.inject.Inject


class UnusualBehaviourFragment : Fragment(R.layout.fragment_unusual_behaviour) {
    private var binding: FragmentUnusualBehaviourBinding? = null

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
        binding = FragmentUnusualBehaviourBinding.bind(view)

        petId = arguments?.getInt(ARG_ID)

        binding?.run {
            adapter = UnusualBehaviourRecyclerViewAdapter(
                listOf(),
                resourceManager,
                Glide.with(requireContext())
            )
            rvBehaviours.addItemDecoration(SimpleVerticalDecorator(20))
            rvBehaviours.adapter = adapter

            btnAdd.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(fragmentContainerId, BehaviourAddingFragment.newInstance(petId ?: 1))
                    .addToBackStack(null)
                    .commit()
            }
        }

        observerData()
    }

    private fun observerData() {
        viewModel.behaviours.observe(this@UnusualBehaviourFragment) { it ->
            val dateFormat = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME)
            val list = it.sortedByDescending { item ->
                dateFormat.parse(item.date)?.time
            }
            adapter?.updateList(list)
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
