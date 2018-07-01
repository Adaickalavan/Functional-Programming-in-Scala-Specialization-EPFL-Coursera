import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object exercise {

  val conf: SparkConf = new SparkConf()
    .setAppName("WikipediaRanking")
    .setMaster("local[*]")  // Run in local machine with maximum threads
  val sc: SparkContext = new SparkContext(conf)

  val newMeans = new Array[Iterable[Int]](4)
  val tuples: List[(Int, Int)] = List((1,1),(2,2),(1,3),(3,4),(3,5))
  val rdd: RDD[(Int, Int)] = sc.parallelize(tuples).persist()
  val rddNew: Array[(Int, Iterable[Int])] = rdd.groupByKey().collect()

  println(newMeans.deep.mkString(" "))

  for {
    (ind, means)<- rddNew
  } newMeans(ind) = means

  println(newMeans.deep.mkString(" "))

}