package ru.kpfu.itis.gureva.catcare.presentation.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemWeightBinding

class WeightViewHolder(
    private val binding: ItemWeightBinding
) : ViewHolder(binding.root) {

    fun onBind(weightEntity: WeightEntity) {
        binding.run {
            tvDate.text = weightEntity.date
            tvWeight.text = weightEntity.weight.toString()
        }
    }
}
