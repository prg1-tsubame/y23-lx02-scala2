package poton

import scala.math.{max, min}

import sgeometry.Pos
import sdraw.{World, Blue, White, Green}

case class PotonWorld(centerX: Int, centerY: Int) extends World() {
  val C = Config
  val BallRadius = C.BallRadius                     // ボールの半径
  val MinX       = BallRadius + 10                  // X座標の最小値（これ以上左に寄ると左の壁に衝突）
  val MaxX       = C.WorldWidth  - BallRadius - 10  // X座標の最大値（これ以上、右に寄ると右の壁に衝突）
  val MaxY       = C.WorldHeight - BallRadius * 2   // Y座標の最大値（これ以上、落ちると画面から消える）
  val HoleX      = 200                              // 穴の中心のX座標

  def draw(): Boolean = {
    canvas.drawRect(Pos(0, 0), canvas.width, canvas.height, White)    // 画面消去
    canvas.drawRect(Pos(10, MaxY - 10), HoleX - 10, 20, Green)        // 左方の草原の描画
    canvas.drawRect(                                                  // 右方の草原の描画
      Pos(HoleX + (BallRadius * 2 + 10), MaxY - 10),     // 矩形の位置: Pos(x, y)
      canvas.width - (HoleX + (BallRadius * 2 + 10)),    // 矩形の幅
      20,                                                // 矩形の高さ
      Green                                              // 塗り潰し色
    )
    canvas.drawDisk(Pos(centerX, centerY), BallRadius, Blue)          // ボール
    true
  }

  def tick(): World = {
    PotonWorld(centerX, min(centerY + 5, MaxY))
  }

  def click(p: Pos): World = { PotonWorld(centerX, centerY) }

  def keyEvent(key: String): World = {
    println(key)
    key match {
      case "SPACE"       => PotonWorld(centerX, MaxY)
      case "LEFT" | "h"  => PotonWorld(max(MinX, centerX - 5), centerY)
      case "RIGHT" | "l" => PotonWorld(min(centerX + 5, MaxX), centerY)
      case _             => PotonWorld(centerX, centerY)
    }
  }
}

object Config {
  val BallRadius  =  50
  val WorldWidth  = 800
  val WorldHeight = 600
}

// Run this app from sbt: [project lxz; runMain poton.A]
object A extends App {
  val world = PotonWorld(Config.WorldWidth / 2, Config.BallRadius * 2)
  world.bigBang(Config.WorldWidth, Config.WorldHeight, 0.1)
}