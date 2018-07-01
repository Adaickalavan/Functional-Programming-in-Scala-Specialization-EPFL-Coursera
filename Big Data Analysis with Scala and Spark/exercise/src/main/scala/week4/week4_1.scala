import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql._
import org.apache.spark.sql.types._

case class Employee(id: Int, fname:String, lname:String, work:Array[Int], city:String, age:Int)


object exercise {

  // Create spark session
  val spark:SparkSession = SparkSession.builder()
    .appName("MyApp")
    .config("spark.master", "local")
    .getOrCreate()
  // Set log message level
  spark.sparkContext.setLogLevel("ERROR")

  // Create spark context
  import spark.implicits._

  def transformations= {
    // Define dataset
    val emp = Seq(Employee(12, "Joe", "Smith", Array(38, 67, 89), "New York", 34),
                  Employee(645, "Slate", "Markham", Array(28, 3), "Sydney", 2),
                  Employee(12, "Sally", "Owens", Array(48, 1, 0), "New York", 12),
                  Employee(221, "Joe", "Walker", Array(21), "Sydney", 89),
                  Employee(12, "Joe", "Runner", Array(21), "Sydney", 89),
                  Employee(645, "Slate", "Ontario", Array(12, 3), "Wellington", 25))
    // Convert dataset to RDD
    val empRDD = spark.sparkContext.parallelize(emp)
    // Convert dataset to Dataframe
    val empDF = empRDD.toDF
    // Convert Dataframe to RDD
    val empRDDfromDF = empDF.rdd

    // Visualize Dataframe
    empDF.printSchema()
    empDF.show()

    // Transformations on dataframe
    val selectedemp = empDF.select($"id",$"lname")
      .where($"city" === "Sydney")
      .orderBy($"id")
    selectedemp.show()

    val rankedemp = empDF.groupBy($"id")
      .max("age")
    rankedemp.show()

    val rank = empDF.groupBy($"id",$"fname")
      .agg(count($"id"))
      .orderBy($"fname",$"count(id)".desc)
    rank.show()

  }


  /** Main function */
  def main(args: Array[String]): Unit = {
    transformations
    spark.stop()
  }

}