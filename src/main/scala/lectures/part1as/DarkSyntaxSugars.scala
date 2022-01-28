package lectures.part1as

import scala.util.Try

object DarkSyntaxSugars extends App {

  /**
   * 1. Methods with single param can be called via curly braces
   */
  def singleArgMethod(arg: Int): String = s"$arg little ducks.."

  val description = singleArgMethod(42)

  val descriptionSyntaxSugar = singleArgMethod {
    // code
    42
  }

  val aTryInstance = Try {
    // throw new RuntimeException
  }

  List(1, 2, 3).map { x =>
    x + 1
  }

  /**
   * 2. Single Abstract Method Pattern:
   * Instance of traits with single method can be reduced to lambdas
   */

  // Example-1
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int): Int = x + 1
  }

  val anInstanceWithSyntaxSugar: Action = (x: Int) => x + 1

  // Example-2
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Hello Scala")
  })

  val aThreadWithSyntaxSugar = new Thread(() => println("Hello Scala"))

  // Example-3
  abstract class AnAbstractType {
    def implemented: Int = 23

    def f(a: Int): Unit
  }

  val anAbstractInstance: AnAbstractType = (x: Int) => println(x)

  /**
   * 3. :: and #: methods are special
   */
  val prependedList = 2 :: List(3, 4)
  // 2.::(List(3, 4)) --> Wrong
  // List(3,4).::(2)  --> Right

  // Any method ending with : is right associative
  val list = 1 :: 2 :: 3 :: 4 :: List(5)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this
  }

  val myStream = 1 -->: 2 -->: 3 -->: 4 -->: new MyStream[Int]

  /**
   * 4. Multi Word Method Naming
   */
  class TeenGirl(name: String) {
    def `and then said`(gossip: String): Unit = println(s"$name said $gossip")
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet"

  /**
   * 5. Infix Types
   */

  // Example-1
  class Composite[A, B]

  val composite: Composite[Int, String] = new Composite[Int, String]

  val compositeSyntaxSugar: Int Composite String = new Composite[Int, String]

  // Example-2
  class -->[A, B]

  val towards: Int --> String = new -->[Int, String]

  /**
   * 6. update() is special just like apply()
   * used in mutable collections
   */
  val anArray = Array(1, 2, 3)

  // Below two statements are same
  anArray(2) = 7
  anArray.update(2, 7)

  /**
   * 7. Setters for mutable containers
   */
  class Mutable {
    private var internalMember: Int = 0

    def member: Int = internalMember

    def member_=(value: Int): Unit = {
      internalMember = value
    }
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // rewritten as aMutableContainer_=(42)
}
