
import org.scalameter._

object exercise{

  val time1 = measure{
    (0 until 1000000).map(math.pow(_,5)).sum
  }
  println(s"The operation took: $time1 ms")

  val time2 = withWarmer(new Warmer.Default).measure{
    (0 until 1000000).map(math.pow(_,5)).sum
  }
  println(s"The operation took: $time2 ms")

  val time3 = config(Key.exec.minWarmupRuns->30,Key.exec.maxWarmupRuns->60).withWarmer(new Warmer.Default).measure{
    (0 until 1000000).map(math.pow(_,5)).sum
  }
  println(s"The operation took: $time3 ms")

}