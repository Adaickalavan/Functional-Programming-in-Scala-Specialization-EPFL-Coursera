
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

  def msort[T](xs: List[T])(lt:(T,T)=>Boolean): List[T] ={
    val n = xs.length/2
    if (n==0) xs
    else {
      def merge(xs:List[T], ys:List[T]):List[T] =
        (xs,ys) match {
          case (Nil,ys) => ys
          case (xs, Nil) => xs
          case (x::xs1, y::ys1) =>
            if (lt(x,y)) x :: merge(xs1,ys)
            else y :: merge(xs, ys1)
        }
      val (fst,snd)=xs.splitAt(n)
      merge(msort(fst)(lt),msort(snd)(lt))
    }
  }

  removeAt[Int](1, nums)
  flatten(List(List(1,1),2,List(3,List(5,8))))
  msort(nums2)((x:Int,y:Int)=>x<y)
  msort(fruit)((x:String,y:String)=> x.compareTo(y) < 0)
}