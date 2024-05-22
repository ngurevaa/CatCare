package ru.kpfu.itis.gureva.catcare.presentation.screens.helpful

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.snackbar.Snackbar
import dagger.Lazy
import retrofit2.HttpException
import ru.kpfu.itis.gureva.catcare.BuildConfig
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.databinding.FragmentCatFactBinding
import ru.kpfu.itis.gureva.catcare.di.appComponent
import ru.kpfu.itis.gureva.catcare.utils.observe
import javax.inject.Inject

class CatFactFragment : Fragment(R.layout.fragment_cat_fact) {
    private var binding: FragmentCatFactBinding? = null

    @Inject
    internal lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    private val viewModel: CatFactViewModel by viewModels {
        viewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCatFactBinding.bind(view)

        observerData()
        viewModel.getFact()

        binding?.run {
            btnAnotherFact.setOnClickListener {
                progressIndicator.visibility = View.VISIBLE
                viewModel.getFact()
            }
        }
    }

    private fun observerData() {
        with(viewModel) {
            currentCatFactFlow.observe(this@CatFactFragment) {catFactUIModel ->
                catFactUIModel?.let {
                    binding?.run {
                        tvFact.text = it.fact
                        downloadImage(BuildConfig.CAT_GIF_BASE_URL)
                    }
                }
            }

            errorFlow.observe(this@CatFactFragment) { error ->
                error?.let {
                    when (error) {
                        is HttpException -> {
                            downloadImage(BuildConfig.CAT_ERROR_BASE_URL + error.code())
                        }
                        else -> {
                            binding?.run {
                                progressIndicator.visibility = View.INVISIBLE
                                Snackbar.make(root, getString(R.string.internet_connection_error), Snackbar.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun downloadImage(url: String) {
        binding?.run {
            Glide.with(requireContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(ObjectKey(System.currentTimeMillis()))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean
                    ): Boolean {
                        progressIndicator.visibility = View.INVISIBLE
                        return false
                    }
                })
                .into(ivCat)
        }
    }
}
