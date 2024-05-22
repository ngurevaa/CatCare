package ru.kpfu.itis.gureva.catcare.presentation.screens.registration

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentWelcomeViewPagerBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.adapter.RegistrationViewPagerAdapter
import ru.kpfu.itis.gureva.catcare.presentation.screens.helpful.HelpfulViewModel
import javax.inject.Inject

class WelcomeViewPagerFragment : Fragment(R.layout.fragment_welcome_view_pager) {
    private var binding: FragmentWelcomeViewPagerBinding? = null

    @Inject
    internal lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    private val viewModel: WelcomeViewModel by viewModels {
        viewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWelcomeViewPagerBinding.bind(view)

        binding?.run {
            viewModel.welcome.observe(viewLifecycleOwner) {
                val list = mutableListOf<WelcomeFragment>()

                for (index in it.indices) {
                    list.add(
                        WelcomeFragment.getInstance(
                            it[index].title, it[index].description, it[index].image,
                            index == it.size - 1
                        )
                    )
                }

                viewPager.adapter = RegistrationViewPagerAdapter(list, childFragmentManager, lifecycle)
                dotsIndicator.attachTo(viewPager)
            }
        }
    }
}
