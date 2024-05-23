package ru.kpfu.itis.gureva.catcare.presentation.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.gureva.catcare.data.database.entity.TreatmentEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDateDescBinding

class TreatmentViewHolder(
    private val binding: ItemDateDescBinding
) : ViewHolder(binding.root) {

    fun onBind(treatmentEntity: TreatmentEntity) {
        binding.run {
            tvDate.text = treatmentEntity.date
            tvDescription.text = treatmentEntity.description
        }
    }
}
