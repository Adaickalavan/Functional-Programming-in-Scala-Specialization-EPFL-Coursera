
object exercise{

  class HelloThread(callback: Thread=>Unit) extends Thread {
    override def run(): Unit ={
      println("Hello!")
      Thread.sleep(40)
      callback(this)
      println("printing last line")
    }
  }

  def call(self:Thread):Unit={
    println("yes callback inside")
  }

  val t = new HelloThread(call)
  println("starting")
  t.start()
  Thread.sleep(10)
//  t.join()
  println("main")
  t.isAlive()



}