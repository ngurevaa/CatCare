package ru.kpfu.itis.gureva.catcare.utils

import ru.kpfu.itis.gureva.catcare.R

enum class Behaviour(val imageId: Int, val description: Int) {
    ACTIVE(R.drawable.cat_active, R.string.active),
    ANGRY(R.drawable.cat_angry, R.string.angry),
    FEARFUL(R.drawable.cat_fearful, R.string.fearful),
    SAD(R.drawable.cat_sad, R.string.sad),
    SLEEPY(R.drawable.cat_sleepy, R.string.sleepy),
    TENDER(R.drawable.cat_tender2, R.string.tender)
}
