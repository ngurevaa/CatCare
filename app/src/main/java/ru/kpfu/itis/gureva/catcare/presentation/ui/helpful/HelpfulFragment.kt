package ru.kpfu.itis.gureva.catcare.presentation.ui.helpful

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentHelpfulBinding

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
