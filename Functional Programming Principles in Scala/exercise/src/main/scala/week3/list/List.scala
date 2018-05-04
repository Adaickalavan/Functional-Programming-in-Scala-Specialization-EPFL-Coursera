package week3.list

trait List[T]{
  def isEmpty: Boolean
  def head:T
  def tail: List[T]
}

class Cons[T](val head : T, val tail: List[T]) extends List[T]{
  def isEmpty = false
}

class Nil[T]{
  def isEmpty = true
  def head : Nothing = throw new NoSuchElementException("Nil.head")
  def tail : Nothing = throw new NoSuchElementException("Nil.tail")
}
