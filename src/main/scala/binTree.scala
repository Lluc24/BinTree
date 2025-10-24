import scala.math.max
import scala.compiletime.ops.int.*

enum BinTree:
    case Node[T, A <: BinTree, B <: BinTree](value: T, left: A, right: B)
    case Empty

    override def toString: String = "\n" + this.iToString("")

    private def iToString(prefix: String): String = this match
        case Empty => ""
        case Node(value, Empty, Empty) => s"${value}\n"
        case Node(value, left, Empty) => s"${value}\n${prefix}└── ${left.iToString(prefix + "    ")}"
        case Node(value, Empty, right) => s"${value}\n${prefix}└── ${right.iToString(prefix + "    ")}"
        case Node(value, left, right) =>
            s"${value}\n${prefix}├── ${left.iToString(prefix + "│   ")}${prefix}└── ${right.iToString(prefix + "    ")}"

object BinTree:
    def apply[T](value: T, left: BinTree = Empty, right: BinTree = Empty): BinTree = Node(value, left, right)
    def apply[T](): BinTree = Empty
    def apply[T](values: T*): BinTree =
        val vec: Vector[T] = values.toVector
        iBuildTree(vec, 1)

    private def iBuildTree[T](vec: Vector[T], pos: Int): BinTree =
        if pos > vec.size then Empty
        else BinTree(vec(pos - 1), iBuildTree(vec, 2*pos), iBuildTree(vec, 2*pos + 1))

import BinTree.*

def sum[X <: Int, Y <: Int, Z <: Int](a: X, b: Y, c: Z) = (a + b + c).asInstanceOf[X + Y + Z]

type Size[T <: BinTree] <: Int = T match
    case Empty.type => 0
    case Node[_, l, r] => 1 + Size[l] + Size[r]

def size[T <: BinTree](tree: T): Size[T] = tree match
    case _: Empty.type => 0
    case node: Node[v, l, r] => sum(1, size(node.left), size(node.right))


trait Functor[F]:
    extension [A](fa: F) def fmap[B](func: A => B): F

given Functor[BinTree] with
    extension [A](t: BinTree) infix def fmap[B](func: A => B): BinTree =
        t match
            case Empty => Empty fmap func
            case node: Node[A, BinTree, BinTree] => Node[B, BinTree, BinTree](func(node.value), node.left fmap func, node.right fmap func)

def preorder(tree: BinTree): List[Any] =
    tree match
        case Empty => Nil
        case Node(value, left, right) => value :: preorder(left) ++ preorder(right)

def depth(tree: BinTree): Int =
    tree match
        case Empty => 0
        case Node(_, left, right) => 1 + max(depth(right), depth(left))
