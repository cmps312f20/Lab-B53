package cmps312.lab.todoapplication.ui.todo

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cmps312.lab.todoapplication.R
import cmps312.lab.todoapplication.data.local.entity.Todo
import cmps312.lab.todoapplication.ui.todo.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_add_todo.*

class AddTodoFragment : Fragment(R.layout.fragment_add_todo) {
    private val projectViewModel : ProjectViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todo = Todo(pid = 1)

        addBtn.setOnClickListener {
            if (isValid(titleEdt, dateEdt, timeEdt)) {
                todo.apply {
                    title = titleEdt.text.toString()
                    status = statusCB.isChecked
                    priority = prioritySP.selectedItem.toString()
                    date = dateEdt.text.toString()
                    time = timeEdt.text.toString()
                    pid = projectViewModel.selectedProject.id

                }
                projectViewModel.addTodo(todo)
                findNavController().navigateUp()
            }
        }


    }
    companion object{
        fun isValid(title: EditText, date: EditText, time: EditText): Boolean {
            if (TextUtils.isEmpty(title.text) || TextUtils.isEmpty(date.text) || TextUtils.isEmpty(time.text))
                return false
            return true
        }
    }

}


/*  Improvement
       dateEdt.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(view.context, DatePickerDialog.OnDateSetListener { _, year , month , day->
                dateEdt.setText("$day/$month/$year")
            }, year, month, day).show()

        }
 */