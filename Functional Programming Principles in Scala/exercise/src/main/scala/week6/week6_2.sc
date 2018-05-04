
object nqueens {
  def queens(n: Int): Set[List[Int]] = {
    def isSafe(col: Int, queens: List[Int]): Boolean = {
      val row = queens.length
      val rows: Range = row - 1 to 0 by -1
      val queensWithRow: IndexedSeq[(Int, Int)] = rows.zip(queens)
      queensWithRow.forall(
        x => x match {
          case (r, c) => col != c && math.abs(col - c) != row - r
        }
      )
    }

    def placeQueens(k: Int): Set[List[Int]] = {
      if (k == 0)
        Set(List())
      else
        for {
          queens: List[Int] <- placeQueens(k - 1)
          col <- 0 until n
          if isSafe(col, queens)
        } yield col :: queens
    }

    placeQueens(n)
  }

  def show(queens: List[Int]): String = {
    val lines: List[String] = for (col <- queens.reverse)
      yield Vector.fill(queens.length)("*").updated(col, "o").mkString
    "\n" + lines.mkString("\n")
  }

  queens(4).map(show)
  queens(8).take(3).map(show).mkString("\n")

}