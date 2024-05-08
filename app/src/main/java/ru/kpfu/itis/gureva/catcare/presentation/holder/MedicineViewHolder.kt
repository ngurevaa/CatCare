package ru.kpfu.itis.gureva.catcare.presentation.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.gureva.catcare.data.database.entity.MedicineEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDateDescBinding

class MedicineViewHolder(
    private val binding: ItemDateDescBinding
) : ViewHolder(binding.root) {

    fun onBind(medicineEntity: MedicineEntity) {
        binding.run {
            tvDate.text = medicineEntity.date
            tvDescription.text = medicineEntity.description
        }
    }
}
