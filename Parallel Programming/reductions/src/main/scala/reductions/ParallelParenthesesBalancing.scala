package reductions

import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
    */
  def balance(chars: Array[Char]): Boolean = {
    val len = chars.length
    def helper(idx:Int, acc: Int): Int = {
      if (idx == len || acc < 0)
        acc
      else if (chars(idx) == '(')
        helper(idx+1, acc + 1)
      else if (chars(idx) == ')')
        helper(idx+1, acc - 1)
      else
        helper(idx+1, acc)
    }
    helper(0,0) == 0
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int): (Int,Int) ={
      var index = idx
      var closeBrackets = arg1
      var openBrackets = arg2
      while (index < until) {
        if (chars(index) == '(') {
          openBrackets = openBrackets + 1
        }
        else if (chars(index) == ')' && openBrackets == 0) {
          closeBrackets = closeBrackets - 1
        }
        else if (chars(index) == ')' && openBrackets != 0) {
          openBrackets = openBrackets - 1
        }
        index = index + 1
      }
      (closeBrackets, openBrackets)
    }

    def reduce(from: Int, until: Int):(Int,Int) = {
      if (until - from <= threshold)
        traverse(from, until,0,0)
      else {
        val mid = from + (until-from)/2
        val (tl, tr) = parallel(reduce(from, mid),
                                reduce(mid, until))
        val intermediateSum = tl._2+tr._1
        if (intermediateSum > 0)
          (tl._1, intermediateSum+tr._2)
        else
          (tl._1+intermediateSum, tr._2)
      }
    }

    reduce(0, chars.length) == (0,0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
