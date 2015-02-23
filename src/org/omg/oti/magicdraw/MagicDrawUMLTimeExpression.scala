package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTimeExpression 
  extends UMLTimeExpression[MagicDrawUML]
  with MagicDrawUMLValueSpecification {

  override protected def e: Uml#TimeExpression
  import ops._

}
