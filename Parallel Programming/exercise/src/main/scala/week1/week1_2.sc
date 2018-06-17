
object Account{
  def apply(amount:Int = 0): Account = new Account(amount)

  private var uidCount: Long = 0L

  def getUniqueId():Long = this.synchronized {
    uidCount = uidCount + 1
    uidCount
  }
}

class Account(private var amount: Int = 0){
  import Account._

  val uid = getUniqueId()

  private def lockAndTransfer(target: Account, n:Int): Unit ={
    this.synchronized{
      target.synchronized{
        this.amount -= n
        target.amount += n
      }
    }
  }
  def transfer(target: Account, n:Int): Unit = {
    if (this.uid < target.uid)
      this.lockAndTransfer(target,n)
    else
      target.lockAndTransfer(this,-n)
  }

  def getAmount():Unit={
    println(amount)
  }
}

//object exercise{

  def startThread(a: Account, b: Account, n:Int): Thread={
    val t=new Thread {
      override def run()={
        for (i <- 0 until n) {
          a.transfer(b,1)
        }
      }
    }
    t.start()
    t
  }

  val a1 = new Account(500000)
  val a2 = new Account(700000)
  a1.getAmount()
  a2.getAmount()
  val t = startThread(a1,a2,150000)
  val s = startThread(a2,a1,100000)
  t.join()
  s.join()
  a1.getAmount()
  a2.getAmount()

//}
