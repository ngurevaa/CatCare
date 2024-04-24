package ru.kpfu.itis.gureva.catcare.presentation.holder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.gureva.catcare.databinding.ItemAddBinding

class AddViewHolder(
    private val binding: ItemAddBinding,
    private val onAddClicked: () -> Unit
) : ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onAddClicked()
        }
    }
}
