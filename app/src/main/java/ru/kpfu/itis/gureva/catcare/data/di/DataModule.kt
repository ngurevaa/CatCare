package ru.kpfu.itis.gureva.catcare.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.data.database.AppDatabase
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences =
        context.getSharedPreferences(Keys.OPTIONS_SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app.db").build()

    @Provides
    fun providePetDao(database: AppDatabase) = database.petDao

    @Provides
    fun provideWeightDao(database: AppDatabase) = database.weightDao
}
