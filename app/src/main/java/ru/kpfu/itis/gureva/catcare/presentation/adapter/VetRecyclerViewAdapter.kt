package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VetEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDateDescBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.VaccinationViewHolder
import ru.kpfu.itis.gureva.catcare.presentation.holder.VetViewHolder

class VetRecyclerViewAdapter : ListAdapter<VetEntity, VetViewHolder>(
    object : DiffUtil.ItemCallback<VetEntity>() {
        override fun areItemsTheSame(oldItem: VetEntity, newItem: VetEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: VetEntity, newItem: VetEntity
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VetViewHolder {
        return VetViewHolder(
            ItemDateDescBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VetViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
