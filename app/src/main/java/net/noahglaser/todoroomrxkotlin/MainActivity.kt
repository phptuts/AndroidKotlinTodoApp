package net.noahglaser.todoroomrxkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpTodoList()
    }

    fun addTodo(v: View) {
        startActivity(Intent(applicationContext, AddTodoActivity::class.java))
    }


    private fun setUpTodoList() {

        App.database
                ?.todoDoa()
                ?.getUnCompleteTodo()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    val adapter = TodoAdapter(
                            it,
                            { editTodo(it) },
                            { completeTodo(it) }
                    )
                    todo_list.layoutManager = LinearLayoutManager(applicationContext)
                    todo_list.adapter = adapter
                    todo_list.addItemDecoration(DividerItemDecoration(todo_list.context, DividerItemDecoration.VERTICAL))
                }, {
                    Log.d("ERROR", it.message)
                })
    }

    private fun editTodo(id: Long) {
        val intent = Intent(applicationContext, EditTodoActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun completeTodo(id: Long) {
        App.database
                ?.todoDoa()
                ?.get(id)
                ?.flatMap {
                    it.completed = true
                    Single.fromCallable {
                        App.database
                                ?.todoDoa()
                                ?.update(it)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                }
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doAfterSuccess { setUpTodoList() }
                ?.subscribe()
    }
}
