import accounting.AccountManager

fun main(args: Array<String>) {
    if (!AccountManager.isLoggedIn()) {
        println("User has not logged in, trying to login")
        if (!AccountManager.login()) {
            println("User has not registered yet, trying to register")
            AccountManager.register()
        }
    }

    if (AccountManager.isLoggedIn()) {
        for (balance in AccountManager.getBalances()) {
            println(balance)
        }
    }


}