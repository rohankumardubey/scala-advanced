package lectures.part2afp

object CurryingAndPartiallyAppliedFunctions extends App {

  def incrementMethod(x: Int): Int = x + 1

  val incrementFunction = (x: Int) => x + 1

  val three = incrementFunction(2)
  println(three) // 3

  /*
     // what the compiler does
     val incrementFunction = new Function1[Int, Int] {
       override def apply(x: Int): Int = x + 1
     }
     val three = incrementFunction.apply(2)
   */

  // eta-expansion: Converting a method to function
  val incrementF1 = incrementMethod _

  val incrementF2: Int => Int = incrementMethod

  /*
    // compiler will generate below piece of code
    val incrementF = (x: Int) => incrementMethod(x)
  */


  // One more example of ETA-EXPANSION where a method is converted to a function value when passed to the higher order function
  List(1, 2, 3).map(incrementMethod)

  // Curried Method
  def curriedAdder(x: Int)(y: Int): Int = x + y

  // Function Value | Lifting | ETA-EXPANSION
  val add4: Int => Int = curriedAdder(4)

  val five = add4(1)
  println(five) // 5

  println(List(1, 2, 3).map(curriedAdder(4)))

  def threeArgAdder(x: Int)(y: Int)(z: Int): Int = x + y + z

  val twoArgsRemaining = threeArgAdder(2) _

  val ten = twoArgsRemaining(3)(5)
  println(ten) // 10

  val oneArgRemaining = threeArgAdder(2)(3) _

  val seven = oneArgRemaining(2)
  println(seven) // 7

  // Usage of Underscore

  def concatenate(a: String, b: String, c: String): String = a + b + c

  val insertName = concatenate("Hello, I am ", _: String, ", How are you ?")

  println(insertName("Daniel")) // Hello, I am Daniel, How are you ?

  val fillInTheBlanks = concatenate("Hello ", _: String, _: String)

  println(fillInTheBlanks("Daniel", ", Scala is awesome")) // Hello Daniel, Scala is awesome
}
