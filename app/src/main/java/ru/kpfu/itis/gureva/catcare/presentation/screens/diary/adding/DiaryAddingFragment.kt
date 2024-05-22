package ru.kpfu.itis.gureva.catcare.presentation.screens.diary.adding

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.Lazy
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.databinding.FragmentDiaryAddingBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.MainActivity
import ru.kpfu.itis.gureva.catcare.presentation.screens.diary.DiaryFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.diary.DiaryViewModel
import ru.kpfu.itis.gureva.catcare.utils.DownloadStatus
import ru.kpfu.itis.gureva.catcare.utils.SavingStatus
import ru.kpfu.itis.gureva.catcare.utils.setMaxLines
import javax.inject.Inject

class DiaryAddingFragment : Fragment(R.layout.fragment_diary_adding) {
    private var binding: FragmentDiaryAddingBinding? = null

    @Inject
    internal lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    private val viewModel: DiaryAddingViewModel by viewModels {
        viewModelFactory.get()
    }

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
        binding = FragmentDiaryAddingBinding.bind(view)

        binding?.run {
            btnSave.setOnClickListener {
                viewModel.saveNote()
            }

            etDescription.doOnTextChanged { text, start, before, count ->
                viewModel.setDescription(text.toString())
            }

            ivCat.setOnClickListener {
                Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }.also {
                    resultLauncher.launch(it)
                }
            }
        }

        observeDiaryState()
        observeDownloadStatus()
        observeSavingStatus()
    }

    private fun observeDownloadStatus() {
        binding?.run {
            viewModel.downloadStatus.observe(viewLifecycleOwner) { status ->
                when (status) {
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

    private fun observeSavingStatus() {
        viewModel.savingStatus.observe(viewLifecycleOwner) {
            when (it) {
                SavingStatus.OK -> {
                    MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
                        .setCancelable(false)
                        .setTitle(getString(R.string.note_adding_successful))
                        .setPositiveButton(getString(R.string.btn_ok)) {_, _ ->
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.main_container, DiaryFragment())
                                .commit()
                        }
                        .show()
                }
                SavingStatus.ERROR -> {
                    binding?.let { b -> Snackbar.make(b.root, getString(R.string.profile_saving_failed), Snackbar.LENGTH_LONG).show() }
                }
            }

        }
    }

    private fun observeDiaryState() {
        binding?.run {
            viewModel.diaryState.observe(viewLifecycleOwner) {
                if (etDescription.text.toString() != it.description) {
                    etDescription.setText(it.description)
                }

                if (layoutDescription.error != it.error) {
                    layoutDescription.error = it.error
                }

                if (viewModel.downloadStatus.value != DownloadStatus.OK && viewModel.downloadStatus.value != DownloadStatus.ERROR) {
                    it.image?.let { img -> uploadImage(img) }
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
