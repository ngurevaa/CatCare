package ru.kpfu.itis.gureva.catcare.presentation.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.gureva.catcare.databinding.ItemDiaryBinding
import ru.kpfu.itis.gureva.catcare.presentation.model.NewsUIModel

class NewsViewHolder(
    private val binding: ItemDiaryBinding,
    private val glide: RequestManager
) : ViewHolder(binding.root) {

    fun onBind(newsUIModel: NewsUIModel) {
        binding.run {
            tvDescription.text = newsUIModel.title
            tvDate.text = newsUIModel.description
            btnOptions.visibility = View.GONE
            glide.load(newsUIModel.image).into(ivImage)
        }
    }
}
