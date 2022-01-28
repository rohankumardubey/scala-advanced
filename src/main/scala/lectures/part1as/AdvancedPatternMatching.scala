package lectures.part1as

object AdvancedPatternMatching extends App {

  /**
   * Custom Pattern matching
   */
  class Person(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] = Some((person.name, person.age))

    def unapply(age: Int): Option[String] = Some(if (age < 21) "Minor" else "Major")
  }

  val bob = new Person("Bob", 25)

  val greeting = bob match {
    case Person(name, age) => s"Hi my name is $name and I am $age years old"
  }

  println(greeting) // Hi my name is Bob and I am 25 years old

  val legalStatus = bob.age match {
    case Person(status) => s"My legal status is $status"
  }

  println(legalStatus) // My legal status is Major


  /**
   * Infix Pattern
   */
  case class Or[A, B](a: A, b: B)

  val either = Or(2, "two")

  val humanDescription = either match {
    // case Or(number, string) => s"$number is written as $string"
    case number Or string => s"$number is written as $string"
  }

  println(humanDescription) // 2 is written as two


  /**
   * Decomposing Sequences
   */
  val numbers = Seq(1, 2, 3, 4, 5)

  val vararg = numbers match {
    case List(n, _*) => s"Starting with $n"
  }

  println(vararg) // Starting with 1

  abstract class MyList[+A] {
    def head: A = ???

    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]

  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] = {
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
    }
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))

  val decomposed = myList match {
    case MyList(n, _*) => s"Starting with $n"
  }

  println(decomposed) // Starting with 1


  /**
   * Custom return types for unapply
   */
  abstract class Wrapper[T] {
    def isEmpty: Boolean

    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = {
      new Wrapper[String] {
        override def isEmpty: Boolean = false

        override def get: String = person.name
      }
    }
  }

  val matchPerson = bob match {
    case PersonWrapper(name) => s"This person name is $name"
    case _ => "An alien"
  }

  println(matchPerson) // This person name is Bob
}
