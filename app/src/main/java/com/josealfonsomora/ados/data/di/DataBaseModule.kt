package com.josealfonsomora.ados.data.di

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import com.josealfonsomora.ados.data.network.ChuckNorrisResponse
import com.josealfonsomora.ados.data.room.AdosDatabaseRoom
import com.josealfonsomora.ados.data.room.AutobusDao
import com.josealfonsomora.ados.data.room.ChuckJokeDao
import com.josealfonsomora.ados.data.room.ChuckJokesRoomDB
import com.josealfonsomora.ados.data.sqlite.AdosDatabaseSqlite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReadableSqlLite

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WritableSqlLite

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    @WritableSqlLite
    fun providesSqliteDb(@ApplicationContext context: Context): SQLiteDatabase =
        AdosDatabaseSqlite(context).writableDatabase

    @Singleton
    @Provides
    @ReadableSqlLite
    fun providesReadableSqliteDb(@ApplicationContext context: Context): SQLiteDatabase =
        AdosDatabaseSqlite(context).readableDatabase

    @Singleton
    @Provides
    fun providesSqliteHelper(@ApplicationContext context: Context): AdosDatabaseSqlite {
        return AdosDatabaseSqlite(context)
    }

    @Singleton
    @Provides
    fun providesRoomDb(@ApplicationContext context: Context): AdosDatabaseRoom {
        val roomDb = Room.databaseBuilder(
            context.applicationContext,
            AdosDatabaseRoom::class.java,
            "ados_room"
        ).build()
        return roomDb
    }

    @Provides
    fun provideAutobusDao(database: AdosDatabaseRoom): AutobusDao {
        return database.autobusDao()
    }

    @Singleton
    @Provides
    fun providesChuckJokesRoomDb(@ApplicationContext context: Context): ChuckJokesRoomDB =
        Room.databaseBuilder(
            context, ChuckJokesRoomDB::class.java, "chuck_jokes"
        ).build()

    @Singleton
    @Provides
    fun providesChuckJokesDao(db: ChuckJokesRoomDB) = db.ChuckJokeDao()

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}