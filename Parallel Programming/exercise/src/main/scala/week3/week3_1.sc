import scala.collection._
import scala.collection.parallel.immutable.ParVector
import java.util.concurrent._
import scala.collection.convert.wrapAsScala._
import org.scalameter._

object exercise {
  //----------------------------

  def sum(xs:Array[Int]):Int={
      xs.par.foldLeft(0)(_ + _)
  }

  val hh = sum(Array[Int](1, 2, 3, 4))
  print(hh)

  //----------------------------

  def intersection(a: GenSet[Int], b:GenSet[Int])={
//    val result = mutable.Set[Int]()
    val result = new ConcurrentSkipListSet[Int]()
    for (x <- a) if (b contains x) result += x
    result
  }

  val seqres = intersection((0 until 1000).toSet, (0 until 1000 by 4).toSet)
  val parres = intersection((0 until 1000).par.toSet, (0 until 1000 by 4).par.toSet)
  log(s"Sequential result - ${seqres.size}")
  log(s"Parallel result - ${parres.size}")

  //----------------------------

  val b = Set(1,2,3)
  val a = Set(3,4,1)
  val c = if (a.size < b.size) a.filter(b(_))
          else b.filter(a(_))

  //----------------------------

  def tt(a: Int): Int = {
    a * 2
  }
  val kk: GenSeq[Int] = ParVector(1, 2, 3, 4, 5, 6, 7, 8, 9)
  val jj: GenMap[Int, GenSeq[Int]] = kk.groupBy(x => tt(x))
  print(jj)
  val child: Unit = jj.foreach{ case (key,value) => print(key+",")}

  //----------------------------
}
