package lectures.part4implicits

object Givens extends App {
  val aList = List(1, 2, 3, 4)
  val anOrderedList = aList.sorted

  // implicit val

  // Scala 2 Style
  object Implicits {
    implicit val descendingOrder: Ordering[Int] = Ordering.fromLessThan(_ > _)
  }

  // Scala 3 Style
  object Givens {
    given descendingOrder: Ordering[Int] = Ordering.fromLessThan(_ > _)
    // givens is equivalent to implicit val
  }

  // instantiating an anonymous class

  // Scala 2 Style
  object GivenAnonymousClass {
    implicit def descendingOrdering_v2: Ordering[Int] = new Ordering[Int] :
      override def compare(x: Int, y: Int): Int = y - x
  }

  // Scala 3 Style
  object GivenWith {
    given descendingOrder_v3: Ordering[Int] with {
      override def compare(x: Int, y: Int): Int = y - x
    }
  } // imports specific given


  // implicit arguments

  // Scala 2 Style
  def extremes[A](list: List[A])(implicit ordering: Ordering[A]): (A, A) = {
    val sortedList = list.sorted
    (sortedList.head, sortedList.last)
  }

  // Scala 3 Style
  def extremes_v2[A](list: List[A])(using ordering: Ordering[A]): (A, A) = {
    val sortedList = list.sorted
    (sortedList.head, sortedList.last)
  }

  // implicit def

  // scala 2 style
  trait Combinator[A] {
    def combine(x: A, y: A): A
  }

  implicit def listOrdering[A](implicit simpleOrdering: Ordering[A], combinator: Combinator[A]): Ordering[List[A]] = new Ordering[List[A]] {
    override def compare(x: List[A], y: List[A]): Int = {
      val sumX = x.reduce(combinator.combine)
      val sumY = y.reduce(combinator.combine)
      simpleOrdering.compare(sumX, sumY)
    }
  }

  // Scala 3 style
  given listOrdering_v2[A] (using simpleOrdering: Ordering[A], combinator: Combinator[A]): Ordering[List[A]] with {
    override def compare(x: List[A], y: List[A]): Int = {
      val sumX = x.reduce(combinator.combine)
      val sumY = y.reduce(combinator.combine)
      simpleOrdering.compare(sumX, sumY)
    }
  }

  // implicit conversions(abused in Scala 2)

  // Scala 2 Style
  case class Person(name: String) {
    def greet(): String = s"Hi, my name is $name"
  }

  implicit def string2Person(string: String): Person = Person(string)

  val danielGreet = "Daniel".greet()

  // Scala 3 Style

  import scala.language.implicitConversions

  given string2PersonConversion: Conversion[String, Person] with {
    override def apply(x: String): Person = Person(x)
  }
}
