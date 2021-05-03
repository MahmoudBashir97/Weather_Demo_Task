package com.mahmoudbashir.weathertask_app.di.modules

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mahmoudbashir.weathertask_app.room.WeatherRoomDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
class WeatherModule(app:Application){
    private var W_app = app
    private lateinit var db:WeatherRoomDatabase
    private val databaseCallback = object : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("RoomDatabaseModule","onCreate")
            CoroutineScope(Dispatchers.IO).launch {

            }
        }
    }

    @Singleton
    @Provides
    fun providesRoomDatabase():WeatherRoomDatabase{
        db = Room.databaseBuilder(W_app,
        WeatherRoomDatabase::class.java,
        "\"weather_db\"")
            .fallbackToDestructiveMigration()
            .addCallback(databaseCallback)
            .build()
        return db
    }

    @Singleton
    @Provides
    fun providesDao(db:WeatherRoomDatabase)=db.dao()
}