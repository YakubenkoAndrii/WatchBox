package com.sample.project.watchbox.di

import com.sample.project.watchbox.data.model.mapper.moviemapper.MoviesMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {

    @Singleton
    @Provides
    fun provideMoviesMapper(): MoviesMapper = MoviesMapper()

}
