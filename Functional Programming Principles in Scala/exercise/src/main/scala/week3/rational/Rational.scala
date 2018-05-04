package week3.rational

class Rational(x: Int, y: Int) {
  require(y != 0, "Denominator must be nonzero")

  private val g = gcd(x, y)

  def this(x: Int) = this(x, 1)

  def max(that: Rational) = if (this < that) that else this

  def <(that: Rational) = numer * that.denom < that.numer * denom

  def + (that: Rational) = {
    new Rational(numer * that.denom + that.numer * denom, denom * that.denom)
  }

  def numer = x / g

  def denom = y / g

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  override def toString = {
    val g = gcd(numer, denom)
    "%d/%d".format(numer/g, denom/g)

  }
}
