package ru.kpfu.itis.gureva.catcare.presentation.ui.helpful

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentCatFactBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.domain.usecase.GetCatFactUseCase
import ru.kpfu.itis.gureva.catcare.utils.observe
import javax.inject.Inject

class CatFactFragment : Fragment(R.layout.fragment_cat_fact) {
    private var binding: FragmentCatFactBinding? = null

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
        binding = FragmentCatFactBinding.bind(view)

        observerData()
        viewModel.getFact()

        binding?.btnAnotherFact?.setOnClickListener {
            viewModel.getFact()
        }
    }

    private fun observerData() {
        with(viewModel) {
            currentCatFactFlow.observe(this@CatFactFragment) {catFactUIModel ->
                catFactUIModel?.let {
                    binding?.run {
                        tvFact.text = it.fact

                        // load image
                    }
                }
            }
        }
    }
}
