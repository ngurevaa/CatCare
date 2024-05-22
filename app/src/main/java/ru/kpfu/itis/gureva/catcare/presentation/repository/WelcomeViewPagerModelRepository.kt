package ru.kpfu.itis.gureva.catcare.presentation.repository

import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.presentation.model.WelcomeViewPagerModel

object WelcomeViewPagerModelRepository {
    val list: List<WelcomeViewPagerModel> = listOf(
        WelcomeViewPagerModel("Здравствуйте!", "Наше приложение поможет вам контролировать здоровье вашего питомца",R.drawable.hello_cat),
        WelcomeViewPagerModel("Вы не забудете ничего", "Ведь теперь вы сможете сохранять всё: от обычных заметок о прошедшем дне до важных записей о вакцинации", R.drawable.cat_with_phone),
        WelcomeViewPagerModel("Хотите начать?", "Тогда давайте заполним данные о вашем питомце",R.drawable.cute_cat)
    )
}
