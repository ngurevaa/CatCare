package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemBehaviourBinding
import ru.kpfu.itis.gureva.catcare.databinding.ItemWeightBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.BehaviourViewHolder
import ru.kpfu.itis.gureva.catcare.presentation.holder.WeightViewHolder
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager

class UnusualBehaviourRecyclerViewAdapter(
    private val resourceManager: ResourceManager,
    private val glide: RequestManager
) : ListAdapter<BehaviourEntity, BehaviourViewHolder>(
    object : DiffUtil.ItemCallback<BehaviourEntity>() {
        override fun areItemsTheSame(oldItem: BehaviourEntity, newItem: BehaviourEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BehaviourEntity, newItem: BehaviourEntity
        ): Boolean = oldItem == newItem
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BehaviourViewHolder {
        return BehaviourViewHolder(
            ItemBehaviourBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            resourceManager,
            glide
        )
    }

    override fun onBindViewHolder(holder: BehaviourViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
