
object monads {
  // ---------------------------------------------------------------
  trait User {
    def child: Option[User]
  }

  object UserService {
    def loadUser(name: String): Option[User] = Some(new User {
      def child: None.type = None
    })
  }

  val getChild = {
    (user: User) => user.child
  }

  val result = UserService.loadUser("hi").flatMap(getChild).flatMap(getChild)

  // ---------------------------------------------------------------

  /** Try **/
  /* Try resembles Option but for the Failure case, it returns an Exception
   * Try is used to pass results of computation that can fail with an exception between
   * threads and computers.
   * Try is not a monad because the Left Unit Law fails;
   * Try(expr) flatMap f != f(expr)
   * the left-hand side will never raise a non-fatal exception whereas the right-hand side will
   * raise any exception thrown by expr or f
   */
  /*abstract class Try[+T] {
    def flatMap[U](f: T => Try[U]): Try[U] = this match {
      case Success(x) => try f(x) catch {
        case NonFatal(ex) => Failure(ex)
      }
      case fail: Failure => fail
    }

    def map[U](f: T => U): Try[U] = this match {
      case Success(x) => Try(f(x))
      case fail: Failure => fail //failure gets propagated in the result
    }
  }

  case class Success[T](x: T) extends Try[T]

  case class Failure(ex: Exception) extends Try[Nothing]

  case class NonFatal(ex: Exception) extends Throwable

  object Try {
    def apply[T](expr: => T): Try[T] =
      try Success(expr)
      catch {
        case NonFatal(ex) => Failure(ex)
      }
  }

  def f: Int => Try[Int] = {
    (x:Int) => Try(3)
  }
  def expr = 5/0
  // val r = f(expr)
  val d = Try(expr).flatMap(f)
*/
}

