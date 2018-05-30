
object primes {
  def from(n:Int):Stream[Int] = {
    n #:: from(n+1)
  }
  val nats = from(0)
  val m4s: Stream[Int] = nats.map(_ * 4)

  (m4s take 10).toList

  def sieve(s:Stream[Int]):Stream[Int] = {
    s.head #:: sieve(s.tail.filter(_%s.head != 0))
  }

  val primes = sieve(from(2))
  primes.take(100).toList

  def sqrtStream(x:Double):Stream[Double] = {
    def improve(guess:Double): Double = (guess + x/guess)/2
    lazy val guesses: Stream[Double] = {
      1 #:: (guesses.map(improve))
    }
    guesses
  }
  def isGoodEnough(guess:Double, x:Double):Boolean={
    math.abs((guess*guess-x)/x) < 0.0001
  }

  sqrtStream(4).filter(isGoodEnough(_,4)).take(10).toList
  

}