package cmps312.lab.todoapplication.ui.todo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cmps312.lab.todoapplication.R
import cmps312.lab.todoapplication.databinding.FragmentUpdateTodoBinding
import cmps312.lab.todoapplication.ui.todo.AddTodoFragment.Companion.isValid
import cmps312.lab.todoapplication.ui.todo.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_update_todo.*


class UpdateTodoFragment : Fragment(R.layout.fragment_update_todo) {
    private val projectViewModel: ProjectViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentUpdateTodoBinding.bind(view)
        binding.todo = projectViewModel.selectedTodo

        updateBtn.setOnClickListener {
            Toast.makeText(view.context, "Ok", Toast.LENGTH_SHORT).show()
            if (isValid(titleEdt, dateEdt, timeEdt)) {
                projectViewModel.selectedTodo?.apply {
                    title = titleEdt.text.toString()
                    status = statusCB.isChecked
                    priority = prioritySP.selectedItem.toString()
                    date = dateEdt.text.toString()
                    time = timeEdt.text.toString()

                }
                projectViewModel?.selectedTodo?.let { it1 -> projectViewModel.updateTodo(it1) }
            }

            activity?.onBackPressed()
        }
    }

}