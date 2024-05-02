package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemBehaviourBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.BehaviourViewHolder
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager

class UnusualBehaviourRecyclerViewAdapter(
    private var list: List<BehaviourEntity>,
    private val resourceManager: ResourceManager,
    private val glide: RequestManager
) : RecyclerView.Adapter<BehaviourViewHolder>() {
    fun updateList(list: List<BehaviourEntity>) {
        this.list = list
        notifyDataSetChanged()
    }
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

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BehaviourViewHolder, position: Int) {
        holder.onBind(list[position])
    }
}
