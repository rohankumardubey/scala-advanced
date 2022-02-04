package lectures.part2afp

object PartialFunctions extends App {

  /**
   * Normal Function
   */
  val aFunction: Int => Int = x => x + 1


  val aFussyFunction: Int => Int = x => {
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException
  }

  class FunctionNotApplicableException extends RuntimeException


  val aNicerFussyFunction: Int => Int = x => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }

  println(aPartialFunction(2)) // 56

  // println(aPartialFunction(7))
  // scala.MatchError: 7 (of class java.lang.Integer)


  /**
   * Partial Functions Utilities
   */

  println(aPartialFunction.isDefinedAt(50)) // false
  println(aPartialFunction.isDefinedAt(2)) // true


  val liftedPartialFunction = aPartialFunction.lift
  println(liftedPartialFunction(50)) // None
  println(liftedPartialFunction(2)) // Some(56)


  val partialFunctionChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }
  println(partialFunctionChain(2)) // 56
  println(partialFunctionChain(45)) // 67


  // Partial Functions extends Normal Functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // Higher Order Functions accepts partial functions as well
  val aMappedList = List(1, 2, 3).map {
    case 1 => 45
    case 2 => 78
    case 3 => 1000
  }
  println(aMappedList) // List(45, 78, 1000)
}
