package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemAddBinding
import ru.kpfu.itis.gureva.catcare.databinding.ItemCatBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.CatViewHolder
import ru.kpfu.itis.gureva.catcare.presentation.holder.AddViewHolder

class MyPetsRecyclerViewAdapter(
    private var list: List<PetEntity>,
    private val glide: RequestManager,
    private val onPetClicked: (Int) -> Unit,
    private val onAddClicked: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateList(list: List<PetEntity>) {
        this.list = list
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            list.size -> 1
            else -> 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> AddViewHolder(
                ItemAddBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onAddClicked
            )
            2 -> CatViewHolder(
                ItemCatBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                glide,
                onPetClicked
            )
            else -> throw IllegalArgumentException("Unknown holder")
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            1 -> (holder as AddViewHolder).onBind()
            2 -> (holder as CatViewHolder).onBind(list[position])
        }
    }
}
