package lectures.part3concurrency

import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}

object FuturesAndPromises extends App {
  def generateMagicNumber(): Int = {
    Thread.sleep(3000)
    23
  }

  val aFuture: Future[Int] = Future {
    generateMagicNumber()
  }

  def multiply(multiplier: Int): Future[Int] =
    if (multiplier == 0) {
      Future.successful(0)
    } else {
      Future(multiplier * generateMagicNumber())
    }

  def divide(divider: Int): Future[Int] =
    if (divider == 0) {
      Future.failed(new IllegalArgumentException("Don't divide by zero"))
    } else {
      Future(generateMagicNumber() / divider)
    }

  def tryDivide(divider: Int): Future[Int] = Future.fromTry(Try {
    generateMagicNumber() / divider
  })

  /**
   * Await.result()
   */
  val maxWaitTime: FiniteDuration = Duration(5, TimeUnit.SECONDS)
  val magicNumber: Int = Await.result(aFuture, maxWaitTime)

  println(magicNumber) // 23


  /**
   * Callbacks
   */
  aFuture.onComplete {
    case Success(number) => println("Succeed with: " + number)
    case Failure(exception) => println("Failed with: " + exception.getMessage)
  }

  aFuture.foreach(result => println("Succeed with: " + result))

  /**
   * Transform Future
   */
  val nextMagicNumberF: Future[Int] = aFuture.map(_ + 1)
}
