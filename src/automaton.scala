package automaton

import scala.sys.exit

import sgeometry.Pos
import sdraw.World

object G {
  type Transition = (String, Char) => String
}

/**
  * 決定的有限オートマトンのシミュレータ
  */
case class DFAWorld(
    inputs: List[Char],
    state: String,
    transition: G.Transition,
    accept: List[String]
) extends World() {

  /** acceptState関数は受理状態列 (accept) に現在の状態が含まれているか否かを判定します。
   * accept.indexOf(state) はstateがacceptのなかで何番目の要素かを答えますが、見つからないときは-1を返します。
   * https://scala-lang.org/api/2.13.3/scala/collection/SeqOps.html#indexOf%5BB%3E:A%5D(B):Int
   */
  def acceptState(s: String): Boolean = { accept.indexOf(state) >= 0 }

  /**
    * 与えられた状態が受理状態なら状態名に * を追加する。
    */
  def decorate(state: String): String = {
    state + (if (acceptState(state)) "*" else "")
  }

  def tick(): World = {
    inputs match {
      case Nil => {
        println(
          if (acceptState(state)) "The input accepted."
          else "The input rejected."
        )

        exit() // scala.sys.exit はScalaの実行を強制終了する。
      }
      case c :: inputs => {
        val newState = transition(state, c)
        println(
          s"${decorate(state)}, [${c}]${inputs.mkString} => ${decorate(newState)}"
        )
        DFAWorld(inputs, newState, transition, accept)
      }
    }
  }

  def draw(): Boolean = { true }

  def click(p: Pos): World = { DFAWorld(inputs, state, transition, accept) }
  def keyEvent(key: String): World = {
    DFAWorld(inputs, state, transition, accept)
  }
}

object A extends App {
  // 状態遷移関数
  def transition(state: String, input: Char): String =
    (state, input) match {
      case ("q", '0')   => "q0"
      case ("q", '1')   => "q"
      case ("q0", '0')  => "q00"
      case ("q0", '1')  => "q"
      case ("q00", '0') => "q00"
      case ("q00", '1') => "q001"
      case ("q001", _)  => "q001"
      case _            => println("Something is wrong."); assert(false); ""
    }

  // 受理状態集合
  val accept = List("q001")

  DFAWorld("1100101".toList, "q", transition, accept).bigBang(1, 1, 3)
}
