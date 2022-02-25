package lectures.part2afp

object Monads extends App {

  // Our own Try Monad

  trait Attempt[+A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }

  object Attempt {
    def apply[A](a: => A): Attempt[A] = {
      try {
        Success(a)
      } catch {
        case e: Throwable => Failure(e)
      }
    }
  }

  case class Success[+A](value: A) extends Attempt[A] {
    override def flatMap[B](f: A => Attempt[B]): Attempt[B] = {
      try {
        f(value)
      } catch {
        case e: Throwable => Failure(e)
      }
    }
  }

  case class Failure(e: Throwable) extends Attempt[Nothing] {
    override def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = this
  }

  /**
   * left-identity:
   *
   * unit(x).flatMap(f) = f(x)
   * Attempt(x).flatMap(f) = f(x) // Success case
   * Success(x).flatMap(f) = f(x) // Proved
   */

  /**
   * right-identity:
   *
   * m.flatMap(unit) = m
   * Success(x).flatMap(x => Attempt(x)) = Attempt(x) = Success(x)
   * Failure(e).flatMap(...) = Failure(e)
   */

  /**
   * associativity:
   *
   * m.flatMap(f).flatMap(g) = m.flatMap(x => f(x).flatMap(g))
   *
   * Failure(e).flatMap(f).flatMap(g) = Failure(e)
   * Failure(e).flatMap(x => f(x).flatMap(g)) = Failure(e)
   *
   * Success(v).flatMap(f).flatMap(g) = f(v).flatMap(g)
   * Success(v).flatMap(x => f(x).flatMap(g)) = f(v).flatMap(g)
   */
}