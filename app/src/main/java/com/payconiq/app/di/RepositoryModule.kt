package com.payconiq.app.di

import com.payconiq.app.network.source.UserDataSource
import com.payconiq.app.network.source.UserDataSourceImpl
import com.payconiq.app.repository.UserRepository
import com.payconiq.app.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class UsersRepository


@InstallIn(SingletonComponent::class)
@Module
abstract class UserModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindUserDataSource(impl: UserDataSourceImpl): UserDataSource
}
