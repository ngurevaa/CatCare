package ru.kpfu.itis.gureva.catcare.presentation.screens.diary

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.Lazy
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.data.database.entity.DiaryEntity
import ru.kpfu.itis.gureva.catcare.databinding.FragmentDiaryBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.adapter.DiaryRecyclerViewAdapter
import ru.kpfu.itis.gureva.catcare.presentation.screens.diary.adding.DiaryAddingFragment
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import javax.inject.Inject

class DiaryFragment : Fragment(R.layout.fragment_diary) {
    private var binding: FragmentDiaryBinding? = null

    private val fragmentContainerId: Int = R.id.main_container

    @Inject
    internal lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    private val viewModel: DiaryViewModel by viewModels {
        viewModelFactory.get()
    }

    private var adapter: DiaryRecyclerViewAdapter? = null

    private var snackbar: Snackbar? = null

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiaryBinding.bind(view)

        binding?.run {
            rvNotes.addItemDecoration(SimpleVerticalDecorator(30))
            adapter = DiaryRecyclerViewAdapter(Glide.with(requireContext()), ::onDeleteClicked, requireContext())
            rvNotes.adapter = adapter

            btnCreate.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(fragmentContainerId, DiaryAddingFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        observerData()
    }

    private fun observerData() {
        viewModel.notes.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
    }

    private fun onDeleteClicked(position: Int) {
        viewModel.removeItem(position)

        snackbar = binding?.let {
            Snackbar.make(it.root, getString(R.string.note_deleted), Snackbar.LENGTH_LONG)
                .setAction(R.string.cancel) {
                    viewModel.returnItem()
                }
        }
        snackbar?.show()
    }

    override fun onDestroyView() {
        snackbar?.dismiss()
        super.onDestroyView()
    }
}
