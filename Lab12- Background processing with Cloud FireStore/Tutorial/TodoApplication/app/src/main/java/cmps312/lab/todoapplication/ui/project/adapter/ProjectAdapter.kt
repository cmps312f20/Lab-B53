package cmps312.lab.todoapplication.ui.project.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cmps312.lab.todoapplication.R
import cmps312.lab.todoapplication.databinding.ProjectListItemBinding
import cmps312.lab.todoapplication.model.Project
import com.bumptech.glide.Glide


class ProjectAdapter(val showTodoList: (Project) -> Unit) :
    RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    var projects = listOf<Project>()
        set(value) {

            for (p: Project in projects)
                Log.d("TAG Project Id", p.id.toString())
            field = value
            notifyDataSetChanged()
        }


    inner class ProjectViewHolder(private val binding: ProjectListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(project: Project) {
            binding.project = project
            binding.root.setOnClickListener { showTodoList(project) }
            //todo
            if (project.imageUrl.length > 10) {
                binding.projectImg.visibility = View.VISIBLE
                Glide.with(binding.root.context).load(project.imageUrl).into(binding.projectImg)
//                Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding: ProjectListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.project_list_item,
            parent,
            false
        )
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) =
        holder.bind(projects[position])

    override fun getItemCount(): Int = projects.size
}