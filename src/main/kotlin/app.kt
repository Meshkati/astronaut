import accounting.AccountManager

fun main(args: Array<String>) {
    println("Start")
    println(AccountManager.register())
    println("End")
    for (balance in AccountManager.getBalances()) {
        println(balance)
    }

}