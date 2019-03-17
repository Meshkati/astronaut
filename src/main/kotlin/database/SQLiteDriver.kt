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
    /**
     * This function is to create keyValue table on the db
     * FIXME: I know this db architect is wrong :D
     */
    private fun createKeyValueStorage() {
        val sql = "CREATE TABLE IF NOT EXISTS keyValue (\n" +
                " id integer PRIMARY KEY,\n" +
                " key text NOT NULL,\n" +
                " value text NOT NULL\n" +
                ");"
        try {
            val connection = DriverManager.getConnection(Config.SQLITE_PATH)
            val statement = connection?.createStatement()
            println(sql)
            statement?.execute(sql)

            connection.close()
        } catch (e: SQLException) {
            println(e.message)
        }
    }

    /**
     * Creates all of the tables needed in application
     */
    private fun createTables() {
        createKeyValueStorage()
    }
}