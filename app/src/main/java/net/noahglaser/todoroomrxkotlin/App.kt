package net.noahglaser.todoroomrxkotlin

import android.app.Application
import android.arch.persistence.room.Room
import com.facebook.stetho.Stetho

class App: Application() {

    companion object {
        var database: Database? = null
    }

    override fun onCreate() {
        super.onCreate()
        App.database = Room
                .databaseBuilder(this, Database::class.java, "todo")
                .build()
        Stetho.initializeWithDefaults(this)
    }
}