package ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.adding

import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.base.Constants
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity
import ru.kpfu.itis.gureva.catcare.databinding.FragmentBehaviourAddingBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.UnusualBehaviourFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.UnusualBehaviourViewModel
import ru.kpfu.itis.gureva.catcare.utils.Behaviour
import ru.kpfu.itis.gureva.catcare.utils.DatePicker
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import java.text.SimpleDateFormat
import java.util.Date

class BehaviourAddingFragment : Fragment(R.layout.fragment_behaviour_adding) {
    private var binding: FragmentBehaviourAddingBinding? = null

    private var petId: Int? = null

    private var fontRegular: Typeface? = null

    private var fontSemiBold: Typeface? = null

    private var selectedItem: Behaviour? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBehaviourAddingBinding.bind(view)

        petId = arguments?.getInt(ARG_ID)

        fontRegular = Typeface.createFromAsset(requireContext().assets, Constants.FONT_MONTSERRAT_MEDIUM)
        fontSemiBold = Typeface.createFromAsset(requireContext().assets, Constants.FONT_MONTSERRAT_SEMI_BOLD)

        binding?.run {
            layoutActive.setOnClickListener {
                onImageClicked(Behaviour.ACTIVE)
            }

            layoutAngry.setOnClickListener {
                onImageClicked(Behaviour.ANGRY)
            }

            layoutFearful.setOnClickListener {
                onImageClicked(Behaviour.FEARFUL)
            }

            layoutSad.setOnClickListener {
                onImageClicked(Behaviour.SAD)
            }

            layoutSleepy.setOnClickListener {
                onImageClicked(Behaviour.SLEEPY)
            }

            layoutTender.setOnClickListener {
                onImageClicked(Behaviour.TENDER)
            }
        }
    }

    private fun onImageClicked(behaviour: Behaviour) {
        selectedItem?.let { item -> setFontRegular(item) }
        selectedItem = behaviour
        selectedItem?.let {
            item -> setFontSemiBold(item)
            BehaviourAddingBottomSheetFragment.newInstance(petId ?: 1, item).show(parentFragmentManager, null)
        }
    }

    private fun setFontRegular(item: Behaviour) {
        binding?.run {
            when (item) {
                Behaviour.ACTIVE -> tvActive.typeface = fontRegular
                Behaviour.ANGRY -> tvAngry.typeface = fontRegular
                Behaviour.FEARFUL -> tvFearful.typeface = fontRegular
                Behaviour.SAD -> tvSad.typeface = fontRegular
                Behaviour.SLEEPY -> tvSleepy.typeface = fontRegular
                Behaviour.TENDER -> tvTender.typeface = fontRegular
            }
        }
    }

    private fun setFontSemiBold(item: Behaviour) {
        binding?.run {
            when (item) {
                Behaviour.ACTIVE -> tvActive.typeface = fontSemiBold
                Behaviour.ANGRY -> tvAngry.typeface = fontSemiBold
                Behaviour.FEARFUL -> tvFearful.typeface = fontSemiBold
                Behaviour.SAD -> tvSad.typeface = fontSemiBold
                Behaviour.SLEEPY -> tvSleepy.typeface = fontSemiBold
                Behaviour.TENDER -> tvTender.typeface = fontSemiBold
            }
        }
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(id: Int) = BehaviourAddingFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
