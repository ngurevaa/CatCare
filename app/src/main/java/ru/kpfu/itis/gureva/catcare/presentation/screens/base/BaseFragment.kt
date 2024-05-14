package ru.kpfu.itis.gureva.catcare.presentation.screens.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import javax.inject.Inject

abstract class BaseFragment : Fragment(R.layout.fragment_note) {
    protected var petId: Int? = null

    @Inject
    lateinit var resourceManager: ResourceManager

    protected abstract val viewModel: BaseViewModel

    private var snackbar: Snackbar? = null

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    protected open fun onItemDelete(position: Int) {
        viewModel.removeItem(position)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        petId = arguments?.getInt(ARG_ID)
    }

    protected fun showItemRemovedSnackbar(view: View) {
        snackbar = Snackbar.make(view, getString(R.string.note_deleted), Snackbar.LENGTH_LONG)
            .setAction(R.string.cancel) {
                viewModel.returnItem()
            }
        snackbar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        snackbar?.dismiss()
    }

    companion object {
        const val ARG_ID = "arg_id"
    }
}
