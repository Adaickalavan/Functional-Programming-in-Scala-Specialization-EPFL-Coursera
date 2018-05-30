

object bookData {
  case class Book(title:String, authors:List[String])

  val books = Set(
    Book(
      title = "Structure and Interpretation of Computer Programs",
      authors = List("Abelson, Harald","Sussman, GErald J.")
      ),
    Book(
      title = "Introduction to Functional Programming",
      authors = List("Bird, Richard","Wadler, Phil")
    ),
    Book(
      title = "Effective Java",
      authors = List("Bloch, Joshua")
    ),
    Book(
      title = "Effective Java 2",
      authors = List("Bloch, Joshua")
    ),
    Book(
      title = "Java Puzzlers",
      authors = List("Bloch, Joshua","Gafter, Neal")
    ),
    Book(
      title = "Programming in Scala",
      authors = List("Odersky, Martin","Spoon, Lex","Venners, Bill")
    ),
  )

  for (b <- books; a <- b.authors if a.startsWith("Bloch"))
    yield b.title


  for {
  b1: Book <- books
  b2: Book <- books
  if b1.title < b2.title
  a1: String <- b1.authors
  a2: String <- b2.authors
  if a1 == a2
  } yield a1

}