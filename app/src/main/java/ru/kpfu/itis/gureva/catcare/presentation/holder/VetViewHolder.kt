package ru.kpfu.itis.gureva.catcare.presentation.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VetEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDateDescBinding

class VetViewHolder(
    private val binding: ItemDateDescBinding
) : ViewHolder(binding.root) {

    fun onBind(vetEntity: VetEntity) {
        binding.run {
            tvDate.text = vetEntity.date
            tvDescription.text = vetEntity.description
        }
    }
}
