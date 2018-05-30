
object Generators {
  //---------------------------------------------------------------
  trait Generator[+T] {
    self => //alias for this
    def generate: T
    def foreach[U](f: T=>U): U = {
      f(generate)
    }
    def map[S](f: T=>S): Generator[S] = {
      new Generator[S] {
        def generate = f(self.generate)
      }
    }
    def flatMap[S](f: T=> Generator[S]): Generator[S] ={
      new Generator[S] {
        def generate:S = f(self.generate).generate
      }
    }
  }

  val integers: Generator[Int] = new Generator[Int] {
    def generate: Int = scala.util.Random.nextInt()
  }

  val booleans: Generator[Boolean] = integers.map[Boolean](x => x >= 0)
  //---------------------------------------------------------------
  trait Tree
  case class Inner(left: Tree, right:Tree) extends Tree
  case class Leaf(x: Int) extends Tree

  def trees: Generator[Tree] = {
    for {
      isLeaf <- booleans
      tree <- if (isLeaf) leafs else inners
    } yield tree
  }

  def leafs: Generator[Leaf] = {
    for {
      x <- integers
    } yield Leaf(x)
  }

  def inners:Generator[Inner] = {
    for {
      l <- trees
      r <- trees
    } yield Inner(l, r)
  }

  trees.generate
  leafs.generate
  inners.generate
  //---------------------------------------------------------------
  def test[T](g:Generator[T],numTimes:Int = 100)(testFunc:T=>Boolean): Unit = {
    for {
      i <- 0 until numTimes
    } { //side effect represented by foreach after desugar
      val value = g.generate
      println(value)
      assert(testFunc(value), " test failed for " + value)
    }
    println("passed "+numTimes+" tests")
  }
  //---------------------------------------------------------------
  def single[T](x:T): Generator[T] = {
    new Generator[T] {
      def generate = x
    }
  }

  def lists: Generator[List[Int]] = {
    for {
      empty <- booleans
      list <- if (empty) emptyLists else nonEmptyLists
    } yield list
  }

  def emptyLists: Generator[Nil.type] = single(Nil)

  def nonEmptyLists: Generator[List[Int]] = {
    for {
      head <- integers
      tail <- lists
    } yield head :: tail
  }

  def pairs[T,U](t:Generator[T], u:Generator[U]): Generator[(T, U)] =
    for {
      x <- t
      y <- u
    } yield (x,y)

  test(g = pairs(lists, lists),numTimes = 5){
    case (xs,ys) => (xs++ys).length > xs.length
  }
  //---------------------------------------------------------------


}

