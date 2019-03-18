class Config {
    companion object {
        const val BOT_URL = "https://friendbot.stellar.org/"
        const val SERVER_URL = "https://horizon-testnet.stellar.org"
        const val SQLITE_PATH = "jdbc:sqlite:astronaut.db"
        const val MODE = "TEST" // `PROD` for production
    }

    /**
     * Checks if we're on test mode, to set the server, network and other properties
     */
    fun isOnTestMode(): Boolean {
        return MODE == "TEST"
    }
}