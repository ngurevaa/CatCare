package ru.kpfu.itis.gureva.catcare.presentation.screens.medicine

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentNoteBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.adapter.MedicineRecyclerViewAdapter
import ru.kpfu.itis.gureva.catcare.presentation.helper.ItemTouchSwipeLeft
import ru.kpfu.itis.gureva.catcare.presentation.screens.medicine.adding.MedicineAddingBottomSheetFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.vaccination.adding.VaccinationAddingBottomSheetFragment
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import ru.kpfu.itis.gureva.catcare.utils.observe
import java.text.SimpleDateFormat
import javax.inject.Inject

class MedicineFragment : Fragment(R.layout.fragment_note) {
    private var binding: FragmentNoteBinding? = null

    private var petId: Int? = null

    private val viewModel: MedicineViewModel by lazyViewModel {
        requireContext().appComponent.getMedicineViewModel().create(petId ?: 1)
    }

    private var adapter: MedicineRecyclerViewAdapter? = null

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
            tvTitle.text = getString(R.string.medicine)

            adapter = MedicineRecyclerViewAdapter()
            rv.addItemDecoration(SimpleVerticalDecorator(20))
            rv.adapter = adapter

            val itemTouchHelper = ItemTouchHelper(ItemTouchSwipeLeft(::onItemDelete, resourceManager))
            itemTouchHelper.attachToRecyclerView(rv)

            btnAdd.setOnClickListener {
                MedicineAddingBottomSheetFragment.newInstance(petId ?: 1).show(parentFragmentManager, null)
            }
        }

        observerData()
    }

    private fun observerData() {
        viewModel.medicines.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
    }

    private fun onItemDelete(position: Int) {
        viewModel.removeItem(position)
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(id: Int) = MedicineFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
