package lectures.part3concurrency

object JVMConcurrencyProblems extends App {
  def runInParallel(): Unit = {
    var x = 0

    val thread1 = new Thread(() => x = 1)
    val thread2 = new Thread(() => x = 2)

    thread1.start()
    thread2.start()

    println(x)
  }

  runInParallel()

  case class BankAccount(var amount: Int)

  def buy(bankAccount: BankAccount, thing: String, price: Int): Unit = {
    bankAccount.amount -= price
  }

  def buySafe(bankAccount: BankAccount, thing: String, price: Int): Unit = {
    bankAccount.synchronized {
      bankAccount.amount -= price
    }
  }

  def demoBankingProblem(): Unit = {
    (1 to 100000).foreach { _ =>
      val account = BankAccount(50000)

      val thread1 = new Thread(() => buySafe(account, "shoes", 3000))
      val thread2 = new Thread(() => buySafe(account, "iPhone", 4000))

      thread1.start()
      thread2.start()

      thread1.join()
      thread2.join()

      if (account.amount != 43000)
        println(s"I have just broken the bank: ${account.amount}")
    }
  }

  demoBankingProblem()
}
