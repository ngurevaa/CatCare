package ru.kpfu.itis.gureva.catcare.presentation.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDateDescBinding

class VaccinationViewHolder(
    private val binding: ItemDateDescBinding
) : ViewHolder(binding.root) {

    fun onBind(vaccinationEntity: VaccinationEntity) {
        binding.run {
            tvDate.text = vaccinationEntity.date
            tvDescription.text = vaccinationEntity.description
        }
    }
}
