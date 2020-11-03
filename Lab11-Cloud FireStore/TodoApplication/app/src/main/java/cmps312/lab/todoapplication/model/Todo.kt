package cmps312.lab.todoapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo(
    var title: String? = "",
    var status: Boolean? = false,
    var priority: String? = "",
    var date: String? = "",
    var time: String? = "",
    var todoId: String? = "",
    var projectId: String? = null
) : Parcelable