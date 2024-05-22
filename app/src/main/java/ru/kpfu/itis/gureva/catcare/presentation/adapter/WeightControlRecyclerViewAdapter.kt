package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.gureva.catcare.data.database.entity.VetEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDateDescBinding
import ru.kpfu.itis.gureva.catcare.databinding.ItemWeightBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.VetViewHolder
import ru.kpfu.itis.gureva.catcare.presentation.holder.WeightViewHolder

class WeightControlRecyclerViewAdapter : ListAdapter<WeightEntity, WeightViewHolder>(
    object : DiffUtil.ItemCallback<WeightEntity>() {
        override fun areItemsTheSame(oldItem: WeightEntity, newItem: WeightEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: WeightEntity, newItem: WeightEntity
        ): Boolean = oldItem == newItem
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        return WeightViewHolder(
            ItemWeightBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
