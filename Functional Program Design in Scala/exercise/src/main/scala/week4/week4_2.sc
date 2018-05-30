

object exercise{

  trait Level1 {
    val dd: String

  }

  class bb extends Level1 {
    val dd = "Hi"
  }

  val f = new bb{
    val gg = "wq"
    assert(dd == "Hi", "0,0")
  }

  f.dd
  f.gg

}