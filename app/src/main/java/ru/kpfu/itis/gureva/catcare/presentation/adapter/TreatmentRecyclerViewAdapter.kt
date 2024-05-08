package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.kpfu.itis.gureva.catcare.data.database.entity.TreatmentEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDateDescBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.TreatmentViewHolder
import ru.kpfu.itis.gureva.catcare.presentation.holder.VaccinationViewHolder

class TreatmentRecyclerViewAdapter : ListAdapter<TreatmentEntity, TreatmentViewHolder>(
    object : DiffUtil.ItemCallback<TreatmentEntity>() {
        override fun areItemsTheSame(oldItem: TreatmentEntity, newItem: TreatmentEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TreatmentEntity, newItem: TreatmentEntity
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreatmentViewHolder {
        return TreatmentViewHolder(
            ItemDateDescBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TreatmentViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
