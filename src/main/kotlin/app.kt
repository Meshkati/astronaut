import accounting.AccountManager
import database.Repository
import transaction.PaymentHandler

fun main(args: Array<String>) {
    if (!AccountManager.isLoggedIn()) {
        println("User has not logged in, trying to login")
        if (!AccountManager.login()) {
            println("User has not registered yet, trying to register")
            AccountManager.register()
        }
    }

    if (!AccountManager.isLoggedIn()) {
        return
    }

    for (balance in AccountManager.getBalances()) {
        println(balance)
    }

    PaymentHandler.sendPayment(Repository.getSecretSeed(),
            "GDH26FG64TAHWPBGJGJFAQIVWKTFNBAZUS2F4HQC2LTBSGOHQKCHLGKR",
            "24",
            "First transaction"
            )

}