package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemWeightBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.WeightViewHolder

class WeightControlRecyclerViewAdapter(
    private var list: List<WeightEntity>
) : RecyclerView.Adapter<WeightViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        return WeightViewHolder(
            ItemWeightBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    fun updateList(list: List<WeightEntity>) {
        this.list = list
        notifyDataSetChanged()
    }
}
