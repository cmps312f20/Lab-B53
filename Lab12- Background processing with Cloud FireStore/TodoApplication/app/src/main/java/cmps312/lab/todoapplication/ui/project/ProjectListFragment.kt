package cmps312.lab.todoapplication.ui.project

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cmps312.lab.todoapplication.R
import cmps312.lab.todoapplication.model.Project
import cmps312.lab.todoapplication.ui.project.adapter.ProjectAdapter
import cmps312.lab.todoapplication.ui.todo.viewmodel.ProjectViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_project_list.*

class ProjectListFragment : Fragment(R.layout.fragment_project_list) {
    private val GALLERY = 100
    private val projectViewModel: ProjectViewModel by activityViewModels()
    var dialogView : View? = null
//    private val todoViewModel : TodoViewModel by activityViewModels()
    private lateinit var photoUri : Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectAdapter = ProjectAdapter(::showTodoList)

        projectRV.apply {
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(context)
        }

        projectViewModel.projects.observe(viewLifecycleOwner) {
            projectAdapter.projects = it
        }

        addProjectFB.setOnClickListener {
            onCreateDialog(null).show()
        }

        setRecyclerSwipeListener()
    }

    private fun showTodoList(project: Project) {
        projectViewModel.selectedProject = project
        projectViewModel.getTodos(project.id)
        findNavController().navigate(R.id.toTodoListFragment)
    }

    private fun deleteProjectListener(project: Project) {
        projectViewModel.deleteProject(project)
        findNavController().navigateUp()
    }

    private fun onProjectDeleted(position: Int) {
        projectViewModel.projects.value?.let {
            val project = it[position]
            projectViewModel.deleteProject(project)

            Snackbar.make(requireView(), "$project removed", Snackbar.LENGTH_LONG)
                .setAction("UNDO") {
                    projectViewModel.addProject(project, photoUri)
                }.show()
        }
    }
    //todo gallery


    private fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myCustomDialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.project_add_dialog, null)
            dialogView = view

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val selectBtn : Button = view.findViewById<Button>(R.id.chooseImgBtn)
            selectBtn.setOnClickListener {
//                openGallery()
            }

            builder.setView(view)
                // Add action buttons
                .setPositiveButton("Add",
                    DialogInterface.OnClickListener { dialog, id ->
                        val projectNameEdt = view.findViewById<EditText>(R.id.projectNameEdt)
                        if (projectNameEdt.text.toString().isNotBlank()) {
                            val newProject = Project()
                            newProject.name = projectNameEdt.text.toString()
                            projectViewModel.addProject(newProject, photoUri)
                        }
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
                .setTitle("Add new project")
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        return myCustomDialog;
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
                onProjectDeleted(viewHolder.adapterPosition)
            }
        }

        /* 3. Initialize ItemTouchHelper with the swipeHandler you defined,
              and then attach it to the RecyclerView.
         */
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(projectRV)
    }
    //endregion

    //todo


}