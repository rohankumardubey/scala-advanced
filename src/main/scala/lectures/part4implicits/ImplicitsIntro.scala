package lectures.part4implicits

object ImplicitsIntro extends App {

  /**
   * Implicit Methods
   */
  val stringPair = "Daniel" -> "555"
  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet = s"Hi, my name is $name"
  }

  implicit def fromStringToPerson(str: String): Person = Person(str)

  println("Peter".greet)
  println(fromStringToPerson("Peter").greet)

  /**
   * Implicit Parameters
   */
  def increment(x: Int)(implicit amount: Int): Int = x + amount

  implicit val defaultAmount: Int = 10

  println(increment(2)) // 12
}
