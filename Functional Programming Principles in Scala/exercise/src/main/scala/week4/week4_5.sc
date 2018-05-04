object week4_5{

  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Var(x: String) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(e:Expr):String = {
    def format(e:Expr):String = {
      e match{
        case Sum(_,_) => "(" + show(e) + ")"
        case _ => show(e)
      }
    }
    e match{
      case Number(x) => x.toString
      case Sum(l,r) => show(l) + "+" + show(r)
      case Var(x) => x
      case Prod(l,r) => format(l) + "*" + format(r)
    }
  }

  show(Sum(Number(1),Number(64)))
  show(Sum(Prod(Number(2),Var("x")),Var("y")))
  show(Prod(Sum(Number(2),Var("x")),Var("y")))

}