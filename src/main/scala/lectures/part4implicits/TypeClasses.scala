package lectures.part4implicits

import java.util.Date

object TypeClasses extends App {

  // ==========================================

  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHTML: String = s"<div>$name ($age yo) <a href=$email/></div>"
  }

  println(User("John", 32, "john@abc.com").toHTML)

  // ==========================================

  /**
   * Type Class
   */
  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div>${user.name} (${user.age} yo) <a href=${user.email}/></div>"
  }

  val john = User("John", 32, "john@abc.com")

  println(UserSerializer.serialize(john))
  // <div>John (32 yo) <a href=john@abc.com/></div>

  /**
   * Advantages
   */

  // 1. We can define serializers for other types as well apart from User
  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div>${date.toString}/></div>"
  }

  // 2. We can define multiple serializers for a type
  object PartialUserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div>${user.name}/></div>"
  }


  // ==========================================

  object HTMLSerializer {
    def apply[T](implicit serializer: HTMLSerializer[T]): HTMLSerializer[T] = serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div>$value</div>"
  }

  println(HTMLSerializer[Int].serialize(42))

  // ==========================================

  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  println(john.toHTML) // <div>John (32 yo) <a href=john@abc.com/></div>

  println(2.toHTML(IntSerializer)) // <div>2</div>

  /*
    Advantages:
    - extend to new types
    - choose implementation
    - super expressive
   */

  // ==========================================

  trait Equal[T] {
    def isEqual(a: T, b: T): Boolean
  }

  implicit object NameEquality extends Equal[User] {
    override def isEqual(a: User, b: User): Boolean = a.name == b.name
  }

  implicit class TypeSafeEqual[T](value: T) {
    def ===(other: T)(implicit equalizer: Equal[T]): Boolean = equalizer.isEqual(value, other)

    def !==(other: T)(implicit equalizer: Equal[T]): Boolean = !equalizer.isEqual(value, other)
  }

  val anotherJohn = User("John", 32, "john@abc.com")
  println(john === anotherJohn)
  // new TypeSafeEqual[User](john).===(anotherJohn)(NameEquality)
}
