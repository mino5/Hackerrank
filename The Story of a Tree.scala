import scala.collection.mutable.Queue
import scala.collection.mutable.Stack
object Solution {

  class Fraction(n: Int, d: Int) {
    // It makes no sense to have the denominator 0
    require(d != 0)

    private val g = gcd(n, d)
    val numerator : Int = n / g
    val denominator : Int = d / g

    // Determines the greatest common divisor of two numbers
    private def gcd(a: Int, b: Int) : Int =
      if (b == 0) a else gcd(b, a % b)

    override def toString =
      numerator + "/" + denominator
  }

  def inorder(graph: Array[List[Int]]): (Array[Int], Array[Int], Array[Int]) = {

    val st = Stack[Int]()
    st.push(1)

    var order = 1
    var visited = Set[Int]()
    val orderCurrent = Array.fill[Int](graph.length)(0)
    val currentOrder = Array.fill[Int](graph.length)(0)
    val smallestChild = Array.fill[Int](graph.length)(0)
    val parent = Array.fill[Int](graph.length)(0)

    while (!st.isEmpty) {
      val current = st.pop()
      val neighbours = graph(current) filterNot visited.contains

      if (neighbours.isEmpty) {
        orderCurrent(order) = current
        currentOrder(current) = order

        if (!visited.contains(current)) {
          smallestChild(current) = order;
        }

        order = order + 1
      }
      else {

        if (!visited.contains(current)) {

          st.push(current)
          smallestChild(current) = order
        }

        for (x <- neighbours filterNot (y => y == current || y == parent(current))) {
            st.push(x)
            parent(x) = current
        }
      }

      visited = visited + current
    }

    return (currentOrder, orderCurrent, smallestChild)
  }


  def main(args: Array[String]) {
    val sc = new java.util.Scanner (System.in)
    var q = sc.nextInt()


    for (steps <- 1 to q) {

      val nodes = sc.nextInt()
      val neighbors = Array.fill[List[Int]](nodes + 1)(List[Int]())

      for (node <- 1 to nodes - 1) {
        val u = sc.nextInt()
        val v = sc.nextInt()
        neighbors(u) = v :: neighbors(u)
        neighbors(v) = u :: neighbors(v)
      }

      val g = sc.nextInt()
      val k = sc.nextInt()

      val timesOccuring = Array.fill[Int](nodes + 1)(0)
      val timesNotOccuring = Array.fill[Int](nodes + 1)(0)
      var timesNotOccuringUpdated = 0

      val inord = inorder(neighbors)
      val currentOrder = inord._1
      val orderCurrent = inord._2
      val smallestChild = inord._3

      for (guesses <- 1 to g) {
        val u = sc.nextInt()
        val v = sc.nextInt()

        if (neighbors(v).contains(u)) {

          var roots = Set[Int]()

          // u is higher
          if (currentOrder(u) > currentOrder(v)) {

            // all nodes without nodes of u
            val order = currentOrder(v)
            val minOrder = smallestChild(v)

            for (i <- minOrder to order) {
                timesNotOccuring(orderCurrent(i)) += 1
            }

            timesNotOccuringUpdated = timesNotOccuringUpdated + 1
          }
          else
          if (currentOrder(u) < currentOrder(v)) {

            val order = currentOrder(u)
            val minOrder = smallestChild(u)

            for (i <- minOrder to order) {
              timesOccuring(orderCurrent(i)) += 1
            }
          }
        }
      }

      val times = (1 to nodes).foldLeft(0)((acc, x) => {
         if (timesNotOccuringUpdated - timesNotOccuring(x) + timesOccuring(x) >= k)
           acc + 1
         else acc
      })

      println(new Fraction(times, nodes))
    }
  }
}