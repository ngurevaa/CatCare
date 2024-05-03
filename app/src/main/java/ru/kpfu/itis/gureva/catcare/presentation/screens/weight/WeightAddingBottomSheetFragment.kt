package ru.kpfu.itis.gureva.catcare.presentation.screens.weight

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentWeightAddingBottomSheetBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.utils.DatePicker
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import java.text.SimpleDateFormat
import java.util.Date

class WeightAddingBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_weight_adding_bottom_sheet) {
    private var binding: FragmentWeightAddingBottomSheetBinding? = null

    private var petId: Int? = null

    private val viewModel: WeightAddingViewModel by lazyViewModel {
        requireContext().appComponent.getWeightAddingViewModel().create(petId ?: 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeightAddingBottomSheetBinding.bind(view)

        petId = arguments?.getInt(ARG_ID)

        binding?.run {
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
                viewModel.save(etWeight.text.toString(), etDate.text.toString())
            }

            viewModel.error.observe(viewLifecycleOwner) {
                layoutWeight.error = it
                if (it == null) {
                    dismiss()
                }
            }
        }
    }
    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(id: Int) = WeightAddingBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
