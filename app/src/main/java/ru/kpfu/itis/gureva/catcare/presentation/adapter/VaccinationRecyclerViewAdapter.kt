package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDateDescBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.VaccinationViewHolder

class VaccinationRecyclerViewAdapter : ListAdapter<VaccinationEntity, VaccinationViewHolder>(
    object : DiffUtil.ItemCallback<VaccinationEntity>() {
        override fun areItemsTheSame(oldItem: VaccinationEntity, newItem: VaccinationEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: VaccinationEntity, newItem: VaccinationEntity
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccinationViewHolder {
        return VaccinationViewHolder(
            ItemDateDescBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VaccinationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
