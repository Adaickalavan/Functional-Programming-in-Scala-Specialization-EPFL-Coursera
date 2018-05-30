
import week4.bankSignal.{BankAccountSignal, Signal}

object accounts extends App{
  def consolidated(accts: List[BankAccountSignal]):Signal[Int]={
    Signal(accts.map(_.balance()).sum)
  }

  print("start ab\n")
  val a = new BankAccountSignal
  val b = new BankAccountSignal
  val c = consolidated(List(a,b))
  println("FirstTime--> "+ c())
  a.deposit(20)
  println("SecondTime--> "+ c())
  b.deposit(30)
  c()
  val xchange = Signal(246.00)
  val inDollar = Signal(c()*xchange())
  inDollar()
  b.withdraw(10)
  inDollar()


}