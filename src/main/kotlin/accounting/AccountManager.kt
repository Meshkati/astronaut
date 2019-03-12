package accounting

import org.stellar.sdk.KeyPair
import java.net.HttpURLConnection
import java.net.URL

/**
 * This class is to manage account properties
 * like registering, logging in and all other the
 * stuffs that are appropriate to user accounts
 */
object AccountManager {
    private var keyPair: KeyPair? = null
    /**
     * Registers a new user
     */
    fun register(): Boolean {
        // Getting new keypair as ID
        this.keyPair = KeyPair.random()
        // Registering the user
        val registerURL = URL(Config.SERVER_URL + "?addr=" + this.keyPair?.accountId)
        val connection = registerURL.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()
        // todo: persist the state of logging in
        if (connection.responseCode != 200) {
            // for resetting the ID
            this.keyPair = null
            return false
        }

        return true
    }
}