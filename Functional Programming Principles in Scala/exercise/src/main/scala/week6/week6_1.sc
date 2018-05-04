
object test {
  val xs = Array(1,2,3,44)
  xs map(x=>x*2)
  val s = "Hello World"
  s.filter(c=>c.isUpper)
  s.exists(c=>c.isUpper)
  s.forall(c=>c.isUpper)

  val r : Range = 1 to 5
  r.foreach(a => println(a))
  (1 to 3).flatMap(x => (5 to 7).map(y=>(x,y)))

  val e = List((1,2),(3,6))
  e.map({case (x,y)=>x*y})

  def isPrime(n:Int):Boolean={
    2.until(n).forall(e => n%e!=0)
  }

  isPrime(23)

  val n=7
  (1 until n).flatMap(i=>(1 until i).map(j=>(i,j))).filter(pair => isPrime(pair._1+pair._2))

  //for {
  //  i <- (1 umtil 7)
  //  j <- (1 until i)
  //  if isPrime(i + j)
  //} yield (i+j)

  val xs2 = List(6,7)
  val ys2 = List(1,2)
  (for ((x,y) <- xs2 zip ys2) yield x*y).sum
  
  val hh = List(1,2,3,4,5,6,7)
  for {
    i <- hh
  } yield (i)

}
