
object week1_1 {
  println("Welcome to Scala")

  val f: PartialFunction[String,String] = {
    case "ping" => "pong"
  }

  f.isDefinedAt("ping")
  f.isDefinedAt("pong")
  f("ping")

  def inter(s:String):String={
    s match {
      case "ping" => "pong"
      case _ => "others"
    }
  }

  val g: String=>String = {
    inter
  }

  g("ping")
  g("abc")

}
