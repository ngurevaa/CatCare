package ru.kpfu.itis.gureva.catcare.presentation.ui.registration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.PetRepository
import ru.kpfu.itis.gureva.catcare.databinding.FragmentRegistrationBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.MainActivity
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private var binding: FragmentRegistrationBinding? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var petRepository: PetRepository

    private var date: String? = null

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)

        binding?.run {
            init()

            etBirthDay.setOnClickListener {
                val datePicker = getDatePicker()
                datePicker.addOnPositiveButtonClickListener {
                    date = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date(it))
                    binding?.etBirthDay?.setText(date)
                }
                datePicker.show(parentFragmentManager, null)
            }

            btnSave.setOnClickListener {
                if (checkFields()) {
                    lifecycleScope.launch {
                        petRepository.save(PetEntity(1, etName.text.toString(),
                            etBirthDay.text.toString(), etBreed.text.toString(), etGender.text.toString()))
                    }

                    sharedPreferences.edit {
                        putBoolean(Keys.REGISTRATION_KEY, true)
                    }

                    val intent = Intent()
                    intent.setClass(requireActivity(), MainActivity::class.java)
                    activity?.startActivity(intent)
                }
            }
        }
    }

    private fun init() {
        binding?.run {
            lifecycleScope.launch {
                val pet = petRepository.getById(1)
                if (pet != null) {
                    etName.setText(pet.name)
                    etBreed.setText(pet.breed)
                    etGender.setText(pet.gender)
                    etBirthDay.setText(pet.birthDay)
                    date = pet.birthDay
                }
                else {
                    date = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date())
                    etBirthDay.setText(date)
                }
            }
        }
    }

    private fun checkFields(): Boolean {
        binding?.run {
            layoutGender.error = null
            layoutBreed.error = null
            layoutName.error = null

            if (etName.text.isNullOrEmpty()) {
                layoutName.error = getString(R.string.error_field_must_not_be_empty)
                return false
            }
            else if (etGender.text.isNullOrEmpty()) {
                layoutGender.error = getString(R.string.error_field_must_not_be_empty)
                return false
            }
            else if (etBreed.text.isNullOrEmpty()) {
                layoutBreed.error = getString(R.string.error_field_must_not_be_empty)
                return false
            }
        }
        return true
    }

    private fun getDatePicker(): MaterialDatePicker<Long> {
        val dateValidator: CalendarConstraints.DateValidator = DateValidatorPointBackward.now()
        val selection = if (date != null) SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).parse(date).time
            else MaterialDatePicker.todayInUtcMilliseconds()

        return MaterialDatePicker.Builder.datePicker()
            .setSelection(selection)
            .setCalendarConstraints(
                CalendarConstraints.Builder().setValidator(dateValidator).build()
            )
            .build()
    }
}
