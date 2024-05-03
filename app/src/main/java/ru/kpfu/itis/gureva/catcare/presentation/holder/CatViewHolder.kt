package ru.kpfu.itis.gureva.catcare.presentation.holder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemCatBinding

class CatViewHolder(
    private val binding: ItemCatBinding,
    private val glide: RequestManager,
    private val onPetClicked: (Int) -> Unit
) : ViewHolder(binding.root) {
    private var pet: PetEntity? = null

    init {
        binding.root.setOnClickListener {
            pet?.id?.let { id -> onPetClicked(id) }
        }
    }

    fun onBind(pet: PetEntity) {
        this.pet = pet

        binding.run {
            tvName.text = pet.name
            tvBreed.text = pet.breed
            glide
                .load(pet.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCat)
        }
    }
}
