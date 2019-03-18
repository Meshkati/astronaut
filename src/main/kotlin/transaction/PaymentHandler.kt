
package transaction

/**
 * This class provides all the stuffs about handling payments,
 * like sending payment to others, get mentioned about getting a payment
 * and other things.
 */
object PaymentHandler {
    
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