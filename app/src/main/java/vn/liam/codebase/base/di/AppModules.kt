package vn.liam.codebase.base.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vn.liam.codebase.base.database.AppDatabase
import vn.liam.codebase.base.database.dao.DetailsCachedDAO
import vn.liam.codebase.base.database.dao.MovieDAO


@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    @Provides
    fun provideMovieDAO(@ApplicationContext appContext: Context): MovieDAO {
        return AppDatabase.getInstance(appContext).movieDao()
    }

    @Provides
    fun provideDetailsCachedDAO(@ApplicationContext appContext: Context): DetailsCachedDAO {
        return AppDatabase.getInstance(appContext).detailsCachedDao()
    }

}