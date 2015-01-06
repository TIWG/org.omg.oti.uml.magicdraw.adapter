package org.omg.oti.magicdraw

import org.omg.oti.UMLStringExpression

trait MagicDrawUMLStringExpression extends UMLStringExpression[MagicDrawUML] with MagicDrawUMLExpression {
  override protected def e: Uml#StringExpression
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
}