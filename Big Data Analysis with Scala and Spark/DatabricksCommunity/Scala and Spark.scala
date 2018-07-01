// Databricks notebook source
import org.apache.spark.SparkContext

val sc = SparkContext.getOrCreate()  // Use exsiting SparkContext in DataBricks Community
val rdd:RDD[String] = sc.textFile("/FileStore/tables/wikipedia.dat")
val containsWord = rdd.filter(x => x.contains("you")).persist()
val numberOfTimes = containsWord.count()  //Obtain number of sentences
val firstFew = containsWord.take(1)  //Obtain sentences containing chosen word

// COMMAND ----------

import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession

// Create spark session 
val spark:SparkSession = SparkSession.builder()
                                     .appName("MyApp")
                                     .getOrCreate()

// Create spark context
import spark.implicits._

// Define dataset
case class Employee(id: Int, fname:String, lname:String, work:Array[Int], city:String, age:Int)
val emp = Seq(Employee(12,"Joe","Smith",Array(38,67,89),"New York",34),
                Employee(645,"Slate","Markham",Array(28,3),"Sydney",2),
                Employee(12,"Sally","Owens",Array(48,1,0),"New York",12),
                Employee(221,"Joe","Walker",Array(21),"Sydney",89),
                Employee(12,"Joe","Runner",Array(21),"Sydney",89),
                Employee(645,"Slate","Ontario",Array(12,3),"Wellington",25))
// Convert dataset to RDD 
val empRDD = spark.sparkContext.parallelize(emp)
// Convert dataset to Dataframe
val empDF = emp.toDF
// Convert Dataframe to RDD
val empRDDfromDF = empDF.rdd

// Visualize Dataframe
empDF.printSchema()
empDF.show()

// COMMAND ----------

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

// COMMAND ----------

case class Abo(id: Int, v:(String,String))
case class Loc(id: Int, v:String)

val as = List(Abo(101, ("Ruetli","AG")), Abo(102, ("Brelaz","DemiTarif")),
              Abo(103, ("Gress","DemiTarifVisa")), Abo(104,("Schatten","DemiTarif")))
val abosDF = spark.sparkContext.parallelize(as).toDF

val ls = List(Loc(101, "Bern"), Loc(101,"Thun"), Loc(102,"Lausanne"), Loc(102,"Geneve"),
              Loc(102, "Nyon"), Loc(103,"Zurich"), Loc(103,"St-Gallen"),Loc(103,"Chur"))
val locationsDF = spark.sparkContext.parallelize(ls).toDF

abosDF.show()
locationsDF.show()

val trackedCustomersDF = abosDF.join(locationsDF, abosDF("id")===locationsDF("id"))
trackedCustomersDF.show()

val abosWithOptionalLocationsDF = abosDF.join(locationsDF, abosDF("id")===locationsDF("id"),"left_outer")
abosWithOptionalLocationsDF.show()
