

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = data(y * width + x)
    def update(x: Int, y: Int, c: RGBA): Unit = {
      data(y * width + x) = c
    }
  }

  val red1:RGBA=>Int = red
  val green1:RGBA=>Int = green
  val blue1:RGBA=>Int = blue
  val alpha1:RGBA=>Int = alpha

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {
    // TODO implement using while loops
    val xMin = clamp(x-radius,0,src.width-1)
    val yMin = clamp(y-radius,0,src.height-1)
    val xMax = clamp(x+radius,0,src.width-1)
    val yMax = clamp(y+radius,0,src.height-1)
    val totalSquares = (xMax-xMin+1)*(yMax-yMin+1)

    var rgbaVal:Int = 0
    var store:Map[(Int=>Int),Int] = Map((red1,0),(green1,0),(blue1,0),(alpha1,0))

    var xCur = xMin
    while(xCur <= xMax) {
      var yCur = yMin
      while (yCur <= yMax) {
        rgbaVal = src(xCur, yCur)
        store = store.map{ case (key, value) => (key, value + key(rgbaVal))}
        yCur += 1
      }
      xCur += 1
    }
    rgba(store(red1)/totalSquares,store(green1)/totalSquares,store(blue1)/totalSquares,store(alpha1)/totalSquares)
  }

}
