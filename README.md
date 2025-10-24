# Scala Binary Tree Implementation

A functional binary tree implementation in Scala 3 with dependent types, compile-time size computation, and tree visualization.

## Features

- **Enum-based ADT**: Type-safe binary tree using Scala 3 enums with GADTs
- **Non-Generic Implementation**: Unified type that works with any value type
- **Dependent Types**: Type-level size computation with `Size[T]` match type
- **Core Operations**: Preorder traversal, depth calculation, type-safe element counting
- **Varargs Constructor**: Build complete binary trees easily with `BinTree(1, 2, 3, ...)`
- **Tree Visualization**: Beautiful ASCII art tree printing with Unicode box-drawing characters
- **Comprehensive Tests**: 26+ test cases covering all functionality

## Tree Structure

The binary tree is defined as an enum with dependent types:

```scala
enum BinTree:
    case Node[T, A <: BinTree, B <: BinTree](value: T, left: A, right: B)
    case Empty
```

## Type-Level Size Computation

The implementation includes a sophisticated type-level size calculation:

```scala
type Size[T <: BinTree] <: Int = T match
    case Empty.type => 0
    case Node[_, l, r] => 1 + Size[l] + Size[r]

def size[T <: BinTree](tree: T): Size[T] = tree match
    case _: Empty.type => 0
    case node: Node[v, l, r] => sum(1, size(node.left), size(node.right))
```

This allows the compiler to compute tree sizes at the type level when types are fully known!

## Available Operations

### Tree Traversal
- **`preorder(tree: BinTree): List[Any]`** - Returns preorder traversal (root → left → right)

### Tree Properties
- **`depth(tree: BinTree): Int`** - Calculates maximum depth of the tree
- **`size[T <: BinTree](tree: T): Size[T]`** - Type-level size computation (use `.asInstanceOf[Int]` for runtime)

### Tree Construction
- **`BinTree(values: T*): BinTree`** - Builds complete binary tree from varargs (heap-like structure)
- **`BinTree(value: T, left: BinTree, right: BinTree): BinTree`** - Manual node construction
- **`BinTree[T](): BinTree`** - Creates an empty tree

### Tree Visualization
- **`toString: String`** - Returns tree in beautiful ASCII art format with box-drawing characters

## Usage Examples

### Creating Trees

```scala
import BinTree.*

// Empty tree
val empty: BinTree = Empty

// Single node
val single = BinTree(42)

// Multiple nodes using varargs
val tree = BinTree(1, 2, 3, 4, 5, 6, 7)

// Manual tree construction
val manual = BinTree(1, 
  BinTree(2, Empty, Empty), 
  BinTree(3, Empty, Empty)
)

// Build from list (creates heap-like structure)
val list = List(1, 2, 3, 4, 5)
val fromList = BinTree(list*)
```

### Tree Operations

```scala
val tree = BinTree(1, 2, 3, 4, 5, 6, 7)

// Traversal
preorder(tree)  // List(1, 2, 4, 5, 3, 6, 7)

// Properties
depth(tree)     // 3
size(tree).asInstanceOf[Int]  // 7 (cast needed for runtime use)

// Visualization (automatic via toString)
println(tree)
// Output:
// 1
// ├── 2
// │   ├── 4
// │   └── 5
// └── 3
//     ├── 6
//     └── 7
```

### Working with Different Types

```scala
// String trees
val words = List("root", "left", "right", "deep", "leaf")
val stringTree = BinTree(words*)
preorder(stringTree)  // List(root, left, deep, leaf, right)

// Character trees
val charTree = BinTree('A', 'B', 'C', 'D', 'E')
println(charTree)

// Mixed types
val mixed = BinTree(1, "two", 3.0, 'D')
```

## Build Tree Algorithm

The `BinTree(values*)` constructor creates a complete binary tree using a heap-like structure:
- Root element at position 1
- For node at position `i`:
  - Left child at position `2*i`
  - Right child at position `2*i + 1`

This ensures a balanced tree structure for optimal performance.

## Advanced Features

### Functor Instance

The implementation includes a Functor typeclass instance for BinTree:

```scala
given Functor[BinTree] with
    extension [A](t: BinTree) infix def fmap[B](func: A => B): BinTree =
        t match
            case Empty => Empty fmap func
            case node: Node[A, BinTree, BinTree] => 
                Node[B, BinTree, BinTree](func(node.value), node.left fmap func, node.right fmap func)
```

This allows functional mapping over tree values while preserving structure.

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
- ✅ Enum structure validation
- ✅ All tree operations (preorder, depth, size)
- ✅ Tree construction with varargs
- ✅ Edge cases (empty trees, single nodes, large trees)
- ✅ Different data types (Int, String, Char, negative numbers)
- ✅ Integration tests

## Example Session

```scala
scala> import BinTree.*

scala> val tree = BinTree(10, 20, 30, 40, 50)
val tree: BinTree = Node(10, Node(20, Node(40, Empty, Empty), Node(50, Empty, Empty)), Node(30, Empty, Empty))

scala> preorder(tree)
val res0: List[Any] = List(10, 20, 40, 50, 30)

scala> depth(tree)
val res1: Int = 3

scala> size(tree).asInstanceOf[Int]
val res2: Int = 5

scala> println(tree)
10
├── 20
│   ├── 40
│   └── 50
└── 30
```

## Key Differences from Traditional Implementations

1. **Enum-based**: Uses Scala 3 enums instead of sealed traits
2. **Non-generic**: Single unified type rather than parameterized `BinTree[T]`
3. **Dependent types**: Type-level size computation with match types
4. **GADTs**: Node case includes type parameters for left and right subtrees
5. **Varargs constructor**: Convenient `BinTree(1, 2, 3)` syntax
6. **Built-in toString**: Beautiful tree visualization without separate print function

## Contributing

Feel free to contribute by:
1. Adding new tree operations (inorder, postorder, level-order, etc.)
2. Implementing tree balancing algorithms (AVL, Red-Black)
3. Adding more type-level computations
4. Improving performance optimizations
5. Extending the Functor instance with Applicative/Monad

## License

This project is open source and available under the MIT License.
