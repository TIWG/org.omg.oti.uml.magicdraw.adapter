package org.omg.oti.magicdraw

import org.omg.oti.UMLLiteralBoolean

trait MagicDrawUMLLiteralBoolean extends UMLLiteralBoolean[MagicDrawUML] with MagicDrawUMLLiteralSpecification {
  override protected def e: Uml#LiteralBoolean
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  override def value = e.isValue
  
}