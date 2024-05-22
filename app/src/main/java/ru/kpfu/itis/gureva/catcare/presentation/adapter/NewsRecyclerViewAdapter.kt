package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.gureva.catcare.databinding.ItemDiaryBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.NewsViewHolder
import ru.kpfu.itis.gureva.catcare.presentation.model.NewsUIModel

class NewsRecyclerViewAdapter(
    private val glide: RequestManager
) : ListAdapter<NewsUIModel, NewsViewHolder>(
    object : DiffUtil.ItemCallback<NewsUIModel>() {
    override fun areItemsTheSame(oldItem: NewsUIModel, newItem: NewsUIModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NewsUIModel, newItem: NewsUIModel
    ): Boolean = oldItem == newItem
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemDiaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
