package com.sample.project.watchbox.di

import com.sample.project.watchbox.data.repositories.MoviesRepository
import com.sample.project.watchbox.data.repositories.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Singleton
    @Binds
    abstract fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository
}
