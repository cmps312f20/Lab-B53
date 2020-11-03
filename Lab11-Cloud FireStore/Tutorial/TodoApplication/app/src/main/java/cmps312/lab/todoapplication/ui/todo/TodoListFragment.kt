package cmps312.lab.todoapplication.ui.todo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cmps312.lab.todoapplication.R
import cmps312.lab.todoapplication.model.Todo
import cmps312.lab.todoapplication.ui.todo.adapter.TodoAdapter
import cmps312.lab.todoapplication.ui.todo.viewmodel.ProjectViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_todo_list.*

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {

    private val projectViewModel: ProjectViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoAdapter = TodoAdapter(::editTodoListener)
        todoListRV.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(context)
        }

        projectViewModel.todos.observe(viewLifecycleOwner) {
            todoAdapter.todos = it
        }

        addTodoFB.setOnClickListener {
            findNavController().navigate(R.id.toAddTodoFragment)
        }

        setRecyclerSwipeListener()
    }

    private fun editTodoListener(todo: Todo) {
        projectViewModel.selectedTodo = todo
        findNavController().navigate(R.id.toUpdateTodoFragment)
    }

    private fun deleteTodoListener(todo: Todo) {
        projectViewModel.deleteTodo(todo)
        findNavController().navigateUp()
    }

    //region Handle swipe to delete
    private fun setRecyclerSwipeListener() {
        /*
        1. Create ItemTouchHelper.SimpleCallback and tell it what events to listen for.
        It takes two parameters: One for drag directions and one for swipe directions.
        We’re only interested in swipe. Pass 0 to inform the callback not to respond to drag events.
        */
        val swipeHandler = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            // Ignore and do not perform any special behavior here
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            // 2. onSwiped ask the RecyclerView adapter to delete the swiped item
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                onTodoDeleted(viewHolder.adapterPosition)
            }
        }

        /* 3. Initialize ItemTouchHelper with the swipeHandler you defined,
              and then attach it to the RecyclerView.
         */
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(todoListRV)
    }
    //endregion

    private fun onTodoDeleted(position: Int) {
        projectViewModel.todos.value?.let {
            val todo = it[position]
            projectViewModel.deleteTodo(todo)

            Snackbar.make(requireView(), "$todo removed", Snackbar.LENGTH_LONG)
                .setAction("UNDO") {
                    projectViewModel.addTodo(todo)
                }.show()
        }
    }
}