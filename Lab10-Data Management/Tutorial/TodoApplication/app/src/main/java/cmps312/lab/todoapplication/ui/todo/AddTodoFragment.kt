package cmps312.lab.todoapplication.ui.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cmps312.lab.todoapplication.R
import cmps312.lab.todoapplication.data.local.entity.Todo
import cmps312.lab.todoapplication.ui.todo.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_add_todo.*
import java.text.SimpleDateFormat
import java.util.*

class AddTodoFragment : Fragment(R.layout.fragment_add_todo) {
    private val projectViewModel: ProjectViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todo = Todo(pid = 1)

        pickDateBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(
                view.context,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    dateTV.text = "$day/$month/$year"
                },
                year,
                month,
                day
            ).show()
        }

        pickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                timeTv.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(view.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        addBtn.setOnClickListener {
            if (isValid(titleEdt, dateTV, timeTv)) {
                todo.apply {
                    title = titleEdt.text.toString()
                    status = statusCB.isChecked
                    priority = prioritySP.selectedItem.toString()
                    date = dateTV.text.toString()
                    time = timeTv.text.toString()
                    pid = projectViewModel.selectedProject.id

                }
                projectViewModel.addTodo(todo)
                findNavController().navigateUp()
            }
        }


    }

    companion object {
        fun isValid(title: EditText, date: TextView, time: TextView): Boolean {
            if (TextUtils.isEmpty(title.text) || TextUtils.isEmpty(date.text) || TextUtils.isEmpty(
                    time.text
                )
            )
                return false
            return true
        }
    }

}


/*  Improvement

 */