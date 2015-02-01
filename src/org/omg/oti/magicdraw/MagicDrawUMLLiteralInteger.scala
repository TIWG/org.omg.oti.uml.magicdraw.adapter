package org.omg.oti.magicdraw

import org.omg.oti.UMLLiteralInteger

trait MagicDrawUMLLiteralInteger extends UMLLiteralInteger[MagicDrawUML] with MagicDrawUMLLiteralSpecification {
  override protected def e: Uml#LiteralInteger
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  override def value = e.getValue
}