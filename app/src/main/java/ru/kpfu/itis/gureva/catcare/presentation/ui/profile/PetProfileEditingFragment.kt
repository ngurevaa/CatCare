package ru.kpfu.itis.gureva.catcare.presentation.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import dagger.Lazy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.PetRepository
import ru.kpfu.itis.gureva.catcare.databinding.FragmentPetProfileEditingBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.MainActivity
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.HelpfulViewModel
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class PetProfileEditingFragment : Fragment(R.layout.fragment_pet_profile_editing) {
    private var binding: FragmentPetProfileEditingBinding? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    internal lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    private val viewModel: PetProfileViewModel by viewModels {
        viewModelFactory.get()
    }

    private val storageRef = Firebase.storage.reference

    private var pet: PetEntity? = null

    private var editPhoto = false

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            editPhoto = true
            val imageUri = result.data?.data
            uploadImage(imageUri.toString())
            pet?.image = imageUri.toString()
        }
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPetProfileEditingBinding.bind(view)

        binding?.run {
            init()

            etBirthDay.setOnClickListener {
                val datePicker = getDatePicker()
                datePicker.addOnPositiveButtonClickListener {
                    pet?.birthDay = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date(it))
                    binding?.etBirthDay?.setText(pet?.birthDay)
                }
                datePicker.show(parentFragmentManager, null)
            }

            btnSave.setOnClickListener {
                savePet()
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

    private fun savePet() {
        if (checkFields()) {
            binding?.run {
                pet?.name = etName.text.toString()
                pet?.breed = etBreed.text.toString()
                pet?.gender = etGender.text.toString()

                if (editPhoto) { savePhoto() }

                if (sharedPreferences.getBoolean(Keys.REGISTRATION_KEY, false)) {
                    pet?.let { viewModel.updatePet(it) }

                    sharedPreferences.edit {
                        putBoolean(Keys.REGISTRATION_KEY, true)
                    }
                }
                else {
                    pet?.let { viewModel.savePet(it) }
                }
            }

            Intent().apply {
                setClass(requireActivity(), MainActivity::class.java)
            }.also {
                requireActivity().startActivity(it)
            }
        }
    }

    private fun savePhoto() {
        val fileName = UUID.randomUUID()
        val uploadTask = storageRef.child("$fileName").putFile(Uri.parse(pet?.image))

        uploadTask.addOnSuccessListener {
            storageRef.child("$fileName").downloadUrl.addOnSuccessListener {
                pet?.image = it.toString()
                // надо скачивать картинку на сервер только если пользователь сохраняет ее
            }.addOnFailureListener {
                binding?.let { Snackbar.make(it.root, getString(R.string.internet_connection_error), Snackbar.LENGTH_LONG).show() }
            }
        }.addOnFailureListener {
            binding?.let { Snackbar.make(it.root, getString(R.string.internet_connection_error), Snackbar.LENGTH_LONG).show() }
        }
    }

    private fun init() {
        binding?.run {
            lifecycleScope.launch {
                pet = viewModel.getPet().firstOrNull()

                if (pet != null) {
                    etName.setText(pet?.name)
                    etBreed.setText(pet?.breed)
                    etGender.setText(pet?.gender)
                    etBirthDay.setText(pet?.birthDay)

                    if (pet?.image != null) {
                        uploadImage(pet?.image.toString())
                    }
                }
                else {
                    pet = PetEntity(1, "", "", "", "", null)
                    pet?.birthDay = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date())
                    etBirthDay.setText(pet?.birthDay)
                }
            }
        }
    }

    private fun checkFields(): Boolean {
        binding?.run {
            layoutGender.error = null
            layoutBreed.error = null
            layoutName.error = null

            // добавить проверки на длину
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

        return MaterialDatePicker.Builder.datePicker()
            .setSelection(SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).parse(pet?.birthDay).time)
            .setCalendarConstraints(
                CalendarConstraints.Builder().setValidator(dateValidator).build()
            )
            .build()
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
}
