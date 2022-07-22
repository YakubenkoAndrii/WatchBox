package com.sample.project.watchbox.di

import com.sample.project.watchbox.utils.scheduler.SchedulerProvider
import com.sample.project.watchbox.utils.scheduler.SchedulerProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AsyncModule {

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProviderImpl()
}