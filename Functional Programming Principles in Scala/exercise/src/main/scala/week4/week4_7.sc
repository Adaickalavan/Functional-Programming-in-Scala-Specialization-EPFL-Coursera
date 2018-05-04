

object week4_7{
  def isort(xs: List[Int]): List[Int] ={
    xs match {
      case List() => List()
      case y :: ys => insert(y, isort(ys))
    }
  }

  def insert(x: Int, xs: List[Int]): List[Int] = {
    xs match {
      case List() => List(x)
      case y :: ys => {
        if (x <= y)
          x :: xs
        else
          y :: insert(x, ys)
      }
    }
  }


  isort(List(5,3,7,100,8,3))

}