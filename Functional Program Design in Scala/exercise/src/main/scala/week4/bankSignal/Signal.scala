package week4.bankSignal

import scala.util.DynamicVariable

object Signal{
  private val caller = new DynamicVariable[Signal[_]](NoSignal)
  def apply[T](expr: => T): Signal[T] = new Signal(expr)
}

class Signal[T](expr : => T){
  import Signal._

  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]]=Set.empty

  println("signal.caller->"+caller)
  println("signal.observers->"+observers)
  println("signal.this->"+this)
  update(expr)

  protected def update(expr: => T):Unit={
    myExpr = () => expr
    computeValue()
  }

  protected def computeValue():Unit={
    println("compute.caller->"+caller)
    println("compute.observers->"+observers)
    println("compute.this->"+this)
    val newValue: T = caller.withValue(this)(myExpr())
    if (myValue != newValue){
      myValue = newValue
      val obs = observers
      observers = Set.empty
      obs.foreach(_.computeValue())
    }
  }

  def apply():T={
    println("apply.caller->"+caller)
    println("apply.observers->"+observers)
    println("apply.this->"+this)
    observers += caller.value
    println("apply.observers->"+observers)
    assert(!caller.value.observers.contains(this),"cyclic signal definition")
    myValue
  }

}

object NoSignal extends Signal[Nothing](???){
  override protected def computeValue():Unit = ()
}
