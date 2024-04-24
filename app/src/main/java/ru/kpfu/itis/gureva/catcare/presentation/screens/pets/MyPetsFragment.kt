package ru.kpfu.itis.gureva.catcare.presentation.screens.pets

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.Lazy
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentMyPetsBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.adapter.MyPetsRecyclerViewAdapter
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileEditingFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileFragment
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import javax.inject.Inject

class MyPetsFragment : Fragment(R.layout.fragment_my_pets) {
    private var binding: FragmentMyPetsBinding? = null

    private val fragmentContainerId = R.id.main_container

    @Inject
    internal lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    private val viewModel: MyPetsViewModel by viewModels {
        viewModelFactory.get()
    }

    private var adapter: MyPetsRecyclerViewAdapter? = null

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPetsBinding.bind(view)

        binding?.run {
            rvCats.addItemDecoration(SimpleVerticalDecorator(20))
            adapter = MyPetsRecyclerViewAdapter(listOf(), Glide.with(requireContext()), ::onPetClicked, ::onAddClicked)
            rvCats.adapter = adapter
        }

        observerData()
    }

    private fun observerData() {
        with(viewModel) {
            pets.observe(viewLifecycleOwner) {
                adapter?.updateList(it)
                adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun onPetClicked(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(fragmentContainerId, PetProfileFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    private fun onAddClicked() {
        parentFragmentManager.beginTransaction()
            .replace(fragmentContainerId, PetProfileEditingFragment())
            .addToBackStack(null)
            .commit()
    }
}
