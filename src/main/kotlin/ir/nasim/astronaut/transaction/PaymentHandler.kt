package ir.nasim.astronaut.transaction

import ir.nasim.astronaut.accounting.AccountManager
import org.stellar.sdk.*
import java.lang.Exception
import ir.nasim.astronaut.Config

/**
 * This class provides all the stuffs about handling payments,
 * like sending payment to others, get mentioned about getting a payment
 * and other things.
 */
object PaymentHandler {
    /**
     * Sends a payment to a user in the network
     * @param secretSeed is the source secret_seed
     * @param accountID is the destination account_id
     * @param amount is the amount of the currency
     * @param memo is the memo message for the transaction, its optional
     *
     * @return returns true of payment succeed, false if fails
     */
    fun sendPayment(secretSeed: String, accountID: String, amount: String, memo: String = ""): Boolean {
        // Preparing the server
        prepareNetwork()
        val server = Server(Config.SERVER_URL)
        // Generating source and destination keys
        val source = KeyPair.fromSecretSeed(secretSeed)
        val destination = KeyPair.fromAccountId(accountID)
        // Check if the destination account exists, to prevent the extra fee
        if (!checkAccountExists(accountID)) {
            println("PaymentHandler::sendPayment::Destination account is not exists")
            return false
        }
        // Gets up-to-date information of the account
        val sourceAccount = AccountManager.loadAccountFromServer()
        // Building the transaction
        // TODO: Persist transaction state for retrieving transaction state after app failure
        val transaction = Transaction.Builder(sourceAccount)
                .addOperation(PaymentOperation.Builder(destination, AssetTypeNative(), amount).build())
                .addMemo(Memo.text(memo))
                .setTimeout(Config.TRANSACTION_TIMEOUT.toLong())
                .build()
        transaction.sign(source)
        // Sending the transaction to the server
        try {
            val submissionResponse = server.submitTransaction(transaction)
            // Checking the response state
            if (!submissionResponse.isSuccess) {
                println("Payment Failed after submission")
            }
            println("Payment Succeed!")

            return true
        } catch (e: Exception) {
            println("PaymentHandler::sendPayment::Exception occurred\n" + e.message)
        }

        return false
    }

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