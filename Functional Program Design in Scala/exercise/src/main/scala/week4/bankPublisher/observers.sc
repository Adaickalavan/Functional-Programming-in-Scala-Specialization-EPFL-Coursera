import week4.bankPublisher.{BankAccountPublisher, Consolidator}

object observers{
  val a = new BankAccountPublisher
  val b = new BankAccountPublisher
  val c = new Consolidator(List(a,b))

  c.totalBalance
  a.deposit(20)
  c.totalBalance
  b.deposit(30)
  c.totalBalance


}