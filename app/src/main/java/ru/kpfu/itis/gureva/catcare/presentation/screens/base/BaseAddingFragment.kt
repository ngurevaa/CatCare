package ru.kpfu.itis.gureva.catcare.presentation.screens.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kpfu.itis.gureva.catcare.R

abstract class BaseAddingFragment(@LayoutRes layout: Int) : BottomSheetDialogFragment(layout) {
    protected var petId: Int? = null

    protected abstract val viewModel: BaseAddingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        petId = arguments?.getInt(ARG_ID)

        if (dialog is BottomSheetDialog) {
            val behaviour = (dialog as BottomSheetDialog).behavior
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    companion object {
        const val ARG_ID = "arg_id"
    }
}
