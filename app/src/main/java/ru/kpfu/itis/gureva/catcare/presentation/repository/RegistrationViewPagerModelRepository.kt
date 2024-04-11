package ru.kpfu.itis.gureva.catcare.presentation.repository

import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.presentation.model.RegistrationViewPagerModel

object RegistrationViewPagerModelRepository {
    val list: List<RegistrationViewPagerModel> = listOf(
        RegistrationViewPagerModel("добро пожаловать", "здесь классно", R.drawable.cat_fact),
        RegistrationViewPagerModel("papapapa", "papapap", R.drawable.cat_fact),
        RegistrationViewPagerModel("hahahhaha", "hahahah", R.drawable.cat_fact)
    )
}
