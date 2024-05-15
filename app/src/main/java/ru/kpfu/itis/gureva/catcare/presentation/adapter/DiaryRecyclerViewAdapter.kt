package ru.kpfu.itis.gureva.catcare.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.gureva.catcare.data.database.entity.DiaryEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.MedicineEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDateDescBinding
import ru.kpfu.itis.gureva.catcare.databinding.ItemDiaryBinding
import ru.kpfu.itis.gureva.catcare.presentation.holder.DiaryViewHolder
import ru.kpfu.itis.gureva.catcare.presentation.holder.MedicineViewHolder

class DiaryRecyclerViewAdapter(
    private val glide: RequestManager,
    private val onDeleteClicked: (Int) -> Unit,
    private val context: Context
) : ListAdapter<DiaryEntity, DiaryViewHolder>(
    object : DiffUtil.ItemCallback<DiaryEntity>() {
        override fun areItemsTheSame(oldItem: DiaryEntity, newItem: DiaryEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DiaryEntity, newItem: DiaryEntity
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        return DiaryViewHolder(
            ItemDiaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide,
            onDeleteClicked,
            context
        )
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
