package ru.kpfu.itis.gureva.catcare.presentation.repository

import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.presentation.model.WelcomeViewPagerModel

object WelcomeViewPagerModelRepository {
    val list: List<WelcomeViewPagerModel> = listOf(
        WelcomeViewPagerModel("добро пожаловать", "здесь классно", R.drawable.cat_fact),
        WelcomeViewPagerModel("papapapa", "papapap", R.drawable.cat_fact),
        WelcomeViewPagerModel("hahahhaha", "hahahah", R.drawable.cat_fact)
    )
}
