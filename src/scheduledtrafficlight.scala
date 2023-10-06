package scheduled_traffic_light

import sgeometry.Pos
import sdraw.{World, Color, Red, Green, Yellow}

case class ScheduledLight(color: Color, waitFor: Int) extends World() {
   val C = Config

  /**
   * 信号を描画する。円を塗り潰しているだけ。
   **/
  def draw(): Boolean = {
    println(s"draw: $waitFor")
    canvas.drawDisk(Pos(150, 150), 120, color)
    true
  }

  /**
   * 信号の状態の遷移
   **/
  def tick(): World = {
    println((color, waitFor))
    (color, waitFor) match {
      case (Red,    1)  => ScheduledLight(Green,  C.SECS_FOR_GREEN)
      case (Green,  1)  => ScheduledLight(Yellow, C.SECS_FOR_YELLOW)
      case (Yellow, 1)  => ScheduledLight(Red,    C.SECS_FOR_RED)
      case _            => ScheduledLight(color,  waitFor - 1)
    }
  }

  /**
   * clickとkeyEventはそれぞれマウスクリックとキーボード入力に対応するための関数だが、
   * この例では積極的には用いない。
   **/
  def click(p: Pos):         World = { ScheduledLight(color, waitFor) }
  def keyEvent(key: String): World = { ScheduledLight(color, waitFor) }
}

object Config {
  val SECS_FOR_RED    = 7
  val SECS_FOR_GREEN  = 5
  val SECS_FOR_YELLOW = 2
}

object Main extends App {
  ScheduledLight(Red, Config.SECS_FOR_RED).bigBang(300, 300, 1)
}