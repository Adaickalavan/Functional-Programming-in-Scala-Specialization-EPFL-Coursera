package week4.bankPublisher

class BankAccountPublisher extends Publisher {
  private var balance = 0

  def currentBalance = balance

  def deposit(amount:Int):Unit ={
    if (amount>0)
      balance = balance + amount
    publish()
  }

  def withdraw(amount: Int):Unit = {
    if (0 < amount && amount <= balance){
      balance = balance - amount
      publish()
    }
    else
      throw new Error("Insufficient funds")
  }
}

class Consolidator(observed: List[BankAccountPublisher]) extends Subscriber{

  observed.foreach(_.subscribe(this))
  compute()

  private var total: Int = _

  private def compute(): Unit = {
    total = observed.map(_.currentBalance).sum
  }

  def handler(pub: Publisher): Unit = compute()

  def totalBalance: Int = total

}
