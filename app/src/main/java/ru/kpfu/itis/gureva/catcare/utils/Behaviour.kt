package ru.kpfu.itis.gureva.catcare.utils

import ru.kpfu.itis.gureva.catcare.R

enum class Behaviour(val imageId: Int, val description: Int) {
    ACTIVE(R.drawable.cat_active2, R.string.active),
    ANGRY(R.drawable.cat_angry2, R.string.angry),
    FEARFUL(R.drawable.cat_fearful2, R.string.fearful),
    SAD(R.drawable.cat_sad2, R.string.sad),
    SLEEPY(R.drawable.cat_sleepy2, R.string.sleepy),
    TENDER(R.drawable.cat_tender2, R.string.tender)
}
