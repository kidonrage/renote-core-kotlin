package renote_core

data class Category(
    val id: Long,
    val name: String
)

data class Note(
    val id: Long,
    val name: String,
    val text: String,
    val attachedLink: String?
)

class RenoteAPI() {

    private val storage = Storage()

}