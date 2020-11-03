package cmps312.lab.todoapplication.ui.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cmps312.lab.todoapplication.R
import cmps312.lab.todoapplication.databinding.TodoListItemBinding
import cmps312.lab.todoapplication.model.Todo

class TodoAdapter(val editTodoListener: (Todo) -> Unit) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    var todos = listOf<Todo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class TodoViewHolder(private val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.editImg.setOnClickListener { editTodoListener(todo) }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding: TodoListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.todo_list_item,
            parent,
            false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) =
        holder.bind(todos[position])

    override fun getItemCount(): Int = todos.size
}