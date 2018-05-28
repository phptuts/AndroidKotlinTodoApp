package net.noahglaser.todoroomrxkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_todo.*

class AddTodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
    }

    fun addTodo(v: View) {
        val todo = Todo(name = et_add_name.text.toString(), id = null, completed = false)

        Single.fromCallable<Long> {
           App.database
                    ?.todoDoa()
                    ?.insert(todo)

        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            Log.d("CREATED TODO", "ID = $it")
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }, {
            Toast.makeText(applicationContext, "There was an error", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, MainActivity::class.java))
        })

    }
}
