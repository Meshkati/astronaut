package database

import org.stellar.sdk.KeyPair

/**
 * This class provides all the stuffs about handling data, actually it's an interface between db driver
 * and other app modules.
 */
object Repository {
    /**
     * Persists the user keyPair
     * @param keyPair
     *
     */
    fun setKeyPair(keyPair: KeyPair) {
        // TODO: Make sure that the transaction is completed
        SQLiteDriver.insert("keyValue", "account_id", keyPair.accountId)
        SQLiteDriver.insert("keyValue", "secret_seed", String(keyPair.secretSeed))
    }

    /**
     * Get's the secret seed from db
     */
    fun getSecretSeed(): String {
        return SQLiteDriver.getValue("keyValue", "secret_seed")
    }

    /**
     * Gets the account ID from db
     */
    fun getAccountID(): String {
        return SQLiteDriver.getValue("keyValue", "accout_id")
    }
}