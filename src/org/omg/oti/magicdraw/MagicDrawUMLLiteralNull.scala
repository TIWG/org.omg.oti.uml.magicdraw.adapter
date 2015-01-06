package org.omg.oti.magicdraw

import org.omg.oti.UMLLiteralNull

trait MagicDrawUMLLiteralNull extends UMLLiteralNull[MagicDrawUML] with MagicDrawUMLLiteralSpecification {
  override protected def e: Uml#LiteralNull
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
}