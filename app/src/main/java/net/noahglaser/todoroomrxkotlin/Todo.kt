package net.noahglaser.todoroomrxkotlin

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Todo")
data class Todo(@PrimaryKey(autoGenerate = true) var id: Long?,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "completed") var completed: Boolean = false)
