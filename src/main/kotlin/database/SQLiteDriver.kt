package database

import java.sql.Connection
import java.sql.DriverManager

object SQLiteDriver {
    fun connect() {
        val connection = DriverManager.getConnection(Config.SQLITE_PATH)
    }
}