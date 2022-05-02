package lectures.part4implicits

object ExtensionMethods extends App {
  case class Person(name: String) {
    def greet(): String = s"Hi, my name is $name"
  }

  extension (string: String) {
    def greetAsPerson(): String = Person(string).greet()
  }

  println("Daniel".greetAsPerson()) // Hi, my name is Daniel

  extension (value: Int) {
    def isEven: Boolean = value % 2 == 0
  }

  println(4.isEven) // true

  extension[A] (list: List[A]) {
    def ends: (A, A) = (list.head, list.last)
    def extremes(using ordering: Ordering[A]): (A, A) = list.sorted.ends
  }

  println(List(1, 2, 3, 4, 5).ends) // (1,5)
}
