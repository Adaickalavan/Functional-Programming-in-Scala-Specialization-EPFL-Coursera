import math.abs

object Lecture1{
  val tolerance = 0.0001
  def isCloseEnough(x:Double, y:Double)={
    abs((x-y)/x)/x < tolerance
  }
  def fixedPoint(f:Double => Double)(firstGuess:Double):Double ={
    def iterate(guess:Double):Double={
      val next = f(guess)
      if (isCloseEnough(guess,next))
        next
      else
        iterate(next)
    }
    iterate(firstGuess)
  }
  fixedPoint(x=>1+x/2)(1)

  def averageDamp(f:Double=>Double): Double => Double ={
    def g(x:Double):Double={
      (x + f(x))/2
    }
    g
  }

  def sqrt(x:Double)={
    fixedPoint(averageDamp(y=>x/y))(1)
  }
  sqrt(2)

}
