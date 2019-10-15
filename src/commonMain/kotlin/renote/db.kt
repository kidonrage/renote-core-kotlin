package renote

import com.squareup.sqldelight.db.SqlDriver
import renoteDB.Categories
import renoteDB.CategoriesQueries
import renoteDB.MainDatabase

expect fun createSysSqlDriver(dbName:String): SqlDriver

class Storage() {
    private val db: MainDatabase
    private val categories: CategoriesQueries

    init {
        db = createMainDB()
        categories = db.categoriesQueries
    }

    fun addCategory(name: String) {
        categories.insertCategory(name)
    }

    fun getCategories(): List<Categories> {
        return categories.selectAllCategories().executeAsList()
    }

    private fun createMainDB(): MainDatabase {
        return MainDatabase(createSysSqlDriver("maindatabase.db"))
    }
}