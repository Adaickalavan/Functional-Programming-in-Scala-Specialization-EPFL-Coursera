import week3.list._

object nth{
  def nth[T](n:Int, xs:List[T]): T ={
    if (n==0) xs.head
    else nth(n-1,xs.tail)

//    else if ()
//      throw new IndexOutOfBoundsException("Index out of bound")

  }
  val list = new Cons(1,new)
}
