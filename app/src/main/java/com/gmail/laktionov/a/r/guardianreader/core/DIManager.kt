package com.gmail.laktionov.a.r.guardianreader.core

import android.arch.persistence.room.Room
import android.content.Context
import com.gmail.laktionov.a.r.guardianreader.BuildConfig
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository
import com.gmail.laktionov.a.r.guardianreader.domain.Repository
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.DBStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.KeyValueStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.LocalStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.StringProvider
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.preference.SharedPrefs
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.resources.LocalStringProvider
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.GuardianDatabase
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.RoomDbStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.RemoteStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit.RetrofitServerStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit.ServerApi
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Simple implementation of dependency injection pattern
 * (Dagger2 light version) =)
 */
object DIManager {

    fun injectAll(context: Context) = GuardianViewModelFactory.initialize(provideRepository(context))

    private fun provideRepository(context: Context): Repository =
            GuardianRepository(
                    RemoteStorage(provideRemoteSource()),
                    LocalStorage(
                            provideDBStorage(context),
                            provideKeyValueStorage(context),
                            provideStringProvider(context)))

    /*
        Remote source initialization
     */
    private fun provideRemoteSource() =
            RetrofitServerStorage(provideServerApi())

    private fun provideServerApi() =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createHttpClient())
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()
                    .create(ServerApi::class.java)


    private fun createHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(TokenInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY })
                    .build()


    /*
        Local source initialization
     */

    private fun provideDBStorage(context: Context): DBStorage {
        return RoomDbStorage(
                Room.databaseBuilder(
                        context,
                        GuardianDatabase::class.java,
                        RoomDbStorage.DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build())
    }

    private fun provideKeyValueStorage(context: Context): KeyValueStorage {
        return SharedPrefs(
                context.getSharedPreferences(SharedPrefs.SHARED_STORAGE, Context.MODE_PRIVATE))
    }

    private fun provideStringProvider(context: Context): StringProvider {
        return LocalStringProvider(context.resources)
    }
}