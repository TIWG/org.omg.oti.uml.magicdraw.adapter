package org.omg.oti.magicdraw

import org.omg.oti.UMLImage

trait MagicDrawUMLImage extends UMLImage[MagicDrawUML] with MagicDrawUMLElement {
  override protected def e: Uml#Image
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def location: Option[String] = e.getLocation match {
    case null => None
    case "" => None
    case s => Some( s )
  }
}