
object excercise{

  def expr = {
    val x = {print("x\n");1}
    lazy val y ={print("y\n");2}
    def z = {print("z\n");3}
    z+y+x+z+y+x
  }

  expr

}