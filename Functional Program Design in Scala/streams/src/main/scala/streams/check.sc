

object check {

  case class Pos(row: Int, col: Int) {
    /** The position obtained by changing the `row` coordinate by `d` */
    def deltaRow(d: Int): Pos = this.copy(row = row + d)

    /** The position obtained by changing the `col` coordinate by `d` */
    def deltaCol(d: Int): Pos = this.copy(col = col + d)
  }

  def terrainFunction(levelVector: Vector[Vector[Char]]): Pos => Boolean = {
    def func(pos:Pos):Boolean ={
      /*pos match {
        case Pos(x,y) => for {
          row: Vector[Char] <- levelVector.lift(x)
          ch: Char <- row.lift(y)
          if ch != '-'
        } yield ch
      }*/

      levelVector(pos.row)(pos.col) != '-' &&
      pos.row <= 2 &&
      pos.col <= 1
    }
    func
  }

  val train: Vector[Vector[Char]] = Vector(Vector('S', 'T'), Vector('o', 'o'), Vector('o', 'o'))
  val d = terrainFunction(train)
  val f = new Pos(0,1)
  d(f)

  train.indexWhere(a => a.contains('S'))

  def findChar(c: Char, levelVector: Vector[Vector[Char]]): Pos = {
    val row = levelVector.indexWhere(x => x.contains(c))
    val col = levelVector(row).indexOf(c)
    Pos(row,col)
  }

  findChar('T',train)


  /*val level =
         """ST
          |oo
          |oo""".stripMargin*/

  trait attempt {
    val level: String

    val vector =
      level.split("\n").map(str => Vector(str: _*))
  }



}