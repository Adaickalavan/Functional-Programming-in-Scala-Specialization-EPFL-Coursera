
object insets {

  val t1= new NonEmpty(9, new Empty, new Empty)
  val t2 = t1 incl 11
  val t3 = t2 incl 10
  val t4 = t3 incl 15
  val t5 = t4 incl 7
  val t6 = t5 incl 8
  val t7 = t6 incl 3
  val t8 = t7 incl 6
  val t9 = new NonEmpty(19, new Empty, new Empty)
  val t10 = t9 incl 23
  val t11 = t10 incl 10
  val t12 = t11 union t8

  abstract class IntSet {
    def incl(x: Int): IntSet
    def contains(x: Int): Boolean
    def union(other: IntSet):IntSet
  }

  class Empty extends IntSet{
    def contains(x: Int): Boolean = false
    def incl(x:Int): IntSet = new NonEmpty(x, new Empty, new Empty)
    override def toString()="."
    def union(other: IntSet):IntSet= other
  }

  class NonEmpty(elem: Int, left:IntSet, right:IntSet) extends IntSet{
    def contains(x:Int): Boolean = {
      if (x<elem) left contains x
      else if (x>elem) right contains x
      else true
    }
    def incl(x:Int): IntSet=
      if (x<elem) new NonEmpty(elem, left incl x, right)
      else if (x>elem) new NonEmpty(elem, left, right incl x)
      else this
    override def toString()={
      "{" + left + elem + right + "}"
    }
    def union(other: IntSet):IntSet={
      ((left union right) union other) incl elem
    }
  }

}