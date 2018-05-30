package week4.bankSignal

class StackableVariable[T](init:T) {
  private var values:List[T] = List(init)

  def value: T = values.head

  def withValue[R](newValue:T)(op: =>R):R = {
    values = newValue :: values
    try
      op
    finally
      values = values.tail
  }

}
