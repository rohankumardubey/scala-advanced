package lectures.part3concurrency

import java.util.concurrent.Executors

object JVMConcurrencyIntro extends App {
  val runnable = new Runnable {
    override def run(): Unit = println("Running in Parallel")
  }

  val aThread = new Thread(runnable)

  runnable.run() // Doesn't do anything in parallel
  aThread.start() // Runs the piece of code present in run method in parallel

  aThread.join()

  val threadHello = new Thread(() => (1 to 3).foreach(_ => println("Hello")))
  val threadGoodBye = new Thread(() => (1 to 3).foreach(_ => println("GoodBye")))

  threadHello.start()
  threadGoodBye.start()

  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the thread pool"))

  // pool.shutdown()

  // pool.execute(() => println("Should not appear"))
  // java.util.concurrent.RejectedExecutionException

  pool.shutdownNow()
}
