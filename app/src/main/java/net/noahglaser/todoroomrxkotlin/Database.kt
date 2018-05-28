package net.noahglaser.todoroomrxkotlin

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun todoDoa(): TodoDao
}