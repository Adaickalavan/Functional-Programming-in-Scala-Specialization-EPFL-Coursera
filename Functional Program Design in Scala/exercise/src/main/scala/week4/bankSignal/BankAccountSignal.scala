package week4.bankSignal

class BankAccountSignal {
  val balance: Var[Int] = Var(0)

  def deposit(amount:Int):Unit ={
    if (amount>0) {
      val b: Int = balance()
      balance() = b + amount
    }
  }

  def withdraw(amount: Int):Unit = {
    if (0 < amount && amount <= balance()){
      val b: Int = balance()
      balance() = b - amount
    }
    else
      throw new Error("Insufficient funds")
  }
}

