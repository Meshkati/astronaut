package database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * This class provides all the stuffs about handling sqlite
 */
object SQLiteDriver {
    private var connection: Connection? = null

    /**
     * Makes the sqlite connection
     */
    private fun connect(): Connection? {
        if (connection != null) {
            return connection
        }
        // Make a new connection to the db
        try {
            connection = DriverManager.getConnection(Config.SQLITE_PATH)
            // Check tables and creates needed ones
            createTables()
        } catch (e: SQLException) {
            println(e.message)
        }

        return connection
    }
}