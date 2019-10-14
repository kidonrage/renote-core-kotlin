package renote

import com.squareup.sqldelight.db.SqlDriver
import renoteDB.Categories
import renoteDB.CategoriesQueries
import renoteDB.MainDatabase

expect fun createSysSqlDriver(dbName:String): SqlDriver

