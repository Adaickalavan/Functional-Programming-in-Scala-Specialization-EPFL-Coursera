
import week4.bankSignal.{BankAccountSignal, Signal}

object accounts{
  def consolidated(accts: List[BankAccountSignal]):Signal[Int]={
    Signal(accts.map(_.balance()).sum)
  }

  val a = new BankAccountSignal
  val b = new BankAccountSignal
  val c = consolidated(List(a,b))
  c()
  a.deposit(20)
  c()
  b.deposit(30)
  c()
  val xchange = Signal(246.00)
  val inDollar = Signal(c()*xchange())
  

}