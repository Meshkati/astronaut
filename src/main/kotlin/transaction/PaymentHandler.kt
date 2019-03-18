
package transaction

/**
 * This class provides all the stuffs about handling payments,
 * like sending payment to others, get mentioned about getting a payment
 * and other things.
 */
object PaymentHandler {
    /**
     * Checks if the account with provided account_id exists in the network
     * We have to do it to prevent extra fees on transactions
     * This operation doesn't take any fee
     * So before every transaction, check the existency of the account
     * @param accountID
     */
    fun checkAccountExists(accountID: String): Boolean {
        // Preparing the server
        prepareNetwork()
        val server = Server(Config.SERVER_URL)
        // Creating the keyPair of the user
        val userToCheck = KeyPair.fromAccountId(accountID)
        try {
            server.accounts().account(userToCheck)
        } catch (e: Exception) {
            println(e.message)

            return false
        }

        return true
    }

    /**
     * Prepares the network for the server, either on the test network or other networks
     */
    private fun prepareNetwork() {
        // TODO: Add kuknus network
        if (Config.MODE == "TEST") {
            Network.useTestNetwork()
        } else {
            Network.usePublicNetwork()
        }
    }
}