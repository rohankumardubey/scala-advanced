package lectures.part4implicits

import scala.annotation.tailrec

object PimpMyLibrary extends App {
  implicit class RichInt(value: Int) {
    def isEven: Boolean = value % 2 == 0

    def times(function: () => Unit): Unit = {
      @tailrec
      def timesAux(n: Int): Unit = {
        if (n <= 0) ()
        else {
          function()
          timesAux(n - 1)
        }
      }

      timesAux(value)
    }

    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] = {
        if (n <= 0) List()
        else concatenate(n - 1) ++ list
      }

      concatenate(value)
    }
  }

  println(42.isEven)
  // println(new RichInt(42).isEven)

  5 times (() => println("Hello Scala"))
  /*
  Hello Scala
  Hello Scala
  Hello Scala
  Hello Scala
  Hello Scala
   */

  println(3 * List(1, 2))
  // List(1, 2, 1, 2, 1, 2)

  implicit class RichString(value: String) {
    def asInt: Int = Integer.valueOf(value)

    def encrypt(cypherDistance: Int): String = value.map(c => (c + cypherDistance).asInstanceOf[Char])
  }

  println("42".asInt) // 42

  println("abcd".encrypt(5)) // fghi


  implicit def stringToInt(s: String): Int = Integer.valueOf(s)

  println("6" / 2)
}
