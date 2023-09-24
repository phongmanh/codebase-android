package vn.liam.codebase.base.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.liam.codebase.base.services.MovieServices

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    fun movieServiceProvider(): MovieServices {
        return ServicesSdk.apiBuilderProvide(apiClass = MovieServices::class.java)
    }

}