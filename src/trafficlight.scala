package traffic_light

import sgeometry.Pos
import sdraw.{World, Color, Red, Green, Yellow}

case class Light(color: Color) extends World() {

  /**
   * 信号を描画する。円を塗り潰しているだけ。
   **/
  def draw(): Boolean = {
    canvas.drawDisk(Pos(150, 150), 120, color)
    true
  }

  /**
   * 信号の状態の遷移
   **/
  def tick(): World = {
    Light(color match {
      case Red => Green
      case Green => Yellow
      case Yellow => Red
    })
  }

  /**
   * clickとkeyEventはそれぞれマウスクリックとキーボード入力に対応するための関数だが、
   * この例では積極的には用いない。
   **/
  def click(p: Pos):         World = { Light(color) }
  def keyEvent(key: String): World = { Light(color) }
}

object A extends App {
  Light(Red).bigBang(300, 300, 2)
}