package net.noahglaser.todoroomrxkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.todo_recycler_view.view.*

class TodoAdapter(private val todos: List<Todo>,
                  private val editListener: (Long) -> Unit,
                  private val completeListener: (Long) -> Unit): RecyclerView.Adapter<TodoAdapter.TodoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_recycler_view, parent, false)
        return TodoHolder(v, editListener, completeListener)
    }

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.itemView.tv_habit_name.text = todos[position].name
        holder.itemView.tv_number.text = "${position+1}) "
        holder.itemView.tag = todos[position].id
    }

    class TodoHolder(private val v: View,
                     private val longClickListener: (Long) -> Unit,
                     private val checkBoxListener: (Long) -> Unit):
            RecyclerView.ViewHolder(v) {
        init {

            v.setOnLongClickListener {
                longClickListener(it.tag.toString().toLong())

                true
            }

            v.cb_checkbox.setOnCheckedChangeListener { _, _ ->
                checkBoxListener(v.tag.toString().toLong())
            }

            v.setOnClickListener {
                v.cb_checkbox.isChecked = !v.cb_checkbox.isChecked
            }
        }
    }
}