import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WikipediaRanking {

  val langs = List(
    "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
    "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")

  val conf: SparkConf = new SparkConf()
    .setAppName("WikipediaRanking")
    .setMaster("local[*]")  // Run in local machine with maximum threads
  val sc: SparkContext = new SparkContext(conf)

  val filepath = "C:/Deego/Functional Programming in Scala Specialization EPFL/Big Data Analysis with Scala and Spark/exercise/src/main/resources/wikipedia/wikipedia_small.dat"
  val wikiRdd = sc.textFile(filepath)
  val numOfElem = wikiRdd.count
  val gg = wikiRdd.take(2).drop(1)
  val gf = wikiRdd.take(1)


  def rankLangsReduceByKey(langs: List[String], rdd: RDD[String]): List[(String, Int)] = {
    rdd.flatMap(article => {
      langs.filter(lang => article.split(" ").contains(lang))
    }.map(lang => (lang, 1))
    ).reduceByKey((x,y) => x+y)
      .sortBy(_._2)
      .collect()
      .toList
      .reverse
  }

  rankLangsReduceByKey(langs, wikiRdd)




}