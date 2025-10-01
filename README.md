# Scala Binary Tree Implementation

A functional binary tree implementation in Scala 3 with comprehensive operations and tree visualization.

## Features

- **Algebraic Data Type (ADT)**: Type-safe binary tree using sealed traits and case classes
- **Generic Implementation**: Works with any type `T`
- **Core Operations**: Preorder traversal, depth calculation, element counting
- **Tree Construction**: Build complete binary trees from lists (heap-like structure)
- **Tree Visualization**: ASCII art tree printing with Unicode characters
- **Comprehensive Tests**: 26+ test cases covering all functionality

## Tree Structure

The binary tree is defined as an ADT with two cases:

```scala
sealed trait BinTree[+A]
case object Empty extends BinTree[Nothing]
case class Node[A](value: A, left: BinTree[A], right: BinTree[A]) extends BinTree[A]
```

## Available Operations

### Tree Traversal
- **`preorder[T](tree: BinTree[T]): List[T]`** - Returns preorder traversal (root → left → right)

### Tree Properties
- **`depth[T](tree: BinTree[T]): Int`** - Calculates maximum depth of the tree
- **`numElems[T](tree: BinTree[T]): Int`** - Counts total number of nodes

### Tree Construction
- **`buildTree[T](xs: List[T]): BinTree[T]`** - Builds complete binary tree from list
- **`iBuildTree[T](vec: Vector[T], pos: Int): BinTree[T]`** - Internal helper for tree building

### Tree Visualization
- **`printTree[T](tree: BinTree[T]): Unit`** - Prints tree in ASCII art format
- **`iPrintTree[T](tree: BinTree[T], prefix: String): Unit`** - Internal helper for printing

## Usage Examples

### Creating Trees

```scala
// Empty tree
val empty: BinTree[Int] = Empty

// Single node
val single = Node(42, Empty, Empty)

// Manual tree construction
val tree = Node(1, 
  Node(2, Empty, Empty), 
  Node(3, Empty, Empty)
)

// Build from list (creates heap-like structure)
val fromList = buildTree(List(1, 2, 3, 4, 5))
```

### Tree Operations

```scala
val tree = buildTree(List(1, 2, 3, 4, 5, 6, 7))

// Traversal
preorder(tree)  // List(1, 2, 4, 5, 3, 6, 7)

// Properties
depth(tree)     // 3
numElems(tree)  // 7

// Visualization
printTree(tree)
// Output:
// 1
// └─2
// │ └─4
// │ └─5
// └─3
//   └─6
//   └─7
```

### Working with Different Types

```scala
// String trees
val stringTree = buildTree(List("root", "left", "right"))
preorder(stringTree)  // List("root", "left", "right")

// Custom types work too
case class Person(name: String, age: Int)
val people = List(Person("Alice", 30), Person("Bob", 25))
val peopleTree = buildTree(people)
```

## Build Tree Algorithm

The `buildTree` function creates a complete binary tree using a heap-like structure:
- Root element at position 1
- For node at position `i`:
  - Left child at position `2*i`
  - Right child at position `2*i + 1`

This ensures a balanced tree structure for optimal performance.

## Project Structure

```
bintree/
├── build.sbt                 # SBT build configuration
├── README.md                 # This file
├── src/
│   ├── main/scala/
│   │   ├── binTree.scala     # Main binary tree implementation
│   │   └── Main.scala        # Application entry point
│   └── test/scala/
│       └── MySuite.scala     # Comprehensive test suite (26+ tests)
└── project/
    └── build.properties      # SBT version
```

## Getting Started

### Prerequisites
- Scala 3.7.3+
- SBT 1.11.6+
- Java 21+

### Building and Running

```bash
# Compile the project
sbt compile

# Run tests
sbt test

# Start Scala REPL with project loaded
sbt console

# Run the main application
sbt run
```

### Running Tests

The project includes a comprehensive test suite with 26+ test cases:

```bash
sbt test
```

Test coverage includes:
- ✅ ADT structure validation
- ✅ All tree operations (preorder, depth, numElems)
- ✅ Tree construction from lists
- ✅ Edge cases (empty trees, single nodes, large trees)
- ✅ Different data types (Int, String, negative numbers)
- ✅ Integration tests

## Example Session

```scala
scala> import binTree._

scala> val tree = buildTree(List(10, 20, 30, 40, 50))
val tree: BinTree[Int] = Node(10, Node(20, Node(40, Empty, Empty), Node(50, Empty, Empty)), Node(30, Empty, Empty))

scala> preorder(tree)
val res0: List[Int] = List(10, 20, 40, 50, 30)

scala> depth(tree)
val res1: Int = 3

scala> numElems(tree)
val res2: Int = 5

scala> printTree(tree)
10
└─20
│ └─40
│ └─50
└─30
```

## Contributing

Feel free to contribute by:
1. Adding new tree operations (inorder, postorder, etc.)
2. Implementing tree balancing algorithms
3. Adding more visualization options
4. Improving performance optimizations

## License

This project is open source and available under the MIT License.
