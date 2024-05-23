package ru.kpfu.itis.gureva.catcare.presentation.screens.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.Lazy
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.databinding.FragmentPetProfileEditingBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.MainActivity
import ru.kpfu.itis.gureva.catcare.utils.DatePicker
import ru.kpfu.itis.gureva.catcare.utils.DownloadStatus
import ru.kpfu.itis.gureva.catcare.utils.FieldError
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.SavingStatus
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import ru.kpfu.itis.gureva.catcare.utils.setMaxLines
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class PetProfileEditingFragment : Fragment(R.layout.fragment_pet_profile_editing) {
    private var binding: FragmentPetProfileEditingBinding? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel: PetProfileEditingViewModel by lazyViewModel {
        requireContext().appComponent.getPetProfileEditingViewModel().create(petId)
    }

    private var petId: Int? = null

    private var alertDialog: AlertDialog? = null

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            uploadImage(imageUri.toString())
            viewModel.setImage(imageUri.toString())
        }
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPetProfileEditingBinding.bind(view)

        petId = arguments?.getInt(ARG_ID)

        observeEditTexts()
        initTextChangeListeners()
        binding?.run {
            btnSave.setOnClickListener {
                viewModel.savePet()
            }

            etBirthDay.setOnClickListener {
                val datePicker = if (viewModel.petState.value?.birthDay == null) {
                    DatePicker.getDatePicker(Date())
                } else {
                    DatePicker.getDatePicker(
                        Date(SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).parse(viewModel.petState.value?.birthDay).time
                                + DatePicker.MILLISECONDS_PER_DAY)
                    )
                }

                datePicker.addOnPositiveButtonClickListener {
                    viewModel.setBirthDay(SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date(it)))
                }
                datePicker.show(parentFragmentManager, null)
            }

            ivCat.setOnClickListener {
                Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }.also {
                    resultLauncher.launch(it)
                }
            }
        }
    }

    private fun observeEditTexts() {
        binding?.run {
            viewModel.petState.observe(viewLifecycleOwner) {
                if (etName.text.toString() != it.name) {
                    etName.setText(it.name)
                }

                if (etBreed.text.toString() != it.breed) {
                    etBreed.setText(it.breed)
                }

                if (etGender.text.toString() != it.gender) {
                    etGender.setText(it.gender, false)
                }

                if (etBirthDay.text.toString() != it.birthDay) {
                    etBirthDay.setText(it.birthDay)
                }

                if (viewModel.downloadStatus.value != DownloadStatus.OK) {
                    it?.let { uploadImage(it.image.toString()) }
                }
            }

            viewModel.fieldError.observe(viewLifecycleOwner) {
                layoutName.error = null
                layoutBreed.error = null
                layoutGender.error = null
                when (it) {
                    FieldError.NAME -> {
                        layoutName.error = getString(R.string.error_field_must_not_be_empty)
                    }
                    FieldError.BREED -> {
                        layoutBreed.error = getString(R.string.error_field_must_not_be_empty)
                    }
                    FieldError.GENDER -> {
                        layoutGender.error = getString(R.string.error_field_must_not_be_empty)
                    }
                    FieldError.NONE -> {}
                }
            }

            viewModel.savingStatus.observe(viewLifecycleOwner) {
                when (it) {
                    SavingStatus.OK -> {
                        showDialogSavingProfile()

                        if (!sharedPreferences.getBoolean(Keys.REGISTRATION_KEY, false)) {
                            sharedPreferences.edit {
                                putBoolean(Keys.REGISTRATION_KEY, true)
                            }
                        }
                    }
                    SavingStatus.ERROR -> {
                        Snackbar.make(root, getString(R.string.profile_saving_failed), Snackbar.LENGTH_LONG).show()
                    }
                }

            }

            viewModel.downloadStatus.observe(viewLifecycleOwner) {
                when (it) {
                    DownloadStatus.OK -> {
                        alertDialog?.dismiss()
                    }
                    DownloadStatus.ERROR -> {
                        alertDialog?.dismiss()
                        showSnackbar(getString(R.string.download_failed))
                    }
                    DownloadStatus.EXECUTION -> {
                        alertDialog = MaterialAlertDialogBuilder(requireContext())
                            .setView(requireActivity().layoutInflater.inflate(R.layout.dialog_progress, null))
                            .setCancelable(false)
                            .show()
                    }
                    DownloadStatus.LONG_EXECUTION -> {
                        showSnackbar(getString(R.string.long_download))
                    }
                }
            }
        }
    }

    private fun showSnackbar(text: String) {
        binding?.let {
            val snackbar = Snackbar.make(it.root, text, Snackbar.LENGTH_LONG)
            snackbar.setMaxLines(3)
            snackbar.show()
        }
    }

    private fun showDialogSavingProfile() {
        MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setCancelable(false)
            .setTitle(getString(R.string.profile_saving_successful))
            .setPositiveButton(getString(R.string.btn_ok)) {_, _ ->
                Intent().apply {
                    setClass(requireActivity(), MainActivity::class.java)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }.also {
                    requireActivity().startActivity(it)
                }
            }
            .show()
    }

    private fun initTextChangeListeners() {
        binding?.run {
            etName.doOnTextChanged { text, start, before, count ->
                viewModel.setName(text?.trim().toString())
            }

            etBreed.doOnTextChanged { text, start, before, count ->
                viewModel.setBreed(text?.trim().toString())
            }

            etGender.doOnTextChanged { text, start, before, count ->
                viewModel.setGender(text?.trim().toString())
            }

            etBirthDay.doOnTextChanged { text, start, before, count ->
                viewModel.setBirthDay(text?.trim().toString())
            }
        }
    }

    private fun uploadImage(uri: String) {
        binding?.run {
            Glide.with(requireContext())
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean
                    ): Boolean {
                        binding?.placeholder?.visibility = View.INVISIBLE
                        return false
                    }
                })
                .into(ivCat)
        }
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(id: Int) = PetProfileEditingFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
