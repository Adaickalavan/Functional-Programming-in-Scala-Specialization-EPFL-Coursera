
import math.Ordering

object week5{
  val fruit = List("apple","pineapple","orange","banana")
  val nums = List(1,2,3)
  val diag3 = List(List(1,0,0),List(0,1,0),List(0,0,1))
  val empty = List()
  val nums2 = List(2,-4,5,7,1)

  def removeAt[T](n: Int, xs:List[T]) ={
    (xs take n) ::: (xs drop n+1)
  }

  def flatten(xs:List[Any]):List[Any] ={
    xs match {
      case List() => List()
      case (x:List[Any]) :: y => flatten(x) ::: flatten(y)
      case x :: y => x :: flatten(y)
    }
  }

  def msort[T](xs: List[T])(implicit ord:Ordering[T]): List[T] ={
    val n = xs.length/2
    if (n==0) xs
    else {
      def merge(xs:List[T], ys:List[T]):List[T] =
        (xs,ys) match {
          case (Nil,ys) => ys
          case (xs, Nil) => xs
          case (x::xs1, y::ys1) =>
            if (ord.lt(x,y)) x :: merge(xs1,ys)
            else y :: merge(xs, ys1)
        }
      val (fst,snd)=xs.splitAt(n)
      merge(msort(fst)(ord),msort(snd)(ord))
    }
  }

  removeAt[Int](1, nums)
  flatten(List(List(1,1),2,List(3,List(5,8))))
  msort(nums2)
  msort(fruit)(Ordering.String)

  val data = List("a","a","a","b","c","c","a")

  def pack[T](xs: List[T]):List[List[T]] = {
    xs match {
      case Nil => Nil
      case x :: xs1 =>
        val (first,rest) = xs span (y => y == x)
        first :: pack(rest)
    }
  }

  def encode[T](xs:List[T]): List[(T,Int)] ={
    pack(xs).map(ys => (ys.head,ys.length))
  }

  pack(data)
  encode(data)

  def concat[T](xs:List[T],ys:List[T]):List[T] ={
    xs.foldLeft(ys)((a,b) => b :: a)
  }

  concat(nums,nums2)

}