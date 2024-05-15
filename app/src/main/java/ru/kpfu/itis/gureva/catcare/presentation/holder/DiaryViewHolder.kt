package ru.kpfu.itis.gureva.catcare.presentation.holder

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.data.database.entity.DiaryEntity
import ru.kpfu.itis.gureva.catcare.databinding.ItemDiaryBinding

class DiaryViewHolder(
    private val binding: ItemDiaryBinding,
    private val glide: RequestManager,
    private val onDeleteClicked: (Int) -> Unit,
    context: Context,
) : ViewHolder(binding.root) {
    private var diaryEntity: DiaryEntity? = null

    init {
        val wrapper: Context = ContextThemeWrapper(context, R.style.Widget_App_PopupMenu)
        val popupMenu = PopupMenu(wrapper, binding.btnOptions)
        popupMenu.inflate(R.menu.menu_item_diary)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    onDeleteClicked(absoluteAdapterPosition)
                    true
                }
                else -> {
                    false
                }
            }
        }


        binding.btnOptions.setOnClickListener {
            popupMenu.show()
        }
    }

    fun onBind(diaryEntity: DiaryEntity) {
        this.diaryEntity = diaryEntity

        binding.run {
            tvDescription.text = diaryEntity.description
            tvDate.text = diaryEntity.date

            if (diaryEntity.image != null) {
                glide.load(diaryEntity.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivImage)
            }
            else {
                ivImage.visibility = View.GONE
            }
        }
    }
}
