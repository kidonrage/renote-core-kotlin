package renote_core

import com.squareup.sqldelight.db.SqlDriver
import renoteDB.MainDatabase

actual fun createSysSqlDriver(dbName: String): SqlDriver {
//    return AndroidSqliteDriver(MainDatabase.Schema, dbName)
}