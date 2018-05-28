package net.noahglaser.todoroomrxkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_edit_todo.*

class EditTodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo)
        App.database
                ?.todoDao()
                ?.get(intent.getLongExtra("id", 1))
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.doAfterSuccess { et_name_todo.setText(it.name)  }
                ?.subscribe()
    }

    fun update(v: View) {
        App.database
                ?.todoDao()
                ?.get(intent.getLongExtra("id", 1))
                ?.flatMap {
                    Single.fromCallable {
                        it.name = et_name_todo.text.toString()
                        App.database
                                ?.todoDao()
                                ?.update(it)
                    }
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                }
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.doAfterSuccess { goBack() }
                ?.subscribe()
    }

    fun delete(v: View) {
        App.database
                ?.todoDao()
                ?.get(intent.getLongExtra("id", 1))
                ?.flatMap {
                    Single.fromCallable {
                        App.database
                                ?.todoDao()
                                ?.delete(it)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                }
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.doAfterSuccess { goBack() }
                ?.subscribe()
    }

    private fun goBack() {
        startActivity(Intent(applicationContext, MainActivity::class.java))

    }
}
