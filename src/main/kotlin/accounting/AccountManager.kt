package accounting

import org.stellar.sdk.KeyPair
import org.stellar.sdk.Server
import org.stellar.sdk.responses.AccountResponse
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
        val registerURL = URL(Config.BOT_URL + "?addr=" + this.keyPair?.accountId)
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

    /**
     * Gets the balances of the account
     */
    fun getBalances(): Array<Balance> {
        // getting account info from servers
        val server = Server(Config.SERVER_URL)
        val account = server.accounts().account(this.keyPair)

        var result = arrayOf<Balance>()
        // filling the balance wrapper
        if (account.balances != null) {
            for (rawBalance in account.balances) {
                result += Balance(rawBalance.assetType, rawBalance.assetCode, rawBalance.balance)
            }
        }
        // returns empty list if there is no balance for this account
        return result
    }
}

/**
 * Data class for account balance model
 */
data class Balance(var assetType: String, val assetCode: String?, val balance: String)