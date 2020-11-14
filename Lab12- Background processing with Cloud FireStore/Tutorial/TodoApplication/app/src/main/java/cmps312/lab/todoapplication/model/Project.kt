package cmps312.lab.todoapplication.model

data class Project(

//    @DocumentId
    var id: String = "",
    var name: String ,
    var userId: String,
    var imageUrl : String
){
    constructor() : this("", "", "", "")

    override fun toString(): String {
        return "id : $id - userId : $userId"
    }
}