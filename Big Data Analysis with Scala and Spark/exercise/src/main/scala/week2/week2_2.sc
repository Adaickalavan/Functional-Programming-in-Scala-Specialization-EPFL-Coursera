
// Options can be viewed as collections
// We can flatMap a Collection[Option[_]] to Collection[_]
// For example we can flatMap List[Option[_]] to List[_]

object exercise {

  // instantiate sample list
  val num = List(1,2,3,4,5,6,7)
  // method 1: map
  val numMap = num.map(p => if (p%2 == 0) Some(p*2) else None)
  // method 2: flatMap
  val numFlatMap = num.flatMap(p => if (p%2 == 0) Some(p*2) else None)
  // method 3: flatMap in for loop
  for {
    num: Option[Int] <- numMap
    out: Int <- num
  } yield out


}