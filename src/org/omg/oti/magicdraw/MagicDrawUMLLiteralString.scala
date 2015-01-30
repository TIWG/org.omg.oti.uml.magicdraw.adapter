package org.omg.oti.magicdraw

import org.omg.oti.UMLLiteralString

trait MagicDrawUMLLiteralString extends UMLLiteralString[MagicDrawUML] with MagicDrawUMLLiteralSpecification {
  override protected def e: Uml#LiteralString
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  override def value = Option.apply(e.getValue)
}