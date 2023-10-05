package sdraw

import colors.IColor
import geometry.{Posn => JavaPos}
import draw.{Canvas => JavaCanvas}

import sgeometry.Pos

case class Canvas(_canvas: JavaCanvas) {
  def width: Int = _canvas.getWidth()
  def height: Int = _canvas.getHeight()

  def drawCircle(c: Pos, radius: Int, color: IColor): Boolean =
    _canvas.drawCircle(new JavaPos(c.x, c.y), radius, color)

  def drawDisk(c: Pos, radius: Int, color: IColor): Boolean =
    _canvas.drawDisk(new JavaPos(c.x, c.y), radius, color)

  def drawRect(c: Pos, width: Int, height: Int, color: IColor): Boolean =
    _canvas.drawRect(new JavaPos(c.x, c.y), width, height, color)

  def drawString(c: Pos, text: String): Boolean =
    _canvas.drawString(new JavaPos(c.x, c.y), text)
}
