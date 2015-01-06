package org.omg.oti.magicdraw

import org.omg.oti.UMLEnumerationLiteral

trait MagicDrawUMLEnumerationLiteral extends UMLEnumerationLiteral[MagicDrawUML] with MagicDrawUMLInstanceSpecification {
  override protected def e: Uml#EnumerationLiteral
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}