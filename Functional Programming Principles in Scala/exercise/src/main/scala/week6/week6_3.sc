
object maps{
  val romanNumerals = Map("I"->1, "V"->5, "X"->10)
  val fruit = List("apple","pear","orange","pineapple")
  val capitalOfCountry = Map("US"->"Washington","Switzerland"->"Bern")
}

maps.capitalOfCountry get "Andorra"
maps.capitalOfCountry.get("US")
maps.fruit.groupBy(_.head)
maps.fruit.groupBy(_.tail)

class poly(val terms0:Map[Int,Double]){

  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  val terms = terms0.withDefaultValue(0.0)
  def + (other:poly) = new poly(terms ++ other.terms.map(adjust))

  def adjust(term: (Int,Double)):(Int,Double) = {
    val (exp, coeff) = term
    //terms.get(exp) match {
    //case Some(coeff1) => exp -> (coeff + coeff1)
    //case None => exp -> coeff
    //}
    exp -> (coeff + terms(exp))
  }

  override def toString: String = {
    (for ((exp,coeff)<-terms.toList.sorted.reverse) yield coeff+"x^"+exp).mkString(" + ")
  }
}


class polyFoldLeft(val terms0:Map[Int,Double]){

  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  val terms = terms0.withDefaultValue(0.0)
  def + (other:polyFoldLeft) = {
    new polyFoldLeft(other.terms.foldLeft(terms)(addTerm))
  }
  def addTerm(terms:Map[Int,Double],term:(Int,Double)):Map[Int,Double]={
    val (exp, coeff) = term
    terms + (exp -> (coeff + terms(exp)))
  }

  override def toString: String = {
    (for ((exp,coeff)<-terms.toList.sorted.reverse) yield coeff+"x^"+exp).mkString(" + ")
  }
}

val p1 = new poly(Map(1 -> 2.0, 3 -> 4.0, 5 -> 6.2))
val p2 = new poly(Map(0 -> 3.0, 3 -> 7.0))
val p3 = new poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2)
val p4 = new polyFoldLeft(1 -> 2.0, 3 -> 4.0, 5 -> 6.2)
val p5 = new polyFoldLeft(0 -> 3.0, 3 -> 7.0)

p1 + p2
p3 + p2
p1.terms(3)
p4 + p5