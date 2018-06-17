

object exercise{

  class sector(val matrix:Array[List[Int]]) {
    def combine(that: sector): sector = {
      val temp = matrix.zip(that.matrix).map(x => x._1 ++ x._2)
      println(temp.mkString(" "))
      this
    }

    override def toString: String = {
      matrix.mkString(" ")
    }
  }

  val kk: Array[List[Int]] = Array(List(1),List(2,7,9))
  val rr: Array[List[Int]] = Array(List(3),List(5,6,7))
  val nn = new sector(kk)
  val ee = new sector(rr)
  val jj = nn.combine(ee)
  println(jj)

}