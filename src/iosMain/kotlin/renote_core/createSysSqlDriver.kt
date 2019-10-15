package renote_core

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import renoteDB.MainDatabase

actual fun createSysSqlDriver(dbName: String): SqlDriver {
    return NativeSqliteDriver(MainDatabase.Schema, dbName)
}