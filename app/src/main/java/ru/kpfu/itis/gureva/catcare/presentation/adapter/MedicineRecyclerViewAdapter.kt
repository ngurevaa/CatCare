package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.kpfu.itis.gureva.catcare.data.database.entity.MedicineEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VetEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDateDescBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.MedicineViewHolder
import ru.kpfu.itis.gureva.catcare.presentation.holder.VaccinationViewHolder
import ru.kpfu.itis.gureva.catcare.presentation.holder.VetViewHolder

class MedicineRecyclerViewAdapter : ListAdapter<MedicineEntity, MedicineViewHolder>(
    object : DiffUtil.ItemCallback<MedicineEntity>() {
        override fun areItemsTheSame(oldItem: MedicineEntity, newItem: MedicineEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MedicineEntity, newItem: MedicineEntity
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        return MedicineViewHolder(
            ItemDateDescBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
