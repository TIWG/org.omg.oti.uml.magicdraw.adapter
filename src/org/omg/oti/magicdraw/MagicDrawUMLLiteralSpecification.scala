package org.omg.oti.magicdraw

import org.omg.oti.UMLLiteralSpecification

trait MagicDrawUMLLiteralSpecification extends UMLLiteralSpecification[MagicDrawUML] with MagicDrawUMLValueSpecification {
  override protected def e: Uml#LiteralSpecification
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
}