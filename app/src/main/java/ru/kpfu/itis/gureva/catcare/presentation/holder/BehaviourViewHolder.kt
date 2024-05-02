package ru.kpfu.itis.gureva.catcare.presentation.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemBehaviourBinding
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager

class BehaviourViewHolder(
    private val binding: ItemBehaviourBinding,
    private val resourceManager: ResourceManager,
    private val glide: RequestManager
) : ViewHolder(binding.root) {
    private var open = false

    init {
        binding.run {
            ivOpen.setOnClickListener {
                if (open) {
                    glide.load(R.drawable.down).into(ivOpen)

                    tvTitleDate.visibility = View.GONE
                    tvDate.visibility = View.GONE
                    tvTitleDescription.visibility = View.GONE
                    tvDescription.visibility = View.GONE
                    open = false
                }
                else {
                    glide.load(R.drawable.up).into(ivOpen)

                    tvTitleDate.visibility = View.VISIBLE
                    tvDate.visibility = View.VISIBLE

                    if (tvDescription.text.isNotEmpty()) {
                        tvTitleDescription.visibility = View.VISIBLE
                        tvDescription.visibility =View.VISIBLE
                    }
                    open = true
                }
            }
        }
    }

    fun onBind(behaviourEntity: BehaviourEntity) {
        binding.run {
            tvBehaviour.text = resourceManager.getString(behaviourEntity.behaviour.description)
            glide.load(behaviourEntity.behaviour.imageId).into(ivBehaviour)
            tvDate.text = behaviourEntity.date
            tvDescription.text = behaviourEntity.description
        }
    }
}
