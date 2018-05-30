import bankAccount.BankAccount

object account {
  val acct = new BankAccount
  acct.deposit(50)
  acct.withdraw(20)

  val y = acct
  y.deposit(10)
  y.withdraw(1)
  acct.withdraw(1)
  y.withdraw(1)
  acct.withdraw(1)

}