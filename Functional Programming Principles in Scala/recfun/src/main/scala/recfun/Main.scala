package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if ((c == 0) || (c == r))
        1
      else
        pascal(c-1,r-1) + pascal(c,r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def func(chars: List[Char], openCount:Int): Boolean= {
        var newOpenCount:Int = openCount
        if (chars.isEmpty)
          if (openCount == 0)
            true
          else
            false
        else {
          val char = chars.head
          if (char == '(')
            newOpenCount = openCount +  1
          if (char == ')')
            newOpenCount = openCount - 1
          if (newOpenCount < 0)
            false
          else
            func(chars.tail, newOpenCount)
        }
      }
      func(chars,0)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if (coins.isEmpty || money < 0)
        0
      else if (money == 0)
        1
      else
        countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
  }
