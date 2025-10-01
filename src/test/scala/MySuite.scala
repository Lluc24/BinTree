// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  
  // Test data - sample trees for testing
  val emptyTree: BinTree[Int] = Empty
  val singleNodeTree: BinTree[Int] = Node(5, Empty, Empty)
  val balancedTree: BinTree[Int] = Node(1, 
    Node(2, Node(4, Empty, Empty), Node(5, Empty, Empty)), 
    Node(3, Node(6, Empty, Empty), Node(7, Empty, Empty))
  )
  val leftSkewedTree: BinTree[Int] = Node(1, 
    Node(2, Node(3, Empty, Empty), Empty), 
    Empty
  )
  val stringTree: BinTree[String] = Node("root", 
    Node("left", Empty, Empty), 
    Node("right", Empty, Empty)
  )

  // Tests for BinTree trait and case classes
  test("Empty should be a valid BinTree") {
    assert(emptyTree.isInstanceOf[BinTree[Int]])
  }

  test("Node should create valid binary tree") {
    val node = Node(10, Empty, Empty)
    assert(node.isInstanceOf[BinTree[Int]])
    assertEquals(node.value, 10)
    assertEquals(node.left, Empty)
    assertEquals(node.right, Empty)
  }

  // Tests for preorder traversal
  test("preorder of empty tree should return empty list") {
    assertEquals(preorder(emptyTree), List())
  }

  test("preorder of single node should return list with one element") {
    assertEquals(preorder(singleNodeTree), List(5))
  }

  test("preorder traversal should visit root, left, right") {
    assertEquals(preorder(balancedTree), List(1, 2, 4, 5, 3, 6, 7))
  }

  test("preorder should work with different types") {
    assertEquals(preorder(stringTree), List("root", "left", "right"))
  }

  // Tests for depth function
  test("depth of empty tree should be 0") {
    assertEquals(depth(emptyTree), 0)
  }

  test("depth of single node should be 1") {
    assertEquals(depth(singleNodeTree), 1)
  }

  test("depth of balanced tree should be correct") {
    assertEquals(depth(balancedTree), 3)
  }

  test("depth of left-skewed tree should be correct") {
    assertEquals(depth(leftSkewedTree), 3)
  }

  // Tests for numElems function
  test("numElems of empty tree should be 0") {
    assertEquals(numElems(emptyTree), 0)
  }

  test("numElems of single node should be 1") {
    assertEquals(numElems(singleNodeTree), 1)
  }

  test("numElems should count all nodes correctly") {
    assertEquals(numElems(balancedTree), 7)
    assertEquals(numElems(leftSkewedTree), 3)
    assertEquals(numElems(stringTree), 3)
  }

  // Tests for buildTree function
  test("buildTree with empty list should return empty tree") {
    val tree = buildTree(List[Int]())
    assertEquals(tree, Empty)
  }

  test("buildTree with single element should create single node") {
    val tree = buildTree(List(42))
    assertEquals(tree, Node(42, Empty, Empty))
  }

  test("buildTree should create complete binary tree from list") {
    val tree = buildTree(List(1, 2, 3, 4, 5))
    // Tree structure: 1 at root, 2,3 as children, 4,5 as children of 2
    val expected = Node(1, 
      Node(2, Node(4, Empty, Empty), Node(5, Empty, Empty)), 
      Node(3, Empty, Empty)
    )
    assertEquals(tree, expected)
  }

  test("buildTree should work with strings") {
    val tree = buildTree(List("a", "b", "c"))
    val expected = Node("a", 
      Node("b", Empty, Empty), 
      Node("c", Empty, Empty)
    )
    assertEquals(tree, expected)
  }

  // Integration tests combining multiple functions
  test("preorder of buildTree creates valid heap-like structure") {
    val input = List(1, 2, 3, 4, 5, 6, 7)
    val tree = buildTree(input)
    val result = preorder(tree)
    // buildTree creates heap structure: root=1, children=2,3, then 4,5 under 2, 6,7 under 3
    val expected = List(1, 2, 4, 5, 3, 6, 7)
    assertEquals(result, expected)
  }

  test("numElems of buildTree result should match input length") {
    val input = List("a", "b", "c", "d", "e")
    val tree = buildTree(input)
    assertEquals(numElems(tree), input.length)
  }

  test("depth and numElems relationship for complete trees") {
    val input = List(1, 2, 3, 4, 5, 6, 7) // Complete binary tree with 7 nodes
    val tree = buildTree(input)
    assertEquals(numElems(tree), 7)
    assertEquals(depth(tree), 3) // depth should be ceil(log2(7+1)) = 3
  }

  // Edge case tests
  test("functions should handle large trees") {
    val largeList = (1.to(100)).toList
    val tree = buildTree(largeList)
    assertEquals(numElems(tree), 100)
    assertEquals(preorder(tree).length, 100)
    assert(depth(tree) > 0)
  }

  test("tree operations should be consistent") {
    val input = List(10, 20, 30, 40)
    val tree = buildTree(input)
    val traversal = preorder(tree)
    val count = numElems(tree)
    
    // buildTree creates: 10 at root, 20,30 as children, 40 as left child of 20
    val expected = List(10, 20, 40, 30)
    assertEquals(traversal, expected)
    assertEquals(count, input.length)
    assert(depth(tree) >= scala.math.ceil(scala.math.log(count + 1) / scala.math.log(2)).toInt)
  }

  // Additional edge case tests
  test("buildTree should handle unbalanced cases correctly") {
    val input = List(1, 2, 3)
    val tree = buildTree(input)
    assertEquals(preorder(tree), List(1, 2, 3))
    assertEquals(numElems(tree), 3)
    assertEquals(depth(tree), 2)
  }

  test("functions should work with negative numbers") {
    val input = List(-1, -2, -3, -4)
    val tree = buildTree(input)
    assertEquals(numElems(tree), 4)
    assertEquals(preorder(tree), List(-1, -2, -4, -3))
  }

  test("depth should be 1 for trees with only root and one child") {
    val tree1 = Node(1, Node(2, Empty, Empty), Empty)
    val tree2 = Node(1, Empty, Node(2, Empty, Empty))
    assertEquals(depth(tree1), 2)
    assertEquals(depth(tree2), 2)
  }

  test("preorder should handle deeply nested left-skewed tree") {
    val deepTree = Node(1, Node(2, Node(3, Node(4, Empty, Empty), Empty), Empty), Empty)
    assertEquals(preorder(deepTree), List(1, 2, 3, 4))
    assertEquals(depth(deepTree), 4)
    assertEquals(numElems(deepTree), 4)
  }
}
