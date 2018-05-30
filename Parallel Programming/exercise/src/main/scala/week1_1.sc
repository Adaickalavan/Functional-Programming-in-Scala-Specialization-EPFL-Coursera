
class HelloThread extends Thread {
  override def run(): Unit ={
    println("Hello!")
    println("World!")
  }
}

object exercise{
  val t = new HelloThread
  val s = new HelloThread
  t.start()
  s.start()
  t.join()
  s.join()

  private var uidCount: Long = 0L

  def getUniqueId():Long = this.synchronized {
    uidCount = uidCount + 1
    uidCount
  }

  def startThread(): Thread = {
    val t = new Thread {
      override def run()= {
        val uids = for (_ <- 0 until 10) yield getUniqueId()
        println(uids)
      }
    }
    t.start()
    t
  }

  val t1 = startThread()
  val t2 = startThread()
  t1.join()
  t2.join()

}