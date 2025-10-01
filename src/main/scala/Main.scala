import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}

@main def binaryTreeDemo(): Unit =
  println("=" * 60)
  println("🌳 Scala Binary Tree Implementation Demo")
  println("=" * 60)
  println("A comprehensive demonstration of functional binary trees in Scala 3")
  println()

  // Demo 1: Basic tree operations
  basicTreeDemo()
  
  // Demo 2: Building trees from lists
  buildTreeDemo()
  
  // Demo 3: Different data types
  differentTypesDemo()
  
  // Demo 4: Tree properties analysis
  demonstrateTreeProperties()
  
  // Demo 5: Interactive mode
  interactiveMode()

def basicTreeDemo(): Unit =
  println("📋 Demo 1: Basic Tree Operations")
  println("-" * 40)
  
  // Create sample trees
  val emptyTree: BinTree[Int] = Empty
  val singleNode = Node(42, Empty, Empty)
  val complexTree = Node(1, 
    Node(2, Node(4, Empty, Empty), Node(5, Empty, Empty)), 
    Node(3, Node(6, Empty, Empty), Node(7, Empty, Empty))
  )
  
  println("Empty tree:")
  println(s"  Elements: ${numElems(emptyTree)}")
  println(s"  Depth: ${depth(emptyTree)}")
  println(s"  Preorder: ${preorder(emptyTree)}")
  println()
  
  println("Single node tree (value: 42):")
  println(s"  Elements: ${numElems(singleNode)}")
  println(s"  Depth: ${depth(singleNode)}")
  println(s"  Preorder: ${preorder(singleNode)}")
  println()
  
  println("Complex tree:")
  printTree(complexTree)
  println(s"  Elements: ${numElems(complexTree)}")
  println(s"  Depth: ${depth(complexTree)}")
  println(s"  Preorder: ${preorder(complexTree)}")
  println()

def buildTreeDemo(): Unit =
  println("🏗️  Demo 2: Building Trees from Lists")
  println("-" * 40)
  
  val examples = List(
    ("Numbers 1-7", List(1, 2, 3, 4, 5, 6, 7)),
    ("Powers of 2", List(1, 2, 4, 8, 16)),
    ("Fibonacci", List(1, 1, 2, 3, 5, 8, 13)),
    ("Random numbers", List(42, 17, 99, 3, 88))
  )
  
  examples.foreach { (description, list) =>
    println(s"$description: ${list.mkString(", ")}")
    val tree = buildTree(list)
    println("Generated tree:")
    printTree(tree)
    println(s"  Preorder traversal: ${preorder(tree)}")
    println(s"  Tree depth: ${depth(tree)}")
    println(s"  Number of elements: ${numElems(tree)}")
    println()
  }

def differentTypesDemo(): Unit =
  println("🔤 Demo 3: Different Data Types")
  println("-" * 40)
  
  // String tree
  val words = List("root", "left", "right", "deep", "leaf")
  val stringTree = buildTree(words)
  println("String tree from: " + words.mkString(", "))
  printTree(stringTree)
  println(s"  Preorder: ${preorder(stringTree)}")
  println()
  
  // Character tree
  val chars = List('A', 'B', 'C', 'D', 'E')
  val charTree = buildTree(chars)
  println("Character tree from: " + chars.mkString(", "))
  printTree(charTree)
  println(s"  Preorder: ${preorder(charTree)}")
  println()
  
  // Negative numbers
  val negatives = List(-10, -5, 0, 5, 10)
  val negTree = buildTree(negatives)
  println("Tree with negative numbers: " + negatives.mkString(", "))
  printTree(negTree)
  println(s"  Preorder: ${preorder(negTree)}")
  println()

def interactiveMode(): Unit =
  println("🎮 Demo 4: Interactive Mode")
  println("-" * 40)
  println("Enter comma-separated integers to build a tree (or 'quit' to exit):")
  println("Example: 1,2,3,4,5")
  println()
  
  var continue = true
  while continue do
    print("📝 Enter numbers: ")
    val input = readLine()
    
    if input == null || input.toLowerCase == "quit" then
      continue = false
      println("👋 Goodbye!")
    else
      parseAndProcessInput(input) match
        case Some(numbers) if numbers.nonEmpty =>
          val tree = buildTree(numbers)
          println()
          println("🌳 Generated tree:")
          printTree(tree)
          println()
          displayTreeStats(tree, numbers)
          println()
        case Some(_) =>
          println("❌ Please enter at least one number.")
        case None =>
          println("❌ Invalid input. Please enter comma-separated integers.")
      println()

def parseAndProcessInput(input: String): Option[List[Int]] =
  Try {
    input.split(",").map(_.trim.toInt).toList
  } match
    case Success(numbers) => Some(numbers)
    case Failure(_) => None

def displayTreeStats[T](tree: BinTree[T], originalList: List[T]): Unit =
  println("📊 Tree Statistics:")
  println(s"  • Original input: ${originalList.mkString(", ")}")
  println(s"  • Preorder traversal: ${preorder(tree).mkString(", ")}")
  println(s"  • Number of elements: ${numElems(tree)}")
  println(s"  • Tree depth: ${depth(tree)}")
  println(s"  • Is complete binary tree: ${isCompleteTree(tree, numElems(tree))}")

def isCompleteTree[T](tree: BinTree[T], totalNodes: Int): Boolean =
  val expectedDepth = math.ceil(math.log(totalNodes + 1) / math.log(2)).toInt
  val actualDepth = depth(tree)
  actualDepth == expectedDepth

// Utility function to demonstrate tree properties
def demonstrateTreeProperties(): Unit =
  val examples = List(
    ("Balanced", List(1, 2, 3, 4, 5, 6, 7)),
    ("Small", List(10, 20)),
    ("Single", List(100)),
    ("Large", (1 to 15).toList)
  )
  
  println("🔍 Tree Properties Analysis:")
  println("-" * 40)
  
  examples.foreach { (name, list) =>
    val tree = buildTree(list)
    val elements = numElems(tree)
    val treeDepth = depth(tree)
    val minDepth = math.ceil(math.log(elements + 1) / math.log(2)).toInt
    
    println(f"$name%10s: $elements%2d elements, depth $treeDepth%2d (min possible: $minDepth%2d)")
  }
