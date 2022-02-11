package lectures.part2afp

object LazyEvaluation extends App {

  lazy val e = throw new RuntimeException

  lazy val x = {
    println("Hello")
    42
  }

  println(x)
  /*
    Hello
    42
   */

  println(x)
  /*
    42
   */

  /**
   * Examples of Implications
   */

  // Side Effects

  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }

  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition

  println(if (simpleCondition && lazyCondition) "yes" else "no") // no
  /*
    Here the side effect statement i.e. Boo is not printed
    Because the first condition i.e. simpleCondition is always false
    So, compiler even did not care of evaluation the lazyCondition and returned no
   */


  // In conjunction with Call By Name

  def retrieveMagicValue: Int = {
    println("Waiting")
    Thread.sleep(1000)
    42
  }

  def byNameMethod(n: => Int): Int = n + n + n

  def effectiveByNameMethod(n: => Int): Int = {
    lazy val t = n
    t + t + t
  }

  println(byNameMethod(retrieveMagicValue))
  /*
    Waiting
    Waiting
    Waiting
    126
   */

  println(effectiveByNameMethod(retrieveMagicValue))
  /*
    Waiting
    126
   */


  /**
   * Lazy evaluation with filtering
   */

  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30 ?")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20 ?")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)

  val lt30 = numbers.filter(lessThan30)
  println(s"lt30 --> $lt30")

  val gt20 = lt30.filter(greaterThan20)
  println(s"gt20 --> $gt20")

  /*
    1 is less than 30 ?
    25 is less than 30 ?
    40 is less than 30 ?
    5 is less than 30 ?
    23 is less than 30 ?
    lt30 --> List(1, 25, 5, 23)
    1 is greater than 20 ?
    25 is greater than 20 ?
    5 is greater than 20 ?
    23 is greater than 20 ?
    gt20 --> List(25, 23)
   */

  println("================================")

  val lt30Lazy = numbers.withFilter(lessThan30)

  val gt20Lazy = lt30Lazy.withFilter(greaterThan20)

  println(gt20Lazy)
  // scala.collection.IterableOps$WithFilter@2ff4f00f

  gt20Lazy.foreach(println)
  /*
    1 is less than 30 ?
    1 is greater than 20 ?
    25 is less than 30 ?
    25 is greater than 20 ?
    25
    40 is less than 30 ?
    5 is less than 30 ?
    5 is greater than 20 ?
    23 is less than 30 ?
    23 is greater than 20 ?
    23
   */

  /**
   * For-Comprehension uses withFilter with guards
   */
  for {
    a <- List(1, 2, 3) if (a % 2 == 0)
  } yield a + 1

  List(1, 2, 3).withFilter(_ % 2 == 0).map(_ + 1)
}
