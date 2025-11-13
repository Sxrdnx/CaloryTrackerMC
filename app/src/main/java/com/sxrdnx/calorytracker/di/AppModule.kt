package com.sxrdnx.calorytracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.sxrdnx.core.data.preferences.DefaultPreferences
import com.sxrdnx.core.domain.preferences.Preferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun provideSharedPreferences(app:Application):SharedPreferences{
        return app.getSharedPreferences("shared-pref",MODE_PRIVATE)
    }

    fun provideDefaultPreferences(preferences:SharedPreferences ):Preferences{
            return DefaultPreferences(preferences)
    }


}