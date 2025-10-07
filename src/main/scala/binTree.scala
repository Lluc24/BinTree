import scala.math.max

enum BinTree[+T]:
    case Node(value: T, left: BinTree[T], right: BinTree[T])
    case Empty

import BinTree.*

def preorder[T](tree: BinTree[T]): List[T] =
    tree match
        case Empty => Nil
        case Node(value, left, right) => value :: preorder(left) ++ preorder(right)

def depth[T](tree: BinTree[T]): Int =
    tree match
        case Empty => 0
        case Node(_, left, right) => 1 + max(depth(right), depth(left))

def numElems[T](tree: BinTree[T]): Int =
    tree match
        case Empty => 0
        case Node(_, left, right) => 1 + numElems(left) + numElems(right)

def iBuildTree[T](vec: Vector[T], pos: Int): BinTree[T] =
    if pos > vec.size then
        Empty
    else
        val leftPos: Int = 2*pos - 1
        val rightPos: Int = 2*pos
        Node(vec(pos - 1), iBuildTree(vec, 2*pos), iBuildTree(vec, 2*pos + 1))

def buildTree[T](xs: List[T]): BinTree[T] =
    val vec: Vector[T] = xs.toVector
    iBuildTree(vec, 1)

def iPrintTree[T](tree: BinTree[T], prefix: String): Unit =
    tree match
        case Empty => ()
        case Node(value, Empty, Empty) => println(value)
        case Node(value, left, Empty) =>
            println(value)
            print(prefix + "└─")
            iPrintTree(left, prefix + "│ ")
        case Node(value, Empty, right) =>
            println(value)
            print(prefix + "└─")
            iPrintTree(right, prefix + "  ")
        case Node(value, left, right) =>
            println(value)
            print(prefix + "└─")
            iPrintTree(left, prefix + "│ ")
            print(prefix + "└─")
            iPrintTree(right, prefix + "  ")

def printTree[T](tree: BinTree[T]): Unit =
    iPrintTree(tree, "")