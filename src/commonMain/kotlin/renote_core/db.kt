package renote_core

import com.squareup.sqldelight.db.SqlDriver
import renoteDB.CategoriesQueries
import renoteDB.NotesQueries
import renoteDB.AssociatedCategoriesQueries
import renoteDB.MainDatabase

expect fun createSysSqlDriver(dbName:String): SqlDriver

class Storage() {
    private val db: MainDatabase
    val categoriesDAO: CategoriesQueries
    val notesDAO: NotesQueries
    val associatedCategoriesDAO: AssociatedCategoriesQueries

    init {
        db = createMainDB()
        categoriesDAO = db.categoriesQueries
        notesDAO = db.notesQueries
        associatedCategoriesDAO = db.associatedCategoriesQueries
    }

    fun addCategory(name: String): Category {
        categoriesDAO.insertCategory(name)
        val categoryId = categoriesDAO.last_insert_rowid().executeAsOne()
        return Category(categoryId, name)
    }

    fun getCategories(): List<Category> {
        return categoriesDAO.selectAllCategories(
            mapper = { id, name -> Category(id, name) }
        ).executeAsList()
    }

    fun getNotes(): List<Note> {
        return notesDAO.selectAllNotes(
            mapper = { id, name, text, attachedLink -> Note(id, name, text, attachedLink) }
        ).executeAsList()
    }

    fun getNotesForCategory(id: Long): List<Note> {
        return associatedCategoriesDAO.getCategoryNotes(
            id,
            mapper = {noteId, name, text, attachedLink, _, _ -> Note(noteId, name, text, attachedLink)}
        ).executeAsList()
    }

    fun getNotesForCategories(categoriesIds: List<Long>): List<Note> {
        var result = listOf<Note>()

        for (id in categoriesIds) {
            result = result.plus(getNotesForCategory(id))
        }

        return result.distinctBy { note -> note.id }
    }

    fun addNote(name: String, text: String, attachedLink: String?, attachedCategories: List<Long>): Note {
        notesDAO.insertNote(name, text, attachedLink)
        val noteId = notesDAO.last_insert_rowid().executeAsOne()

        for (attachedCategoryId in attachedCategories) {
            associatedCategoriesDAO.insertAssociatedCategory(noteId, attachedCategoryId)
        }

        return Note(noteId, name, text, attachedLink)
    }

    private fun createMainDB(): MainDatabase {
        return MainDatabase(createSysSqlDriver("maindatabase.db"))
    }
}