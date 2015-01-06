package org.omg.oti.magicdraw

import org.omg.oti.UMLOpaqueExpression

trait MagicDrawUMLOpaqueExpression extends UMLOpaqueExpression[MagicDrawUML] with MagicDrawUMLValueSpecification {
  override protected def e: Uml#OpaqueExpression
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
}