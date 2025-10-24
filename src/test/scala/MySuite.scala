import BinTree.*

class MySuite extends munit.FunSuite {

    // Test data - sample trees for testing
    val emptyTree: BinTree = Empty
    val singleNodeTree: BinTree = BinTree(5)
    val balancedTree: BinTree = BinTree(1, 2, 3, 4, 5, 6, 7)
    val leftSkewedTree: BinTree = BinTree(1, 2, 3)
    val stringTree: BinTree = BinTree("root", "left", "right")

    // Tests for BinTree trait and case classes
    test("Empty should be a valid BinTree") {
        assert(emptyTree.isInstanceOf[BinTree])
    }

    test("Node should create valid binary tree") {
        val node = BinTree(10)
        assert(node.isInstanceOf[BinTree])
        node match
            case Node(value, left, right) =>
                assertEquals(value.asInstanceOf[Int], 10)
                assertEquals(left, Empty)
                assertEquals(right, Empty)
            case Empty => fail("Node should not match Empty")
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
        assertEquals(depth(leftSkewedTree), 2)
    }

    // Tests for size function
    test("size of empty tree should be 0") {
        assertEquals(size(emptyTree).asInstanceOf[Int], 0)
    }

    test("size of single node should be 1") {
        assertEquals(size(singleNodeTree).asInstanceOf[Int], 1)
    }

    test("size should count all nodes correctly") {
        assertEquals(size(balancedTree).asInstanceOf[Int], 7)
        assertEquals(size(leftSkewedTree).asInstanceOf[Int], 3)
        assertEquals(size(stringTree).asInstanceOf[Int], 3)
    }

    // Tests for BinTree construction function
    test("BinTree with empty varargs should return empty tree") {
        val tree = BinTree[Int]()
        assertEquals(tree, Empty)
    }

    test("BinTree with single element should create single node") {
        val tree = BinTree(42)
        preorder(tree) match
            case List(value) => assertEquals(value, 42)
            case _ => fail("Expected single node tree")
    }

    test("BinTree should create complete binary tree from values") {
        val tree = BinTree(1, 2, 3, 4, 5)
        // Tree structure: 1 at root, 2,3 as children, 4,5 as children of 2
        assertEquals(preorder(tree), List(1, 2, 4, 5, 3))
    }

    test("BinTree should work with strings") {
        val tree = BinTree("a", "b", "c")
        assertEquals(preorder(tree), List("a", "b", "c"))
    }

    // Integration tests combining multiple functions
    test("preorder of BinTree creates valid heap-like structure") {
        val input = List(1, 2, 3, 4, 5, 6, 7)
        val tree = BinTree(input*)
        val result = preorder(tree)
        // BinTree creates heap structure: root=1, children=2,3, then 4,5 under 2, 6,7 under 3
        val expected = List(1, 2, 4, 5, 3, 6, 7)
        assertEquals(result, expected)
    }

    test("size of BinTree result should match input length") {
        val input = List("a", "b", "c", "d", "e")
        val tree = BinTree(input*)
        assertEquals(size(tree).asInstanceOf[Int], input.length)
    }

    test("depth and size relationship for complete trees") {
        val input = List(1, 2, 3, 4, 5, 6, 7) // Complete binary tree with 7 nodes
        val tree = BinTree(input*)
        assertEquals(size(tree).asInstanceOf[Int], 7)
        assertEquals(depth(tree), 3) // depth should be ceil(log2(7+1)) = 3
    }

    // Edge case tests
    test("functions should handle large trees") {
        val largeList = (1.to(100)).toList
        val tree = BinTree(largeList*)
        assertEquals(size(tree).asInstanceOf[Int], 100)
        assertEquals(preorder(tree).length, 100)
        assert(depth(tree) > 0)
    }

    test("tree operations should be consistent") {
        val input = List(10, 20, 30, 40)
        val tree = BinTree(input*)
        val traversal = preorder(tree)
        val count = size(tree).asInstanceOf[Int]

        // BinTree creates: 10 at root, 20,30 as children, 40 as left child of 20
        val expected = List(10, 20, 40, 30)
        assertEquals(traversal, expected)
        assertEquals(count, input.length)
        assert(depth(tree) >= scala.math.ceil(scala.math.log(count + 1) / scala.math.log(2)).toInt)
    }

    // Additional edge case tests
    test("BinTree should handle unbalanced cases correctly") {
        val input = List(1, 2, 3)
        val tree = BinTree(input*)
        assertEquals(preorder(tree), List(1, 2, 3))
        assertEquals(size(tree).asInstanceOf[Int], 3)
        assertEquals(depth(tree), 2)
    }

    test("functions should work with negative numbers") {
        val input = List(-1, -2, -3, -4)
        val tree = BinTree(input*)
        assertEquals(size(tree).asInstanceOf[Int], 4)
        assertEquals(preorder(tree), List(-1, -2, -4, -3))
    }

    test("depth should be 2 for trees with only root and one child") {
        val tree1 = BinTree(1, 2)
        val tree2 = BinTree(1, 2, 3)
        assertEquals(depth(tree1), 2)
        assertEquals(depth(tree2), 2)
    }

    test("preorder should handle deeply nested tree") {
        val deepTree = BinTree(1, 2, 3, 4)
        assertEquals(preorder(deepTree), List(1, 2, 4, 3))
        assertEquals(depth(deepTree), 3)
        assertEquals(size(deepTree).asInstanceOf[Int], 4)
    }
}
