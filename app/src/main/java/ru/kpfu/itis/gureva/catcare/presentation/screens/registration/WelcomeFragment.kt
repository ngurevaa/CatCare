package ru.kpfu.itis.gureva.catcare.presentation.screens.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentWelcomeBinding
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileEditingFragment

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    private var binding: FragmentWelcomeBinding? = null

    private val fragmentContainerId = R.id.main_container

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWelcomeBinding.bind(view)

        binding?.run {
            tvTitle.text = arguments?.getString(ARG_TITLE)
            tvDescription.text = arguments?.getString(ARG_DESCRIPTION)
            Glide.with(requireContext())
                .load(arguments?.getInt(ARG_IMAGE))
                .into(ivImage)

            if (arguments?.getBoolean(ARG_LAST) == true) {
                btnStart.visibility = View.VISIBLE

                btnStart.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(fragmentContainerId, PetProfileEditingFragment())
                        .commit()
                }
            }
        }
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_DESCRIPTION = "arg_description"
        private const val ARG_IMAGE = "arg_image"
        private const val ARG_LAST = "arg_last"

        fun getInstance(title: String, description: String, image: Int, last: Boolean) = WelcomeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_DESCRIPTION, description)
                putInt(ARG_IMAGE, image)
                putBoolean(ARG_LAST, last)
            }
        }
    }
}
