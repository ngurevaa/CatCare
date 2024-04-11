package ru.kpfu.itis.gureva.catcare.presentation.ui.helpful

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.Lazy
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentHelpfulBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.domain.usecase.GetCatFactUseCase
import ru.kpfu.itis.gureva.catcare.utils.observe
import javax.inject.Inject

class HelpfulFragment : Fragment(R.layout.fragment_helpful) {

    private var binding: FragmentHelpfulBinding? = null

    private val fragmentContainerId: Int = R.id.main_container

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHelpfulBinding.bind(view)

        binding?.btnFact?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(fragmentContainerId, CatFactFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
