package timer1

import sgeometry.Pos
import sdraw.{World, White}

/**
 * TimerWorld1の時間の表現は、経過時間を10ミリ秒単位の自然数としている。
 * これは TimerWorld1(t: Int) において引数 t で表されている。
 * つまり、保存されている経過時間の値が105の場合、1秒+50ミリ秒が経過したことを表す。
 * なお、1秒 = 1000ミリ秒、10ミリ秒の100倍が1秒。
 **/
case class TimerWorld(t: Int) extends World() {
  /**
    * draw は画面の描画を担当する関数。
    * tick を初めとするイベント処理のたびに自動的に呼ばれる。
    * ここでは、経過時間を描画している。
    */
  def draw(): Boolean = {
    // まず、画面を消去するために(0, 0)-(0+width, 0+height)の範囲を白で塗り潰している。
    canvas.drawRect(Pos(0, 0), canvas.width, canvas.height, White)
    // 画面に経過時間を表示
    canvas.drawString(Pos(0, canvas.height/2), f"${0.01*t}%.02f")
    true
  }

  /**
   * tick関数は bigBang 関数で指定された時間の刻み幅ごとに自動的に呼び出される。
   * ここでは経過時間を加算して次のワールドを構成している。
   **/
  def tick():                World = { TimerWorld(t + 1) }

  /**
   * clickとkeyEventはそれぞれマウスクリックとキーボード入力に対応するための関数だが、
   * この例では積極的には用いない。
   **/
  def click(p: Pos):         World = { TimerWorld(t) }
  def keyEvent(key: String): World = { TimerWorld(t) }
}

object A extends App {
  /**
   * 初期ワールドの構成。ここから世界が始まる。
   * 最初のふたつの引数は表示するウィンドウの横と縦の長さをピクセル数で指定したもの。
   * 三番目の引数は画面更新の頻度を秒単位で指定したもの。0.01秒、あるいは10ミリ秒ごとにtick関数が呼ばれる。
   **/

  TimerWorld(0).bigBang(50, 50, 0.01)
}
