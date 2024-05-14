package ru.kpfu.itis.gureva.catcare.presentation.screens.vet.adding

import android.os.Bundle
import android.text.InputType
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentAddingBottomSheetBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseAddingFragment
import ru.kpfu.itis.gureva.catcare.utils.DatePicker
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import java.text.SimpleDateFormat
import java.util.Date

class VetAddingBottomSheetFragment : BaseAddingFragment(R.layout.fragment_adding_bottom_sheet) {
    private var binding: FragmentAddingBottomSheetBinding? = null

    override val viewModel: VetAddingViewModel by lazyViewModel {
        requireContext().appComponent.getVetAddingViewModel().create(petId ?: 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddingBottomSheetBinding.bind(view)

        binding?.run {
            tvAddComment.text = getString(R.string.vet_adding_hint)
            etDate.inputType = InputType.TYPE_NULL
            etDate.setText(SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date()))

            etDate.setOnClickListener {
                val datePicker = DatePicker.getDatePicker(Date())

                datePicker.addOnPositiveButtonClickListener {
                    etDate.setText(SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date(it)))
                }
                datePicker.show(parentFragmentManager, null)
            }

            btnAdd.setOnClickListener {
                if (etDescription.text.isNullOrBlank()) {
                    layoutDescription.error = getString(R.string.error_field_must_not_be_empty)
                }
                else {
                    viewModel.save(etDescription.text.toString(), etDate.text.toString())
                    dismiss()
                }
            }
        }
    }

    companion object {
        fun newInstance(id: Int) = VetAddingBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
