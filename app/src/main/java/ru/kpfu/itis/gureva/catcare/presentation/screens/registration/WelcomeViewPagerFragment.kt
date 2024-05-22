package ru.kpfu.itis.gureva.catcare.presentation.screens.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentWelcomeViewPagerBinding
import ru.kpfu.itis.gureva.catcare.presentation.adapter.RegistrationViewPagerAdapter
import ru.kpfu.itis.gureva.catcare.presentation.repository.WelcomeViewPagerModelRepository

class WelcomeViewPagerFragment : Fragment(R.layout.fragment_welcome_view_pager) {
    private var binding: FragmentWelcomeViewPagerBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWelcomeViewPagerBinding.bind(view)

        binding?.run {
            val list = mutableListOf<WelcomeFragment>()

            for (index in WelcomeViewPagerModelRepository.list.indices) {
                val model = WelcomeViewPagerModelRepository.list[index]

                list.add(
                    WelcomeFragment.getInstance(
                        model.title, model.description, model.image,
                        index == WelcomeViewPagerModelRepository.list.size - 1
                    )
                )
            }
            viewPager.adapter = RegistrationViewPagerAdapter(list, childFragmentManager, lifecycle)
            dotsIndicator.attachTo(viewPager)
        }
    }
}
