package ru.kpfu.itis.gureva.catcare.presentation.ui.helpful

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentHelpfulBinding
import ru.kpfu.itis.gureva.catcare.di.MainApp
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.domain.usecase.GetCatFactUseCase
import javax.inject.Inject

class HelpfulFragment : Fragment(R.layout.fragment_helpful) {
    private var binding: FragmentHelpfulBinding? = null
    private val viewModel: HelpfulViewModel by viewModels() {HelpfulViewModel.Factory}

    @Inject
    lateinit var getCatFactUseCase: GetCatFactUseCase

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHelpfulBinding.bind(view)

//        observerData()

        binding?.btnFact?.setOnClickListener {
//            viewModel.getFact()
            lifecycleScope.launch {
                binding?.tvF?.text = getCatFactUseCase.invoke().fact
            }
        }
    }

    private fun observerData() {
        with(viewModel) {
            currentCatFactFlow.observe(this@HelpfulFragment) {catFactUIModel ->  
                catFactUIModel?.let {
                    binding?.tvF?.text = it.fact
                }
            }
        }
    }
}

inline fun <T> Flow<T>.observe(fragment: Fragment, crossinline block: (T) -> Unit): Job {
    val lifecycleOwner = fragment.viewLifecycleOwner
    return lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            collect { data -> block(data) }
        }
    }
}
