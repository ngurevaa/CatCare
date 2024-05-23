package ru.kpfu.itis.gureva.catcare.presentation.screens.weight.adding

import android.os.Bundle
import android.text.InputType
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentWeightAddingBottomSheetBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseAddingFragment
import ru.kpfu.itis.gureva.catcare.utils.DatePicker
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.lazyViewModel
import java.text.SimpleDateFormat
import java.util.Date

class WeightAddingBottomSheetFragment : BaseAddingFragment(R.layout.fragment_weight_adding_bottom_sheet) {
    private var binding: FragmentWeightAddingBottomSheetBinding? = null

    override val viewModel: WeightAddingViewModel by lazyViewModel {
        requireContext().appComponent.getWeightAddingViewModel().create(petId ?: 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeightAddingBottomSheetBinding.bind(view)

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
        fun newInstance(id: Int) = WeightAddingBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
            }
        }
    }
}
