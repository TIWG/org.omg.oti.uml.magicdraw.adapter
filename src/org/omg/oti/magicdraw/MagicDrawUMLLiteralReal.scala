package org.omg.oti.magicdraw

import org.omg.oti.UMLLiteralReal

trait MagicDrawUMLLiteralReal extends UMLLiteralReal[MagicDrawUML] with MagicDrawUMLLiteralSpecification {
  override protected def e: Uml#LiteralReal
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
}