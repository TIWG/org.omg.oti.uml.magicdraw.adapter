package org.omg.oti.magicdraw

import org.omg.oti.UMLExpression

trait MagicDrawUMLExpression extends UMLExpression[MagicDrawUML] with MagicDrawUMLValueSpecification {
  override protected def e: Uml#Expression
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}