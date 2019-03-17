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
     * Inserts a key-value like data in database
     * @param table
     * @param key
     * @param value
     *
     * TODO: Value type has to be changed to byte
     */
    fun insert(table: String, key: String, value: String): Int {
        var insertedID = -1
        val sql = "INSERT INTO $table (key,value) VALUES(?,?);"
        println(sql)
        try {
            val connection = connect()
            val statement = connection?.prepareStatement(sql)
            statement?.setString(1, key)
            statement?.setString(2, value)
            insertedID = statement?.executeUpdate()!!
        } catch (e: SQLException) {
            println(e.message)
        }

        return insertedID
    }

    /**
     * Checks if a table is created or not
     * @param tableName
     */
    fun isTableExists(tableName: String): Boolean {
        try {
            val connection = connect()
            val metaData = connection?.metaData
            val result = metaData?.getTables(null, null, tableName, null)
            result?.last()

            return result?.row!! > 0
        } catch (e: SQLException) {
            println(e.message)
        }

        return false
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