import waterPouring.Pouring

object PouringTest{

  val problem: Pouring = new Pouring(capacity = Vector(4, 9, 19))
  problem.moves
  problem.pathSets.take(3).toList
  problem.solutions(17)


}
