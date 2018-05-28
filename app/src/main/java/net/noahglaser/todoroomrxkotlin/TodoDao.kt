package net.noahglaser.todoroomrxkotlin

import android.arch.persistence.room.*
import io.reactivex.Single

@Dao
interface TodoDao {

    @Insert()
    fun insert(todo: Todo): Long?

    @Query("SELECT * FROM Todo WHERE completed = 0")
    fun getUnCompleteTodo(): Single<List<Todo>>?

    @Query("SELECT * FROM Todo WHERE id = :id")
    fun get(id: Long): Single<Todo>

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)

}