package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    x <- Arbitrary.arbitrary[A]
    h <- Gen.frequency((45, Gen.const(empty)),(55,genHeap))
  } yield insert(x, h)

//  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("prop1a-Add and find minimum of a heap") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("prop1b-Add and find the minimum of a heap") = forAll(genHeap){(h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("prop2-Minimum of two elem inserted to an empty heap") = forAll(Gen.const(empty), arbitrary[A], arbitrary[A]){(emptyHeap: H, elem1:A, elem2:A) =>
    val m = insert(elem2, insert(elem1,emptyHeap))
    findMin(m) == math.min(elem1,elem2)
  }

  property("prop3-Insert and delete elem to/from empty heap") = forAll(Gen.const(empty), arbitrary[A]){(emptyHeap: H, elem1:A) =>
    val m = insert(elem1,emptyHeap)
    deleteMin(m) == empty
  }

  property("prop4-Sorted heap") = forAll(genHeap){(heap:H) =>
    def sorted(h:H): Boolean={
      if (isEmpty(h))
        true
      else {
        val minVal = findMin(h)
        val reducedHeap = deleteMin(h)
        isEmpty(reducedHeap) || ((minVal <= findMin(reducedHeap)) && sorted(reducedHeap))
      }
    }
    sorted(heap)
  }

  property("prop5-Minimum of two heaps") = forAll(genHeap, genHeap){(heap1:H, heap2:H) =>
    findMin(meld(heap1,heap2)) == math.min(findMin(heap1),findMin(heap2))
  }

  property("prop6-Verify method deleteMin and link") = forAll(genHeap, genHeap){(heap1:H, heap2:H) =>
    def heapEqual(h1:H, h2:H): Boolean={
      if (isEmpty(h1)&&isEmpty(h2)) true
      else (findMin(h1) == findMin(h2)) && heapEqual(deleteMin(h1),deleteMin(h2))
    }
    val h1:H = meld(heap1,heap2)
    val h2:H = meld(deleteMin(heap1),insert(findMin(heap1),heap2))
    heapEqual(h1,h2)
  }

}
