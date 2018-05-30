package waterPouring

class Pouring(capacity: Vector[Int]){

  //States
  type State = Vector[Int]
  val initialState: Vector[Int] = capacity.map(x => 0)

  //Moves
  trait Move {
    def change(state:State):State
  }
  case class Empty(glass:Int) extends Move{
    def change(state:State):State = {
      state.updated(glass,0)
    }
  }
  case class Fill(glass:Int) extends Move{
    def change(state:State):State= {
      state.updated(glass, capacity(glass))
    }
  }
  case class Pour(from:Int, to:Int) extends Move{
    def change(state:State):State={
      val amount = state(from).min(capacity(to) - state(to))
      state.updated(from,state(from)-amount).updated(to,state(to)+amount)
    }
  }

  val glasses: Range = 0.until(capacity.length)
  val moves = {
    (for {g <- glasses} yield Empty(g)) ++
    (for {g <- glasses} yield Fill(g)) ++
    (for {from <- glasses; to <- glasses if from != to} yield Pour(from,to))
  }

  //Paths
  class Path(history:List[Move], val endState: State){
    def extend(move: Move) = new Path(move :: history, move.change(endState))
    override def toString: String = history.reverse.mkString(" ")+"-->"+endState
  }

  val initialPath = new Path(Nil, initialState)

  def from(paths:Set[Path], explored:Set[State]):Stream[Set[Path]] = {
    if (paths.isEmpty) Stream.empty
    else {
      val more = for {
        path: Path <- paths
        next: Path <- moves.map(path.extend)
        if !(explored.contains(next.endState))
      } yield next
      paths #:: from(more, explored ++ (more.map(_.endState)))
    }
  }

  val pathSets: Stream[Set[Path]] = from(Set(initialPath),Set(initialState))

  def solutions(target: Int):Stream[Path] = {
    for {
      pathSet: Set[Path] <- pathSets
      path: Path <- pathSet
      if path.endState.contains(target)
    } yield path
  }

}