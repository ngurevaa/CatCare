package ru.kpfu.itis.gureva.catcare.presentation.screens.helpful

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.Lazy
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentHelpfulBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.adapter.NewsRecyclerViewAdapter
import ru.kpfu.itis.gureva.catcare.presentation.screens.helpful.fact.CatFactFragment
import ru.kpfu.itis.gureva.catcare.utils.SimpleVerticalDecorator
import javax.inject.Inject

class HelpfulFragment : Fragment(R.layout.fragment_helpful) {

    private var binding: FragmentHelpfulBinding? = null

    private val fragmentContainerId: Int = R.id.main_container

    private var adapter: NewsRecyclerViewAdapter? = null

    @Inject
    internal lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    private val viewModel: HelpfulViewModel by viewModels {
        viewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHelpfulBinding.bind(view)

        binding?.run {
            btnFact.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(fragmentContainerId, CatFactFragment())
                    .addToBackStack(null)
                    .commit()
            }

            rvNews.addItemDecoration(SimpleVerticalDecorator(30))
            adapter = NewsRecyclerViewAdapter(Glide.with(requireContext()))
            rvNews.adapter = adapter
        }

        observerData()
    }

    private fun observerData() {
        viewModel.news.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
    }
}
