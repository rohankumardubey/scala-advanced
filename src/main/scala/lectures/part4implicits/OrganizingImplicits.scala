package lectures.part4implicits

object OrganizingImplicits extends App {
  case class Person(name: String, age: Int)

  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66)
  )

  object Person {
    implicit val alphabeticalOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  println(persons.sorted)
  // List(Person(Amy,22), Person(John,66), Person(Steve,30))

  object AgeOrdering {
    implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.age < b.age)
  }

  import AgeOrdering._

  println(persons.sorted)
  // List(Person(Amy,22), Person(Steve,30), Person(John,66))
}
